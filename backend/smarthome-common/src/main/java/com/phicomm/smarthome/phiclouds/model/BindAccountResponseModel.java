/**
 * 
 */
package com.phicomm.smarthome.phiclouds.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wenhua.tang
 *
 */
public class BindAccountResponseModel {
	private List<PhiCloudData> data;
	private String error;
	private String message;

	@JsonProperty("token_status")
	private String tokenStatus;

	public List<PhiCloudData> getData() {
		return data;
	}

	public void setData(List<PhiCloudData> data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTokenStatus() {
		return tokenStatus;
	}

	public void setTokenStatus(String tokenStatus) {
		this.tokenStatus = tokenStatus;
	}

}
