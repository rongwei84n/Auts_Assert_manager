/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.model.h5web;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wenhua.tang
 *
 */
public class UnifiedOrderRequest {
	@JsonProperty("device_mac")
	private String deviceMac;

	@JsonProperty("router_mac")
	private String routerMac;

	@JsonProperty("online_time")
	private String onlineTime;

	@JsonProperty("online_time_unit")
	private String onlineTimeUnit;

	@JsonProperty("online_time_unit_price")
	private String onlineTimeUnitPrice;

	@JsonProperty("router_ip")
	private String routerIp;

	@JsonProperty("router_port")
	private String routerPort;

	@JsonProperty("ssid")
	private String ssid;

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public String getRouterMac() {
		return routerMac;
	}

	public void setRouterMac(String routerMac) {
		this.routerMac = routerMac;
	}

	public String getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getOnlineTimeUnit() {
		return onlineTimeUnit;
	}

	public void setOnlineTimeUnit(String onlineTimeUnit) {
		this.onlineTimeUnit = onlineTimeUnit;
	}

	public String getOnlineTimeUnitPrice() {
		return onlineTimeUnitPrice;
	}

	public void setOnlineTimeUnitPrice(String onlineTimeUnitPrice) {
		this.onlineTimeUnitPrice = onlineTimeUnitPrice;
	}

	public String getRouterIp() {
		return routerIp;
	}

	public void setRouterIp(String routerIp) {
		this.routerIp = routerIp;
	}

	public String getRouterPort() {
		return routerPort;
	}

	public void setRouterPort(String routerPort) {
		this.routerPort = routerPort;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

}
