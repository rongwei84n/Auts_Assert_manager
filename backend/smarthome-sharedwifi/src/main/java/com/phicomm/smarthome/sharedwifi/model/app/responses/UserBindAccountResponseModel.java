package com.phicomm.smarthome.sharedwifi.model.app.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.sharedwifi.model.common.BaseResponseModel;

/**
 * @author wenhua.tang
 *
 */
public class UserBindAccountResponseModel extends BaseResponseModel{
    @JsonProperty("open_id")
    private String openId;

    private String token;

    public UserBindAccountResponseModel() {
        super();
    }

    public UserBindAccountResponseModel(String openId, String token) {
        super();
        this.openId = openId;
        this.token = token;
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
