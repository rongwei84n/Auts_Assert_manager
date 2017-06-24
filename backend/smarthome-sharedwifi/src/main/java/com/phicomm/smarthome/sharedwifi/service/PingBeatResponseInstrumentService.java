package com.phicomm.smarthome.sharedwifi.service;

import com.phicomm.smarthome.sharedwifi.model.router.RequestPingBeatsBodyModel;
import com.phicomm.smarthome.sharedwifi.model.router.RouterConfigModel;
import com.phicomm.smarthome.sharedwifi.model.router.RouterWhiteListModel;

import java.util.List;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.jpa
 * PACKAGE_NAME: com.fhicomm.sharedwifi.service
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/6
 */
public interface PingBeatResponseInstrumentService {

    public RouterConfigModel verifyValidityRouterItemApi(RequestPingBeatsBodyModel requestPingBeatsBodyModel);
    public List<RouterWhiteListModel> getRouterWhiteListModelByConfigIdApi(RouterConfigModel routerConfigModel);
}
