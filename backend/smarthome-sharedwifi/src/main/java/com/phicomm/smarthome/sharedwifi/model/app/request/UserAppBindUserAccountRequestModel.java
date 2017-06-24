package com.phicomm.smarthome.sharedwifi.model.app.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 绑定斐讯帐号和微信的OpenId 用户请求参数
 * @author rongwei.huang
 *
 */
public class UserAppBindUserAccountRequestModel implements Serializable {

    @JsonProperty("open_id")
    private String openId; //微信open_id
    private String token;

    public UserAppBindUserAccountRequestModel() {
        super();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
