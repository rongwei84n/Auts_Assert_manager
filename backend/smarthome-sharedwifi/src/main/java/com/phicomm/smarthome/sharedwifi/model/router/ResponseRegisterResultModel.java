package com.phicomm.smarthome.sharedwifi.model.router;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.sharedwifi
 * PACKAGE_NAME: com.fhicomm.sharedwifi.model.common
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/8
 */
public class ResponseRegisterResultModel {

    @ApiModelProperty(value = "是否允许注册(0:允许 1不允许)")
    @JsonProperty("is_allow_registering")
    private int isAllowRegistering;

    @ApiModelProperty(value = "是否需要下次再来连(0是 1否)")
    @JsonProperty("is_retry_nextTime")
    private int isRetryNextTime;

    @ApiModelProperty(value = "不能注册的原因")
    private String reason;

    @ApiModelProperty(value = "下次注册的相对时间(1month 秒级),long类型")
    @JsonProperty("retry_time")
    private long retryTime;

    public ResponseRegisterResultModel(){}

    public ResponseRegisterResultModel(int isAllowRegistering, int isRetryNextTime, String reason, long retryTime){
        this.isAllowRegistering = isAllowRegistering;
        this.isRetryNextTime  =isRetryNextTime;
        this.reason = reason;
        this.retryTime = retryTime;
    }

    public int getIsAllowRegistering() {
        return isAllowRegistering;
    }

    public void setIsAllowRegistering(int isAllowRegistering) {
        this.isAllowRegistering = isAllowRegistering;
    }

    public int getIsRetryNextTime() {
        return isRetryNextTime;
    }

    public void setIsRetryNextTime(int isRetryNextTime) {
        this.isRetryNextTime = isRetryNextTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(long retryTime) {
        this.retryTime = retryTime;
    }
}
