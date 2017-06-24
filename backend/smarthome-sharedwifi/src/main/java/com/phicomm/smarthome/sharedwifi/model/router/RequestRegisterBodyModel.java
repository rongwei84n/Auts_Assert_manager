package com.phicomm.smarthome.sharedwifi.model.router;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.sharedwifi
 * PACKAGE_NAME: com.fhicomm.sharedwifi.model
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/8
 */
public class RequestRegisterBodyModel {

    @ApiModelProperty(value = "路由器类型")
    private String model;

    @ApiModelProperty(value = "路由器固件版本")
    @JsonProperty("rom_version")
    private String romVersion;

    @ApiModelProperty(value = "路由器WAN口IP")
    @JsonProperty("router_ip")
    private String routerIp;

    @ApiModelProperty(value = "路由器MAC地址")
    @JsonProperty("router_mac")
    private String routerMac;

    public RequestRegisterBodyModel(){}

    public RequestRegisterBodyModel(String model, String romVersion, String routerIp, String routerMac){
        this.model = model;
        this.romVersion = romVersion;
        this.routerIp = routerIp;
        this.routerMac = routerMac;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRomVersion() {
        return romVersion;
    }

    public void setRomVersion(String romVersion) {
        this.romVersion = romVersion;
    }

    public String getRouterIp() {
        return routerIp;
    }

    public void setRouterIp(String routerIp) {
        this.routerIp = routerIp;
    }

    public String getRouterMac() {
        return routerMac;
    }

    public void setRouterMac(String routerMac) {
        this.routerMac = routerMac;
    }
}
