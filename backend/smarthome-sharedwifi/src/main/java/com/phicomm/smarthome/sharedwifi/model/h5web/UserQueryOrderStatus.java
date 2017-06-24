package com.phicomm.smarthome.sharedwifi.model.h5web;


/**
 * Portal页面查询订单状态数据类
 * ret_code: 0 支付成功, 非0 支付失败
 * ret_msg:	支付失败原因
 * @author rongwei.huang
 *
 */
public class UserQueryOrderStatus {
	private int ret_code;
	private String ret_msg;
	
	public int getRet_code() {
		return ret_code;
	}
	
	public void setRet_code(int ret_code) {
		this.ret_code = ret_code;
	}
	
	public String getRet_msg() {
		return ret_msg;
	}
	
	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}
}
