package com.phicomm.smarthome.sharedwifi.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * PROJECT_NAME: liang04-smarthome-sharedwifi
 * PACKAGE_NAME: com.phicomm.smarthome.sharedwifi.model.user
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/13
 */

@Entity
@Table(name = "sw_guest")
@ApiModel(value = "Guest4RouterModel", description = "路由器访问者表集合")
public class Guest4RouterModel {

    @ApiModelProperty(value = "主键ID")
    @Column(name = "id")
    @JsonIgnore
    private long id;

    @ApiModelProperty(value = "用户的OpenId")
    @Column(name = "openId")
    @JsonIgnore
    private String openId;

    @ApiModelProperty(value = "手机设备mac")
    @Column(name = "deviceMac", columnDefinition="default ''")
    @JsonProperty("device_mac")
    private String deviceMac;

    @ApiModelProperty(value = "路由器Wan口ip")
    @Column(name = "routerIp", columnDefinition="default ''")
    @JsonProperty("router_ip")
    private String routerIp;

    @ApiModelProperty(value = "路由器mac地址")
    @Column(name = "routerMac", columnDefinition="default ''")
    @JsonProperty("router_mac")
    private String routerMac;

    @ApiModelProperty(value = "微信的nick name")
    @Column(name = "wxNickName", columnDefinition="default ''")
    @JsonProperty("wx_nickname")
    private String wxNickName;

    @ApiModelProperty(value = "微信头像Url")
    @Column(name = "wxHeadIconUrl", columnDefinition="default ''")
    @JsonProperty("wx_head_iconurl")
    private String wxHeadIconUrl;

    @ApiModelProperty(value = "手机电话号码")
    @Column(name = "iphone", columnDefinition="default ''")
    private String iphone;

    @ApiModelProperty(value = "访问者性别")
    @Column(name = "sex", columnDefinition="default 0")
    private byte sex;

    @ApiModelProperty(value = "邮编")
    @Column(name = "zipCode", columnDefinition="default ''")
    private String zipCode;

    @ApiModelProperty(value = "区域")
    @Column(name = "zone", columnDefinition="default ''")
    private String zone;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "createTime", columnDefinition="default 0")
    @JsonIgnore
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "updaterTime", columnDefinition="default 0")
    @JsonIgnore
    private String updaterTime;

    @ApiModelProperty(value = "条目状态")
    @Column(name = "status", columnDefinition="default 0")
    @JsonIgnore
    private byte status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

    public String getWxHeadIconUrl() {
        return wxHeadIconUrl;
    }

    public void setWxHeadIconUrl(String wxHeadIconUrl) {
        this.wxHeadIconUrl = wxHeadIconUrl;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdaterTime() {
        return updaterTime;
    }

    public void setUpdaterTime(String updaterTime) {
        this.updaterTime = updaterTime;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }
}
