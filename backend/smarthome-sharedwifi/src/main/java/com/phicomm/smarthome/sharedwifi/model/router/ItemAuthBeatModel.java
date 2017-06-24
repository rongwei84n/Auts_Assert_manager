package com.phicomm.smarthome.sharedwifi.model.router;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.sharedwifi
 * PACKAGE_NAME: com.fhicomm.sharedwifi.model
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/7
 */


public class ItemAuthBeatModel {

    @ApiModelProperty(value = "终端MAC地址")
    @JsonProperty("device_mac")
    private String deviceMac;

    @ApiModelProperty(value = "终端支付订单ID")
    @JsonProperty("order_id")
    private String orderId;

    public ItemAuthBeatModel(){}

    public ItemAuthBeatModel(String deviceMac, String orderId){
        this.deviceMac = deviceMac;
        this.orderId = orderId;
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
