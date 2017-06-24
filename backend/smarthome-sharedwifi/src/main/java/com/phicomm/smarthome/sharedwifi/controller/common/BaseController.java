package com.phicomm.smarthome.sharedwifi.controller.common;


import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.consts.Const.ResponseStatus;
import com.phicomm.smarthome.sharedwifi.util.MyResponseutils;
import com.phicomm.smarthome.util.StringUtil;

public class BaseController {
    protected SmartHomeResponse<Object> errorResponse(int errCode) {
        String errMsg = MyResponseutils.parseMsg(errCode);
        return errorResponse(errCode, errMsg);
    }

    protected SmartHomeResponse<Object> errorResponse(int errCode, String errMsg) {
        SmartHomeResponse<Object> response = MyResponseutils.geResponse(null);
        response.setErrCode(errCode);
        if (StringUtil.isNullOrEmpty(errMsg)) {
            response.setErrMsg(MyResponseutils.parseMsg(errCode));
        }else {
            response.setErrMsg(errMsg);
        }
        return response;
    }

    protected SmartHomeResponse<Object> successResponse(Object obj) {
        SmartHomeResponse<Object> response = MyResponseutils.geResponse(obj);
        response.setErrCode(ResponseStatus.STAUS_OK);
        response.setErrMsg(MyResponseutils.parseMsg(ResponseStatus.STAUS_OK));
        return response;
    }
}
