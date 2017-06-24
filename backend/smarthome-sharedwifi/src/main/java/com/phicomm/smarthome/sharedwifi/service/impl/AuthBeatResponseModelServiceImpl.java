package com.phicomm.smarthome.sharedwifi.service.impl;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.consts.Const;
import com.phicomm.smarthome.sharedwifi.model.h5web.GuestOrder;
import com.phicomm.smarthome.sharedwifi.model.router.ItemAuthBeatModel;
import com.phicomm.smarthome.sharedwifi.model.router.ItemAuthResultModel;
import com.phicomm.smarthome.sharedwifi.model.router.RequestAuthBodyModel;
import com.phicomm.smarthome.sharedwifi.model.router.ResponseAuthResultModel;
import com.phicomm.smarthome.sharedwifi.model.user.Guest4RouterModel;
import com.phicomm.smarthome.sharedwifi.repository.RouterGuestModelJpaGerepository;
import com.phicomm.smarthome.sharedwifi.repository.RouterGuestOrderModelJpaGerepository;
import com.phicomm.smarthome.sharedwifi.service.AuthBeatResponseModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * PROJECT_NAME: fhicomm.smarthome.model.sharedwifi
 * PACKAGE_NAME: com.fhicomm.sharedwifi.impl
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/8
 */

@Transactional
@Service
@PropertySources(@PropertySource(value = "classpath:server.properties", ignoreResourceNotFound = true))
public class AuthBeatResponseModelServiceImpl implements AuthBeatResponseModelService {

    @Autowired
    private RouterGuestOrderModelJpaGerepository routerGuestOrderModelJpaGerepository;

    @Autowired
    private RouterGuestModelJpaGerepository routerGuestModelJpaGerepository;

    /* 定参: Guest上网时长偏移量，Guest可以多于服务器时间XX分钟上网时长,资源文件在server.properties,默认300s */
    @Value("${OFFSET_CALCULATION_IN_SECOND_VALUE}")
    private long offsetCalculationInSecond;

    @Override
    public List<ItemAuthResultModel> getList4GuestOrderFinalStatusApi(List<ItemAuthBeatModel> itemAuthBeatModelList, String routerMac){
        if(null == itemAuthBeatModelList){return null;}
        List<ItemAuthResultModel> curResult4ItemAuthResultModelList = new ArrayList<ItemAuthResultModel>();
        Iterator<ItemAuthBeatModel> iterator = itemAuthBeatModelList.iterator();
        while(iterator.hasNext()){
            ItemAuthBeatModel itemAuthBeatModel = (iterator.next());
            if(null != itemAuthBeatModel)
            {
                curResult4ItemAuthResultModelList.add(getGuestItemOrderFinalStatusApi(itemAuthBeatModel,routerMac));
            }
        }
        return curResult4ItemAuthResultModelList;
    }

    @Override
    public ItemAuthResultModel getGuestItemOrderFinalStatusApi(ItemAuthBeatModel itemAuthBeatModel,String routerMac){

        if(null == itemAuthBeatModel){return null;}

        /* * 1. 根据ItemAuthBeatModel携带router_mac找到 sw_guest 的Id,此sw_guest_Id与 sw_guest_order 表中guest_Id对应 */
        Guest4RouterModel guest4RouterModel = routerGuestModelJpaGerepository.findByDeviceMacAndRouterMac(itemAuthBeatModel.getDeviceMac(), routerMac);
        if(null == guest4RouterModel){return null;}

        /* * 2. 根据guest_id和对应的order_id 找到对应的订单Order详情*/
        GuestOrder order = routerGuestOrderModelJpaGerepository.findValidByGuestIdAndOrderId(guest4RouterModel.getId(),itemAuthBeatModel.getOrderId());
        if (null == order){return null;}

        /* **************************************
         *  授权值 authority 结果判定条件：
         *  1. order_pay_status 状态 0 待支付  1 支付成功
         *  2. order_status  订单状态 1 待支付  2 消费中  3 已过期
         *  3. 通过getGuestAuthorityStatusApi()判定1&2状态位设置当前guest_id authority(0 接受 1拒绝)
         * *************************************/
        return new ItemAuthResultModel(getGuestAuthorityStatusApi(order), guest4RouterModel.getDeviceMac(), order.getOrderId());
    }

