package com.phicomm.smarthome.sharedwifi.model.router;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.jpa
 * PACKAGE_NAME: com.fhicomm.sharedwifi.model
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/5
 */
@Entity
@Table(name = "sw_router_whitelist")
@ApiModel(value = "RouterWhiteListModel", description = "服务端白名单结果表集合")
public class RouterWhiteListModel implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @Column(name = "id")
    @JsonIgnore
    private long id;

    @ApiModelProperty(value = "配置文件Id")
    @Column(name = "config_id", columnDefinition="default ''")
    @JsonProperty("config_id")
    private long configId;

    @ApiModelProperty(value = "白名单主机名")
    @Column(name = "host")
    private String host;

    @ApiModelProperty(value = "白名单端口")
    @Column(name = "port")
    private int port;

    @ApiModelProperty(value = "白名单主机支持协议")
    @Column(name = "protocol")
    private String protocol;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time", columnDefinition="default 0")
    @JsonIgnore
    private long createTime;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "update_time", columnDefinition="default 0")
    @JsonIgnore
    private long updateTime;

    @ApiModelProperty(value = "当前路由器状态，状态 0 正常 -1 删除")
    @Column(name = "status")
    @JsonIgnore
    private Byte status;

    public RouterWhiteListModel(){}

    public RouterWhiteListModel(long id,
                                long configId,
                                String host,
                                int port,
                                String protocol,
                                long createTime,
                                long updateTime,
                                Byte status)
    {
        this.id = id;
        this.configId = configId;
        this.host = host;
        this.port = port;
        this.protocol = protocol;

        /** 20170619 16:27 Liang04 修复RouterWhiteListModel内创建时间及修改时间自动写入机制 */
        this.createTime = new Date().getTime()/1000;
        this.updateTime = updateTime;
        this.status = status;
    }

    public RouterWhiteListModel(long configId,
                                String host,
                                int port,
                                String protocol)
    {
        this.configId = configId;
        this.host = host;
        this.port = port;
        this.protocol = protocol;

        /** 20170619 16:27 Liang04 修复RouterWhiteListModel内创建时间及修改时间自动写入机制 */
        this.createTime = new Date().getTime()/1000;
        this.updateTime = new Date().getTime()/1000;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }
}
