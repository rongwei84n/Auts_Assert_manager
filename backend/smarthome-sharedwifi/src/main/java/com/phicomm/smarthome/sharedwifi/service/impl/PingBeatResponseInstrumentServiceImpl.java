package com.phicomm.smarthome.sharedwifi.service.impl;

import com.phicomm.smarthome.sharedwifi.model.router.*;
import com.phicomm.smarthome.sharedwifi.repository.RouterConfigModelJpaGerepository;
import com.phicomm.smarthome.sharedwifi.repository.RouterItemModelJpaGerepository;
import com.phicomm.smarthome.sharedwifi.repository.RouterWhiteListModelJpaGerepository;
import com.phicomm.smarthome.sharedwifi.service.PingBeatResponseInstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.jpa
 * PACKAGE_NAME: com.fhicomm.sharedwifi.impl
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/6
 */
@Transactional
@Service
public class PingBeatResponseInstrumentServiceImpl implements PingBeatResponseInstrumentService {

    @Autowired
    private RouterItemModelJpaGerepository routerItemModelJpaGerepository;

    @Autowired
    private RouterConfigModelJpaGerepository routerConfigModelJpaGerepository;

    @Autowired
    private RouterWhiteListModelJpaGerepository routerWhiteListModelJpaGerepository;

    @Override
    public RouterConfigModel verifyValidityRouterItemApi(RequestPingBeatsBodyModel requestPingBeatsBodyModel) {
        /**
         * 1. Router 发起 pingbeats 时后台校验当前路由器是否已经注册成功，status值；
         * 2. 根据Router ip、mac找到当前router对应的model；
         * 3. 依据条件当前router model、config_version判断是否下发配置；
         * */
        if(null == verificationParameter4RequestPingBeatsBodyModel(requestPingBeatsBodyModel)){

            /**
             *  返回当前请求值（路由器对象）非法，不合法状态为-1；
             * */
            return null;
        }

        String modelInRequest = ""+requestPingBeatsBodyModel.getModel();
        String routerIpInRequest = ""+requestPingBeatsBodyModel.getRouterIp();
        String routerMacInRequest = ""+requestPingBeatsBodyModel.getRouterMac();
        String configVersionInRequest = ""+requestPingBeatsBodyModel.getConfigVersion();

        RouterItemModel  routerItemModel = routerItemModelJpaGerepository.findByRouterIpAndRouterMacAndModel(routerIpInRequest,routerMacInRequest,modelInRequest);

        if(null == routerItemModel){return null;}
        if(routerItemModel.getStatus() != 0){return null;}

        /** 当前路由器后台注册结果为可用（0），可以继续配置版本号校验（查询sw_router_config_表_router_id，） */
        RouterConfigModel routerConfigModel = routerConfigModelJpaGerepository.findByRouterId(routerItemModel.getId());

        if(null == routerConfigModel){return null;}
        if(configVersionInRequest.equals(routerConfigModel.getConfigVersion())){
            return null;
        }
        return routerConfigModel;
    }

    @Override
    public List<RouterWhiteListModel> getRouterWhiteListModelByConfigIdApi(RouterConfigModel routerConfigModel) {
        if(null == routerConfigModel){return null;}
        return routerWhiteListModelJpaGerepository.findByConfigId(routerConfigModel.getId());
    }

    public RequestPingBeatsBodyModel verificationParameter4RequestPingBeatsBodyModel(RequestPingBeatsBodyModel requestPingBeatsBodyModel){

        if(null == requestPingBeatsBodyModel){return null;}
        if(((null == requestPingBeatsBodyModel.getModel()||(("".equals(requestPingBeatsBodyModel.getModel()))))) ||
                ((null == requestPingBeatsBodyModel.getConfigVersion()||(("".equals(requestPingBeatsBodyModel.getConfigVersion()))))) ||
                ((null == requestPingBeatsBodyModel.getRouterIp()||(("".equals(requestPingBeatsBodyModel.getRouterIp()))))) ||
                ((null == requestPingBeatsBodyModel.getRouterMac()||(("".equals(requestPingBeatsBodyModel.getRouterMac()))))) ||
                ((null == requestPingBeatsBodyModel.getRouterUpTime()||(("".equals(requestPingBeatsBodyModel.getRouterUpTime()))))))
        {return null;}

        return requestPingBeatsBodyModel;

    }
}
