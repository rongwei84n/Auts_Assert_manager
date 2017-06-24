/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.consts;

/**
 * @author wenhua.tang
 *
 */
public interface WxConst {
	String deviceInfo = "WEB";
	String body = "斐讯路由|打赏网络|扫码支付";
	String h5Body = "斐讯路由|打赏网络|H5支付";
	String feeType = "CNY";
	String productID = "A01S01";
	String h5ProductID = "A01501";
	String tradeType = "NATIVE";
	String h5TradeType = "MWEB";
	String limitPay = "no_credit";
	String notifyUrl = "http://sharedwifi.phicomm.com:8000/sharedwifi/v1/h5web/wxpay.action";
}
