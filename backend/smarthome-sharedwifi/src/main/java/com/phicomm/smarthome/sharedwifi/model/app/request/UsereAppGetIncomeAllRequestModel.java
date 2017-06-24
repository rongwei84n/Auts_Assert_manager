package com.phicomm.smarthome.sharedwifi.model.app.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 查询用户账户总余额用户参数
 * @author rongwei.huang
 *
 */
public class UsereAppGetIncomeAllRequestModel implements Serializable {

    @JsonProperty("router_mac")
    private String routerMac;  //路由器mac
    private String token;       // 用户token

    public UsereAppGetIncomeAllRequestModel() {
        super();
    }

    public String getRouterMac() {
        return routerMac;
    }

    public void setRouterMac(String routerMac) {
        this.routerMac = routerMac;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
