/**
 *
 */
package com.phicomm.smarthome.sharedwifi.controller.h5web;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.phiclouds.model.PhiCloudData;
import com.phicomm.smarthome.sharedwifi.consts.Const.OrderStatusInterfaces;
import com.phicomm.smarthome.sharedwifi.consts.Const.ResponseStatus;
import com.phicomm.smarthome.sharedwifi.consts.WxConst;
import com.phicomm.smarthome.sharedwifi.middleware.GetPhiCloudMacUid;
import com.phicomm.smarthome.sharedwifi.model.app.UserUnifiedOrder;
import com.phicomm.smarthome.sharedwifi.model.h5web.GuestOrder;
import com.phicomm.smarthome.sharedwifi.model.h5web.UnifiedOrderRequest;
import com.phicomm.smarthome.sharedwifi.protocol.unifiedorder.UnifiedorderReqData;
import com.phicomm.smarthome.sharedwifi.protocol.unifiedorder.UnifiedorderResData;
import com.phicomm.smarthome.sharedwifi.service.impl.GuestOrderStatusServiceImpl;
import com.phicomm.smarthome.sharedwifi.service.impl.GuestServiceImpl;
import com.phicomm.smarthome.sharedwifi.service.impl.OrderIdGeneratorServiceImpl;
import com.phicomm.smarthome.sharedwifi.service.impl.UnifiedorderService;
import com.phicomm.smarthome.sharedwifi.util.IpUtil;
import com.phicomm.smarthome.sharedwifi.util.MyResponseutils;
import com.phicomm.smarthome.sharedwifi.util.Signature;
import com.phicomm.smarthome.sharedwifi.util.Util;
import com.phicomm.smarthome.util.StringUtil;

/**
 * @author wenhua.tang
 *
 */
@CrossOrigin(origins = "*")
@RestController
public class WebUnifiedOrder {
	private final static Logger logger = LogManager.getLogger(WebUnifiedOrder.class);

	@Autowired
	private OrderIdGeneratorServiceImpl orderIdGeneratorServiceImpl;

	@Autowired
	private GuestOrderStatusServiceImpl guestOrderStatusServiceImpl;

	@Autowired
	private GuestServiceImpl guestServiceImpl;

	private static final UnifiedorderService unifiedorderService = new UnifiedorderService();

