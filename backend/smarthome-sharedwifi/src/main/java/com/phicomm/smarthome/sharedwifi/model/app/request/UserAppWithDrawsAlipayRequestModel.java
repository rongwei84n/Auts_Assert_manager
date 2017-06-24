package com.phicomm.smarthome.sharedwifi.model.app.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 提现到支付宝帐号 用户请求参数
 * @author rongwei.huang
 *
 */
public class UserAppWithDrawsAlipayRequestModel implements Serializable {
    private String token;

    @JsonProperty("alipay_account")
    private String alipayAccount; //支付宝账号

    public UserAppWithDrawsAlipayRequestModel() {
        super();
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
