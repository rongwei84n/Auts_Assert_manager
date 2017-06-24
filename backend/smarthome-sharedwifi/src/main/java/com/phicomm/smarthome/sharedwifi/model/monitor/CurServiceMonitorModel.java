package com.phicomm.smarthome.sharedwifi.model.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * PROJECT_NAME: liang04-smarthome-sharedwifi
 * PACKAGE_NAME: com.phicomm.smarthome.sharedwifi.model.status
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/20
 */
public class CurServiceMonitorModel implements Serializable {

    @ApiModelProperty(value = "服务名称")
    @JsonProperty("service_name")
    private String serviceName;

    @ApiModelProperty(value = "服务名称")
    @JsonProperty("service_version")
    private String serviceVersion;

    @ApiModelProperty(value = "服务名称")
    @JsonProperty("service_status")
    private String serviceStatus;

    @ApiModelProperty(value = "服务名称")
    @JsonProperty("service_db_name")
    private String serviceDBName;

    @ApiModelProperty(value = "服务名称")
    @JsonProperty("service_db_status")
    private String serviceDBStatus;

    public CurServiceMonitorModel(){}

    public CurServiceMonitorModel(String serviceName,
                                  String serviceVersion,
                                  String serviceStatus,
                                  String serviceDBName,
                                  String serviceDBStatus){
        this.serviceName = serviceName;
        this.serviceVersion = serviceVersion;
        this.serviceStatus = serviceStatus;
        this.serviceDBName = serviceDBName;
        this.serviceDBStatus = serviceDBStatus;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDBName() {
        return serviceDBName;
    }

    public void setServiceDBName(String serviceDBName) {
        this.serviceDBName = serviceDBName;
    }

    public String getServiceDBStatus() {
        return serviceDBStatus;
    }

    public void setServiceDBStatus(String serviceDBStatus) {
        this.serviceDBStatus = serviceDBStatus;
    }
}
