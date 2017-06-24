package com.phicomm.smarthome.sharedwifi.model.router;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.sharedwifi
 * PACKAGE_NAME: com.fhicomm.sharedwifi.model
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/7
 */

public class ItemAuthResultModel {

    @ApiModelProperty(value = "用户终端mac对应权限")
    private int authority;

    @ApiModelProperty(value = "用户终端mac")
    @JsonProperty("device_mac")
    private String deviceMac;

    @ApiModelProperty(value = "用户终端mac对应订单ID")
    @JsonProperty("order_id")
    private String orderId;

    public ItemAuthResultModel(){}

    public ItemAuthResultModel(int authority,
                               String deviceMac,
                               String orderId){
        this.authority = authority;
        this.deviceMac = deviceMac;
        this.orderId = orderId;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(Byte authority) {
        this.authority = authority;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
