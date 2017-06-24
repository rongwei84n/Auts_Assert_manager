package com.phicomm.smarthome.sharedwifi.model.app.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 查询用户微信号是否绑定了斐讯帐号 的请求参数
 * @author rongwei.huang
 *
 */
public class UserAppGetUserBindMsgRequestModel implements Serializable {

    public UserAppGetUserBindMsgRequestModel() {
        super();
    }

    @JsonProperty("open_id")
    private String openId; //用户微信open_id

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
