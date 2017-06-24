package com.phicomm.smarthome.sharedwifi.model.router;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.jpa
 * PACKAGE_NAME: com.fhicomm.sharedwifi.model
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/5
 */

@ApiModel(value = "RequestPingBeatsBodyModel", description = "路由器心跳包对象")
public class RequestPingBeatsBodyModel implements Serializable {
    public RequestPingBeatsBodyModel(){}

    @ApiModelProperty(value = "配置信息版本号")
    @JsonProperty("config_version")
    private String configVersion;

    @ApiModelProperty(value = "路由器GatewayID,终端类型")
    private String model;

    @ApiModelProperty(value = "路由器WLAN口IP")
    @JsonProperty("router_ip")
    private String routerIp;

    @ApiModelProperty(value = "路由器MAC地址")
    @JsonProperty("router_mac")
    private String routerMac;

    @ApiModelProperty(value = "路由器系统更新时间")
    @JsonProperty("router_uptime")
    private String routerUpTime;

    public RequestPingBeatsBodyModel(
            String configVersion,
            String model,
            String routerIp,
            String routerMac,
            String routerUpTime)
    {
        this.configVersion = configVersion;
        this.model = model;
        this.routerIp = routerIp;
        this.routerMac = routerMac;
        this.routerUpTime = routerUpTime;
    }

    public String getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(String configVersion) {
        this.configVersion = configVersion;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getRouterUpTime() {
        return routerUpTime;
    }

    public void setRouterUpTime(String routerUpTime) {
        this.routerUpTime = routerUpTime;
    }
}
