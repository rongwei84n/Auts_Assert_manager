package com.phicomm.smarthome.sharedwifi.model.router;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.jpa
 * PACKAGE_NAME: com.fhicomm.sharedwifi.model
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/5
 */
@Entity
@Table(name = "sw_router_config")
@ApiModel(value = "RouterConfigModel", description = "路由器配置参数表集合")
public class RouterConfigModel implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @Column(name = "id")
    @JsonIgnore
    private long id;

    @ApiModelProperty(value = "配置信息版本号")
    @Column(name = "configVersion", columnDefinition="default ''")
    @JsonProperty("config_version")
    private String configVersion;

    @ApiModelProperty(value = "路由器Id")
    @Column(name = "routerId", columnDefinition="default ''")
    @JsonIgnore
    private long routerId;

    @ApiModelProperty(value = "打赏功能开关")
    @Column(name = "isForbidden", columnDefinition="default ''")
    @JsonProperty("is_forbidden")
    private int isForbidden;

    @ApiModelProperty(value = "支付页URL")
    @Column(name = "portalUrl", columnDefinition="default ''")
    @JsonProperty("portal_url")
    private String portalUrl;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "createTime", columnDefinition="default 0")
    @JsonIgnore
    private long createTime;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "updateTime", columnDefinition="default 0")
    @JsonIgnore
    private long updateTime;

    public RouterConfigModel(){}

    public RouterConfigModel(String configVersion,
                             int isForbidden,
                             String portalUrl){
        this.configVersion = configVersion;
        this.isForbidden = isForbidden;
        this.portalUrl = portalUrl;
    }

    public RouterConfigModel(String configVersion,
                             long routerId,
                             int isForbidden,
                             String portalUrl){
        this.routerId = routerId;
        this.configVersion = configVersion;
        this.isForbidden = isForbidden;
        this.portalUrl = portalUrl;

        /** 20170619 16:27 Liang04 修复RouterConfigModel内创建时间及修改时间自动写入机制 */
        this.createTime = new Date().getTime()/1000;
        this.updateTime = new Date().getTime()/1000;
    }

    public RouterConfigModel(long id,
                             String configVersion,
                             long routerId,
                             int isForbidden,
                             String portalUrl,
                             long createTime,
                             long updateTime)
    {
        this.id = id;
        this.configVersion = configVersion;
        this.routerId = routerId;
        this.isForbidden = isForbidden;
        this.portalUrl = portalUrl;

        /** 20170619 16:27 Liang04 修复RouterConfigModel内创建时间及修改时间自动写入机制 */
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

    public String getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(String configVersion) {
        this.configVersion = configVersion;
    }

    public int getIsForbidden() {
        return isForbidden;
    }

    public void setIsForbidden(int isForbidden) {
        this.isForbidden = isForbidden;
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

    public String getPortalUrl() {
        return portalUrl;
    }

    public void setPortalUrl(String portalUrl) {
        this.portalUrl = portalUrl;
    }

    public long getRouterId() {
        return routerId;
    }

    public void setRouterId(long routerId) {
        this.routerId = routerId;
    }
}
