/**
 * 
 */
package com.phicomm.smarthome.model;

/**
 * @author wenhua.tang
 *
 */
public class Guest {
	private Long id;
	private String openId;
	private String deviceMac;
	private String routerIp;
	private String routerMac;
	private String wxNickName;
	private String wxHeadIconUrl;
	private String iphone;
	private int sex;
	private String zipCode;
	private String zone;
	private Long createTime;
	private Long updateTime;
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
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

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
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

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
