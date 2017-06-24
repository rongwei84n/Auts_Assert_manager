/**
 *
 */
package com.phicomm.smarthome.sharedwifi.util;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.consts.Const.ResponseStatus;

/**
 * @author wenhua.tang
 *
 */
public class MyResponseutils {
	public static SmartHomeResponse<Object> geResponse(Object result) {
		SmartHomeResponse<Object> smartHomeResponseT = new SmartHomeResponse<Object>();
		smartHomeResponseT.setResult(result);
		return smartHomeResponseT;
	}

	public static String parseMsg(int code) {
	    switch (code) {
        case ResponseStatus.STAUS_OK:
            return "成功";
        case ResponseStatus.STAUS_COMMON_FAILED:
            return "请求失败";
        case ResponseStatus.STAUS_ORDER_FAILED:
            return "下单失败";
        case ResponseStatus.STAUS_WITH_DRAW_TOO_OFEN:
            return "本月已提现，请下个月再来";
        case ResponseStatus.STAUS_WITH_DRAW_NO_BALANCE:
            return "提现金额不足 0.04 元";
        case ResponseStatus.STAUS_NO_PARA_IN_REQUEST:
            return "请求中参数不足";
        case ResponseStatus.STAUS_NO_TOKEN_IN_REQUEST:
            return "请求中缺少Token";
        case ResponseStatus.STAUS_NO_OPEN_ID_IN_REQUEST:
            return "请求中缺少Open ID";
        case ResponseStatus.STAUS_ALIPAY_ACCOUNT_IN_REQUEST://请求参数中没有支付宝账号
            return "请输入支付宝绑定的邮箱或手机号";
        case ResponseStatus.STAUS_FAILED_GET_PHICOMM_ACCOUNT:
            return "获取斐讯云账户失败，请重新登录";
        case ResponseStatus.STAUS_DATABASE_OPERATE_ERROR:
            return "操作数据库失败";
        case ResponseStatus.STAUS_BIND_OPENID_UID_ALREADY:
            return "Open ID已经被绑定";
        case ResponseStatus.STAUS_NO_ROUTER_MAC_IN_REQUEST:
            return "请求中没有路由器MAC地址";
        default:
            return "请求失败";
        }
	}
}
