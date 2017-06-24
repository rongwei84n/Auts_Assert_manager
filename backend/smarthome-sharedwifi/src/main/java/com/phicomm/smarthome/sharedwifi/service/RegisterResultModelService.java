package com.phicomm.smarthome.sharedwifi.service;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.model.router.RequestRegisterBodyModel;
import com.phicomm.smarthome.sharedwifi.model.router.RouterItemModel;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.sharedwifi
 * PACKAGE_NAME: com.fhicomm.sharedwifi.service
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/8
 */
public interface RegisterResultModelService {

    public SmartHomeResponse getRegisterResultModelApi(RequestRegisterBodyModel requestRegisterBodyModel);
    public RouterItemModel insertRouterItemModelJpaApi(RequestRegisterBodyModel requestRegisterBodyModel);
    public RequestRegisterBodyModel verificationParameter4RequestRegisterBodyModel(RequestRegisterBodyModel requestRegisterBodyModel);

}
