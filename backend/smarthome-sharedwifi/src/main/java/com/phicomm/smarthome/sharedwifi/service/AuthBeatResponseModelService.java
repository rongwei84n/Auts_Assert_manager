package com.phicomm.smarthome.sharedwifi.service;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.model.h5web.GuestOrder;
import com.phicomm.smarthome.sharedwifi.model.router.ItemAuthBeatModel;
import com.phicomm.smarthome.sharedwifi.model.router.ItemAuthResultModel;
import com.phicomm.smarthome.sharedwifi.model.router.RequestAuthBodyModel;

import java.util.List;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.sharedwifi
 * PACKAGE_NAME: com.fhicomm.sharedwifi.service
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/8
 */
public interface AuthBeatResponseModelService {

    public List<ItemAuthResultModel> getList4GuestOrderFinalStatusApi(List<ItemAuthBeatModel> itemAuthBeatModelList,String routerMac);
    public ItemAuthResultModel getGuestItemOrderFinalStatusApi(ItemAuthBeatModel itemAuthBeatModel, String routerMac);
    public int TEMPORARY4SSP_inspectOrderStatusFromTencentWeChatServiceApi(GuestOrder order);
    public SmartHomeResponse executorBus4AuthRequestApi(RequestAuthBodyModel requestAuthBodyModel);

    /** not 4 controller */
    public int inspectValidityGuestOrderItemByCalculationTimeApi(GuestOrder order);
    public int getGuestAuthorityStatusApi(GuestOrder order);
    public List<ItemAuthResultModel> download4PassAuthorizationApi(RequestAuthBodyModel requestAuthBodyModel);



}
