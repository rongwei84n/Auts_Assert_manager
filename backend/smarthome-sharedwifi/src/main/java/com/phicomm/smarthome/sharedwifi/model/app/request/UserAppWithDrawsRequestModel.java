package com.phicomm.smarthome.sharedwifi.model.app.request;

import java.io.Serializable;

/**
 * 提现到微信帐号 用户请求参数
 * @author rongwei.huang
 *
 */
public class UserAppWithDrawsRequestModel implements Serializable {
    /*
     * 请求提现的参数
     */
    private float amount; //金额（精确到小数点2位 如：99.22）
    private String token; //用户token

    public UserAppWithDrawsRequestModel() {
        super();
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