    /**
     * 1. 通过sw_guest_order的buy_time(起始时间)与online_time_total(购买时长)判断当前Order(对应Guest)有效性
     * 2. 根据当前服务NTP时间判断相对时间
     * 3. 修改sw_guest_order 的 order_status 订单状态的值；
     * */
    @Override
    public int inspectValidityGuestOrderItemByCalculationTimeApi(GuestOrder order){

        /* 订单状态 1 待支付  2 消费中  3 已过期 */
        int curOrderStatus = Const.OrderStatusInterfaces.ORDER_STATUS_EXPIRED;
        if(null == order){return curOrderStatus;}

        /* 取到Order中起始时间，毫秒级*/
        long buyTimeInOrderTb = order.getBuyTime();
        long onlineTimeTotalInSecond = (long)(order.getOnlineTimeTotal()*3600);

        /* 当前时间 curServiceTimeInSecond 秒级*/
        long curServiceTimeInSecond = new Date().getTime()/1000;

        if((buyTimeInOrderTb + onlineTimeTotalInSecond) > (curServiceTimeInSecond + offsetCalculationInSecond)){

            /* 订单状态 2 消费中 */
            curOrderStatus = Const.OrderStatusInterfaces.ORDER_STATUS_PAIDINCOMMON;
            return curOrderStatus;
        }
        order.setOrderStatus(curOrderStatus);

        /* 修正通过sw_guest_order的 order_status */
        return routerGuestOrderModelJpaGerepository.save(order).getOrderStatus();
    }

    @Override
    public int TEMPORARY4SSP_inspectOrderStatusFromTencentWeChatServiceApi(GuestOrder order) {

        /* *****************************************************************
         *  正式接口: inspectOrderStatusFromTencentWeChatService(Order order)
         *  接口功能: 向腾讯微信支付后台查询当前OrderID对应支付的结果；
         *  Timer : 20170614
         * *****************************************************************/
        order.setOrderPayStatus(Const.OrderPayStatusInterfaces.ORDER_PAY_STATUS_PAID); /* 状态 0 待支付  1支付成功 */
        return routerGuestOrderModelJpaGerepository.save(order).getOrderPayStatus();
    }

    @Override
    public int getGuestAuthorityStatusApi(GuestOrder order){

        /* *
         *  0. 当订单Item raw data的status状态为 -1 禁用时(Authority 1 拒绝)
         *  1. 当订单支付状态为(待支付 0)时，(Authority 1 拒绝)
         *  2. 当订单状态 order_status 为(2 消费中)时，(Authority 0 接受)
         *  3. 当订单状态 order_status 为(3 已过期)时，(Authority 1 拒绝)
         * */
        if(null == order){return Const.GuestAuthorityResultInterfaces.AUTHORITY_RESULT_NOK;}
        if(Const.DataItemStatusInDbInterfaces.ITEM_STATUS_NOK == order.getStatus()){
            return Const.GuestAuthorityResultInterfaces.AUTHORITY_RESULT_NOK;
        }
        if(order.getOrderPayStatus() == Const.OrderPayStatusInterfaces.ORDER_PAY_STATUS_UNPAID){
            return Const.GuestAuthorityResultInterfaces.AUTHORITY_RESULT_NOK;
        }

        /* * 只针对订单是在消费中(order_status=2)时做时间差额计算，根据结果重置order_status，并返回 Authority 对应状态*/
        if(order.getOrderStatus() == Const.OrderStatusInterfaces.ORDER_STATUS_PAIDINCOMMON){
            if(inspectValidityGuestOrderItemByCalculationTimeApi(order) == Const.OrderStatusInterfaces.ORDER_STATUS_PAIDINCOMMON)
            {
                return Const.GuestAuthorityResultInterfaces.AUTHORITY_RESULT_OK;
            }
            else{return Const.GuestAuthorityResultInterfaces.AUTHORITY_RESULT_NOK;}
        }
        if(order.getOrderStatus() == Const.OrderStatusInterfaces.ORDER_STATUS_EXPIRED){
            return Const.GuestAuthorityResultInterfaces.AUTHORITY_RESULT_NOK;
        }

        return Const.GuestAuthorityResultInterfaces.AUTHORITY_RESULT_NOK;
    }