	@RequestMapping(value = "/h5web/unifiedorder", method = RequestMethod.POST, produces = { "application/json" })
	public SmartHomeResponse<Object> unifiedorder(@RequestBody UnifiedOrderRequest unifiedOrderRequest, HttpServletRequest request) {

		if (unifiedOrderRequest == null) {
			logger.error("unifiedOrderRequest is null");
			SmartHomeResponse<Object> response = MyResponseutils.geResponse(null);
			response.setErrCode(ResponseStatus.STAUS_ERROR);
			response.setErrMsg("请求参数为空");
			return response;
		}

		if (StringUtil.isNullOrEmpty(unifiedOrderRequest.getRouterMac())) {
			logger.error("router_mac is null");
			SmartHomeResponse<Object> response = MyResponseutils.geResponse(null);
			response.setErrCode(ResponseStatus.STAUS_ERROR);
			response.setErrMsg("路由mac为空");
			return response;
		}

		PhiCloudData cloudData = GetPhiCloudMacUid.getRouterMacBindMsg(unifiedOrderRequest.getRouterMac());
		if (cloudData == null || StringUtil.isNullOrEmpty(cloudData.getUid())) {
			logger.error("router_mac is null");
			SmartHomeResponse<Object> response = MyResponseutils.geResponse(null);
			response.setErrCode(ResponseStatus.STAUS_ERROR);
			response.setErrMsg("路由mac未绑定phicomm用户");
			return response;
		}

		String tradeType = "";
		String productId = "";
		String body = "";
		String userAgent = request.getHeader("User-Agent");
		if (StringUtil.isNullOrEmpty(userAgent)) {
			logger.error("the request header can not get User-Agent");
			SmartHomeResponse<Object> response = MyResponseutils.geResponse(null);
			response.setErrCode(ResponseStatus.STAUS_ERROR);
			response.setErrMsg("the request header can not get User-Agent");
			return response;
		}

		if (StringUtil.isMobile(userAgent.toLowerCase())) {
			tradeType = WxConst.h5TradeType;
			productId = WxConst.h5ProductID;
			body = WxConst.h5Body;
		} else {
			tradeType = WxConst.tradeType;
			productId = WxConst.productID;
			body = WxConst.body;
		}

		UserUnifiedOrder userUnifiedOrder = new UserUnifiedOrder();

		String orderId = orderIdGeneratorServiceImpl.getOrderId();
		if (StringUtils.isEmpty(orderId)) {
			logger.error("generate orderID [{}]  error", orderId);
			userUnifiedOrder.setRet_code(ResponseStatus.STAUS_ORDER_FAILED);
			userUnifiedOrder.setRet_err_desc("下单失败");
			return MyResponseutils.geResponse(userUnifiedOrder);
		}

		int totalFee = 3;// 1块钱 100
		java.util.Date date = new java.util.Date();
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化当前系统日期
		String createOrderTime = dateFm.format(date);

		java.util.Calendar Cal = java.util.Calendar.getInstance();
		Cal.setTime(date);
		Cal.add(java.util.Calendar.HOUR_OF_DAY, 2);
		String expireOrderTime = dateFm.format(Cal.getTime());

		UnifiedorderReqData unifiedorderReqData = new UnifiedorderReqData(WxConst.deviceInfo, body, orderId, WxConst.feeType, totalFee,
				IpUtil.getClientIP(request), createOrderTime, expireOrderTime, WxConst.notifyUrl, tradeType, productId, WxConst.limitPay);

		String unifiedorderServiceResponseString = "";
		try {
			unifiedorderServiceResponseString = unifiedorderService.request(unifiedorderReqData);
			// 将从API返回的XML数据映射到Java对象
			UnifiedorderResData unifiedorderResData = (UnifiedorderResData) Util.getObjectFromXML(unifiedorderServiceResponseString,
					UnifiedorderResData.class);

			if (unifiedorderResData == null || unifiedorderResData.getReturn_code() == null
					|| unifiedorderResData.getReturn_code().equals("FAIL")) {
				logger.error("【支付失败】支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
				userUnifiedOrder.setRet_code(ResponseStatus.STAUS_ORDER_FAILED);
				userUnifiedOrder.setRet_err_desc("请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
				return MyResponseutils.geResponse(userUnifiedOrder);
			}

			// 下单成功
			// 收到API的返回数据的时候得先验证一下数据有没有被第三方篡改，确保安全
			// --------------------------------------------------------------------
			if (!Signature.checkIsSignValidFromResponseString(unifiedorderServiceResponseString)) {
				logger.error("【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了");
				userUnifiedOrder.setRet_code(ResponseStatus.STAUS_ORDER_FAILED);
				userUnifiedOrder.setRet_err_desc("支付请求API返回的数据签名验证失败，有可能数据被篡改了");
				return MyResponseutils.geResponse(userUnifiedOrder);
			}

			// 获取错误描述
			// 获取错误码
			String errorCode = unifiedorderResData.getErr_code();
			String errorCodeDes = unifiedorderResData.getErr_code_des();
			if (unifiedorderResData.getResult_code().equals("SUCCESS")) {
				// 根据参数查询出guest id
				long guestId = guestServiceImpl.findByName(unifiedOrderRequest.getRouterMac(), unifiedOrderRequest.getDeviceMac());
				if (guestId <= 0) {
					logger.error("router mac[{}] device mac[{}] have not register guest", unifiedOrderRequest.getRouterMac(),
							unifiedOrderRequest.getDeviceMac());
					userUnifiedOrder.setRet_code(ResponseStatus.STAUS_ORDER_FAILED);
					userUnifiedOrder.setRet_err_desc("设备对应不上");
					return MyResponseutils.geResponse(userUnifiedOrder);
				}

				// 入订单库
				GuestOrder guestOrder = new GuestOrder();
				guestOrder.setOrderId(orderId);
				guestOrder.setGuestId(guestId);
				guestOrder.setUserShareParaId(1L);
				guestOrder.setOrderStatus(OrderStatusInterfaces.ORDER_STATUS_UNPAID);
				guestOrder.setOnlineTimeTotal(0.1);
				guestOrder.setOnlineTimeTotalCost("3");
				guestOrder.setCreateTime(System.currentTimeMillis() / 1000);
				guestOrder.setUpdateTime(System.currentTimeMillis() / 1000);

				int affected = guestOrderStatusServiceImpl.insertGuestOrder(guestOrder);
				if (affected <= 0) {
					logger.error("订单持久化失败[%s]", guestOrder);
					userUnifiedOrder.setRet_code(ResponseStatus.STAUS_ORDER_FAILED);
					userUnifiedOrder.setRet_err_desc("订单持久化失败");
					return MyResponseutils.geResponse(userUnifiedOrder);
				}

				userUnifiedOrder.setRet_code(ResponseStatus.STAUS_OK);
				userUnifiedOrder.setRet_err_desc("下单成功");
				if (!StringUtil.isNullOrEmpty(unifiedorderResData.getCode_url())) {
					userUnifiedOrder.setCode_url(unifiedorderResData.getCode_url());
				}

				if (!StringUtil.isNullOrEmpty(unifiedorderResData.getMweb_url())) {
					userUnifiedOrder.setMweb_url(unifiedorderResData.getMweb_url() + "&redirect_url="
							+ URLEncoder.encode("http://http://172.31.34.8:8000/sharedwifiweb/v1/pay_success.html?" + "order_id=" + orderId
									+ "&trade_state=1" + "&device_mac=" + unifiedOrderRequest.getDeviceMac() + "&router_mac="
									+ unifiedOrderRequest.getRouterMac() + "&router_ip=" + unifiedOrderRequest.getRouterIp()
									+ "&router_port=" + unifiedOrderRequest.getRouterPort() + "&online_time_unit="
									+ unifiedOrderRequest.getOnlineTimeUnit(), "UTF-8"));
				}

				userUnifiedOrder.setOrder_id(orderId);
				return MyResponseutils.geResponse(userUnifiedOrder);
			}

			// 业务错误时错误码有好几种，商户重点提示以下几种
			if (errorCode.equals("AUTHCODEEXPIRE") || errorCode.equals("AUTH_CODE_INVALID") || errorCode.equals("NOTENOUGH")) {

			} else if (errorCode.equals("USERPAYING")) {

			} else {

			}
		} catch (Exception e) {
			logger.error(e);
		}
		return MyResponseutils.geResponse(userUnifiedOrder);
	}
}
