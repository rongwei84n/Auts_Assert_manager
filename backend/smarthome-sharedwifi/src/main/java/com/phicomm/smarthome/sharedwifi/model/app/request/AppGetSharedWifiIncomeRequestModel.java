package com.phicomm.smarthome.sharedwifi.model.app.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * App的详情页面的“今日收益”和“累计收益” 的请求参数接口
 * @author rongwei.huang
 *
 */
public class AppGetSharedWifiIncomeRequestModel implements Serializable {
    
    @JsonProperty("router_mac")
    private String routerMac;
    
    private String token;

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
