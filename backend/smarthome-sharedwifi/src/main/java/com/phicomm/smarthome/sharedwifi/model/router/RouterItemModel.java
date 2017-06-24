package com.phicomm.smarthome.sharedwifi.model.router;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * PROJECT_NAME: liang04-smarthome-sharedwifi
 * PACKAGE_NAME: com.phicomm.smarthome.sharedwifi.model.router
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/12
 */
@Entity
@Table(name = "sw_router")
@ApiModel(value = "RouterItemModel", description = "服务端路由器Router实体表")
public class RouterItemModel implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @Column(name = "authResultId")
    @JsonIgnore
    private long id;

    @ApiModelProperty(value = "路由器IP")
    @Column(name = "router_ip", columnDefinition="default ''")
    @JsonProperty("router_ip")
    private String routerIp;

    @ApiModelProperty(value = "用户终端mac对应权限")
    @Column(name = "router_mac", columnDefinition="default ''")
    @JsonProperty("router_mac")
    private String routerMac;

    @ApiModelProperty(value = "路由器型号")
    @Column(name = "model", columnDefinition="default ''")
    @JsonProperty("model")
    private String model;

    @ApiModelProperty(value = "固件版本")
    @Column(name = "rom_version", columnDefinition="default ''")
    @JsonProperty("rom_version")
    private String romVersion;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time", columnDefinition="default 0")
    @JsonIgnore
    private long createTime;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "update_time", columnDefinition="default 0")
    @JsonIgnore
    private long updateTime;

    @ApiModelProperty(value = "当前路由器状态，状态 0 正常 -1 删除")
    @Column(name = "status", columnDefinition="default 0")
    @JsonIgnore
    private int status;

    public RouterItemModel(){}

    public RouterItemModel(String routerIp,
                           String routerMac,
                           String model,
                           String romVersion){
        this.routerIp = routerIp;
        this.routerMac = routerMac;
        this.model = model;
        this.romVersion = romVersion;

        /** 20170619 16:27 Liang04 修复RouterModel内创建时间及修改时间自动写入机制 */
        this.createTime = new Date().getTime()/1000;
        this.updateTime = new Date().getTime()/1000;
    }

    public RouterItemModel(long id,
                           String routerIp,
                           String routerMac,
                           String model,
                           String romVersion,
                           long createTime,
                           long updateTime){
        this.id = id;
        this.routerIp = routerIp;
        this.routerMac = routerMac;
        this.model = model;
        this.romVersion = romVersion;

        /** 20170619 16:27 Liang04 修复RouterModel内创建时间及修改时间自动写入机制 */
        this.createTime = new Date().getTime()/1000;
        this.updateTime = updateTime;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
