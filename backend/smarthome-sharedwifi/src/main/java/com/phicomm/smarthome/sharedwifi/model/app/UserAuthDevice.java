package com.phicomm.smarthome.sharedwifi.model.app;

/**
 * 验证放行设备的订单id ret_code 0 验证成功 非0 验证失败 ret_msg 如果失败 失败原因
 * 
 * @author rongwei.huang
 *
 */
public class UserAuthDevice {
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
