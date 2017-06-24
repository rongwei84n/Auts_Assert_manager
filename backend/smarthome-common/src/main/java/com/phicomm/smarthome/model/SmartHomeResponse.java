package com.phicomm.smarthome.model;

/**
 *
 */

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wenhua.tang
 *
 */

public class SmartHomeResponse<Object> {
	@JsonProperty("err_code")
	private int errCode; // 0正常调用 非0 异常

	@JsonProperty("err_msg")
	private String errMsg;

	private Object result;

	public SmartHomeResponse() {}

	public SmartHomeResponse(int errCode, String errMsg) {
	    this.errCode = errCode;
	    this.errMsg = errMsg;
	}

	public SmartHomeResponse(int errCode, String errMsg, Object obj) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.result = obj;
    }

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
