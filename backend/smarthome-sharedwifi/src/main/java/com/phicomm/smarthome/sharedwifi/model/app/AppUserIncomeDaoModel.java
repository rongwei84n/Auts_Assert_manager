package com.phicomm.smarthome.sharedwifi.model.app;

import com.phicomm.smarthome.sharedwifi.model.common.BaseDaoModel;

public class AppUserIncomeDaoModel extends BaseDaoModel {
	// 斐讯云账号uid
	private String uid;

	// 路由器mac地址
	private String routerMac;

	// 今天收益
	private String todayIncome;

	// 总收益（可以用来提现的）
	private String totalIncome;

	private long createTime;

	private long updateTime;

	// 状态
	private int status;

	// 哪天产生的收入
	private String todayDate;

	// 订单号
	private String orderId;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRouterMac() {
		return routerMac;
	}

	public void setRouterMac(String routerMac) {
		this.routerMac = routerMac;
	}

	public String getTodayIncome() {
		return todayIncome;
	}

	public void setTodayIncome(String todayIncome) {
		this.todayIncome = todayIncome;
	}

	public String getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTodayDate() {
		return todayDate;
	}

	public void setTodayDate(String todayDate) {
		this.todayDate = todayDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {

		return "uid=" + uid + ",router_mac=" + routerMac + ",todayIncome=" + todayIncome + ",totalIncome=" + totalIncome
				+ "order_id=" + orderId;
	}
}
