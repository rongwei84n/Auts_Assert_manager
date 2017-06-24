package com.phicomm.smarthome.sharedwifi.model.app;

/**
 * Portal页面下单，后台响应数据类 code_url: 二维码链接 order_id: 订单id
 * 
 * @author rongwei.huang
 *
 */
public class UserUnifiedOrder {
	private String code_url;
	private String mweb_url;
	private String order_id;
	private int ret_code;
	private String ret_err_desc;

	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public int getRet_code() {
		return ret_code;
	}

	public void setRet_code(int ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_err_desc() {
		return ret_err_desc;
	}

	public void setRet_err_desc(String ret_err_desc) {
		this.ret_err_desc = ret_err_desc;
	}

	public String getMweb_url() {
		return mweb_url;
	}

	public void setMweb_url(String mweb_url) {
		this.mweb_url = mweb_url;
	}

}
