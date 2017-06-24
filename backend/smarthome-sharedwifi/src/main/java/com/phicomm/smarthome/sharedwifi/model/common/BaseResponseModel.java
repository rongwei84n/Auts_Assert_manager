package com.phicomm.smarthome.sharedwifi.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.sharedwifi.consts.Const.ResponseStatus;
import com.phicomm.smarthome.sharedwifi.util.MyResponseutils;

/**
 * 返回给客户端Json数据基类，返回的数据必须包含ret_code和ret_msg数据
 * @author rongwei.huang
 *
 */
public class BaseResponseModel {
    @JsonProperty("ret_code")
    private int retCode;
    
    @JsonProperty("ret_msg")
    private String retMsg;
    
    public BaseResponseModel() {
        retCode = ResponseStatus.STAUS_OK;
        retMsg = MyResponseutils.parseMsg(ResponseStatus.STAUS_OK);
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
        this.retMsg = MyResponseutils.parseMsg(retCode);
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
