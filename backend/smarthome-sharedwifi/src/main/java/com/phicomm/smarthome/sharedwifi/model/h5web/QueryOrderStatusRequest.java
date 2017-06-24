/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.model.h5web;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wenhua.tang
 *
 */
public class QueryOrderStatusRequest {
	@JsonProperty("device_mac")
	private String deviceMac;
	@JsonProperty("order_id")
	private String orderId;
	@JsonProperty("router_mac")
	private String routerMac;

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRouterMac() {
		return routerMac;
	}

	public void setRouterMac(String routerMac) {
		this.routerMac = routerMac;
	}

}