    @Override
    public SmartHomeResponse executorBus4AuthRequestApi(RequestAuthBodyModel requestAuthBodyModel){

        SmartHomeResponse smartHomeResponse = new SmartHomeResponse(Const.ResponseStatus.STATUS_COMMON_NULL,
                Const.ResponseStatus.STATUS_COMMON_NULL_STR,
                null);
        if(null == requestAuthBodyModel){return smartHomeResponse;}

        switch(requestAuthBodyModel.getAction())
        {
            case Const.RouterAuthBeatActionKeys.AUTH_ACTION_KEY_LOGIN:
                if(requestAuthBodyModel.getItemAuthBeatModelList().size() != 1)
                {
                    smartHomeResponse.setErrCode(Const.ResponseStatus.STATUS_COMMON_NULL);
                    smartHomeResponse.setErrMsg(Const.ResponseStatus.STATUS_COMMON_NULL_STR);
                    smartHomeResponse.setResult(null);

                }else{
                    smartHomeResponse.setErrCode(Const.ResponseStatus.STATUS_OK);
                    smartHomeResponse.setErrMsg(Const.ResponseStatus.STATUS_OK_STR);
                    smartHomeResponse.setResult(new ResponseAuthResultModel(getList4GuestOrderFinalStatusApi(requestAuthBodyModel.getItemAuthBeatModelList(), requestAuthBodyModel.getRouterMac())));
                }

                break;
            case Const.RouterAuthBeatActionKeys.AUTH_ACTION_KEY_QUREY:

                /** 20170619 20:31 修复Query时单Item对象*/
                if(requestAuthBodyModel.getItemAuthBeatModelList().size() > 0){

                    List<ItemAuthResultModel> itemAuthResultModelList = getList4GuestOrderFinalStatusApi(requestAuthBodyModel.getItemAuthBeatModelList(), requestAuthBodyModel.getRouterMac());
                    if((null != itemAuthResultModelList)&&(itemAuthResultModelList.size() > 0))
                    {
                        smartHomeResponse.setErrCode(Const.ResponseStatus.STATUS_OK);
                        smartHomeResponse.setErrMsg(Const.ResponseStatus.STATUS_OK_STR);

                        smartHomeResponse.setResult(new ResponseAuthResultModel(itemAuthResultModelList));
                    }
                }

                break;
            case Const.RouterAuthBeatActionKeys.AUTH_ACTION_KEY_LOGOUT:

                smartHomeResponse.setErrCode(Const.ResponseStatus.STATUS_OK);
                smartHomeResponse.setErrMsg(Const.ResponseStatus.STATUS_OK_ENABLE);
                smartHomeResponse.setResult(null);

                break;

            case Const.RouterAuthBeatActionKeys.AUTH_ACTION_KEY_DOWNLOAD:

                if(null != requestAuthBodyModel.getItemAuthBeatModelList()){return smartHomeResponse;}
                smartHomeResponse.setErrCode(Const.ResponseStatus.STATUS_OK);
                smartHomeResponse.setErrMsg(Const.ResponseStatus.STATUS_OK_STR);
                smartHomeResponse.setResult(new ResponseAuthResultModel(download4PassAuthorizationApi(requestAuthBodyModel)));

                break;
            default:
                break;
        }
        return smartHomeResponse;
    }

    @Override
    public List<ItemAuthResultModel> download4PassAuthorizationApi(RequestAuthBodyModel requestAuthBodyModel){
        if(null == requestAuthBodyModel){return null;}

        /** 通过router_mac查询device_mac，找到order_id */
        if(null == requestAuthBodyModel.getRouterMac()){return null;}

        List<Guest4RouterModel> guest4RouterModelList = routerGuestModelJpaGerepository.findByRouterMac(requestAuthBodyModel.getRouterMac());
        if(null == guest4RouterModelList){return null;}
        List<ItemAuthResultModel> itemAuthResultModelList = new ArrayList<>();
        Iterator iterator = guest4RouterModelList.iterator();
        while(iterator.hasNext())
        {
            Guest4RouterModel guest4RouterModel = (Guest4RouterModel)(iterator.next());

            List<GuestOrder> guestOrderList = routerGuestOrderModelJpaGerepository.findPassAuthorizationByGuestIdAndOrderStatus(guest4RouterModel.getId(), Const.OrderStatusInterfaces.ORDER_STATUS_PAIDINCOMMON);
            if((null != guestOrderList)&&(guestOrderList.size()>0))
            {
                itemAuthResultModelList.add(new ItemAuthResultModel(Const.GuestAuthorityResultInterfaces.AUTHORITY_RESULT_OK,
                        guest4RouterModel.getDeviceMac(),
                        guestOrderList.get(0).getOrderId()));
            }
        }

        return itemAuthResultModelList;
    }

}
