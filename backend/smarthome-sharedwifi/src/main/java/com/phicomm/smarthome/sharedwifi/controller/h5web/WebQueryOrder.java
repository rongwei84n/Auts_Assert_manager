/**
 *
 */
package com.phicomm.smarthome.sharedwifi.controller.h5web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.phiclouds.model.PhiCloudData;
import com.phicomm.smarthome.sharedwifi.consts.Const.OrderPayStatusInterfaces;
import com.phicomm.smarthome.sharedwifi.consts.Const.ResponseStatus;
import com.phicomm.smarthome.sharedwifi.middleware.GetPhiCloudMacUid;
import com.phicomm.smarthome.sharedwifi.model.app.AppUserIncomeDaoModel;
import com.phicomm.smarthome.sharedwifi.model.h5web.GuestOrder;
import com.phicomm.smarthome.sharedwifi.model.h5web.QueryOrderStatusRequest;
import com.phicomm.smarthome.sharedwifi.model.h5web.UserQueryOrderStatus;
import com.phicomm.smarthome.sharedwifi.protocol.pay_query.ScanPayQueryReqData;
import com.phicomm.smarthome.sharedwifi.protocol.pay_query.ScanPayQueryResData;
import com.phicomm.smarthome.sharedwifi.service.impl.GuestOrderStatusServiceImpl;
import com.phicomm.smarthome.sharedwifi.service.impl.ScanPayQueryService;
import com.phicomm.smarthome.sharedwifi.service.impl.UserIncomeTransactionalImpl;
import com.phicomm.smarthome.sharedwifi.util.MyListUtils;
import com.phicomm.smarthome.sharedwifi.util.MyResponseutils;
import com.phicomm.smarthome.sharedwifi.util.Signature;
import com.phicomm.smarthome.sharedwifi.util.Util;
import com.phicomm.smarthome.util.StringUtil;

/**
 * @author wenhua.tang
 *
 */
@RestController
public class WebQueryOrder {
	private static final Logger logger = LogManager.getLogger(WebQueryOrder.class);

	private static final ScanPayQueryService scanPayQueryService = new ScanPayQueryService();

	@Autowired
	private GuestOrderStatusServiceImpl guestOrderStatusServiceImpl;

	@Autowired
	private UserIncomeTransactionalImpl userIncomeTransactionalImpl;

	@RequestMapping(value = "/h5web/queryorder", method = RequestMethod.POST, produces = { "application/json" })
	public SmartHomeResponse<Object> queryorder(@RequestBody QueryOrderStatusRequest queryOrderStatusRequest) {

		UserQueryOrderStatus statusBean = new UserQueryOrderStatus();

		if (queryOrderStatusRequest == null) {
			logger.error("request para is empty");
			SmartHomeResponse<Object> smartHomeResponseT = MyResponseutils.geResponse(null);
			smartHomeResponseT.setErrCode(ResponseStatus.STAUS_LESS_PARA);
			smartHomeResponseT.setErrMsg("请求参数为空");
			return smartHomeResponseT;
		}

		List<GuestOrder> orderList = guestOrderStatusServiceImpl
				.getOrderPaiedStatus(queryOrderStatusRequest.getOrderId());
		if (MyListUtils.isEmpty(orderList)) {
			logger.error("order_id[{}] can not be finded in the db", queryOrderStatusRequest.getOrderId());
			statusBean.setRet_code(ResponseStatus.STAUS_LESS_PARA);
			statusBean.setRet_msg("订单不存在");
			return MyResponseutils.geResponse(statusBean);
		}

		if (orderList.get(0).getOrderPayStatus() == OrderPayStatusInterfaces.ORDER_PAY_STATUS_PAID) {
			statusBean.setRet_msg("订单已支付");
			statusBean.setRet_code(ResponseStatus.STAUS_ORDER_PAYOK);
			return MyResponseutils.geResponse(statusBean);
		}

		String payQueryResponseString = "";
		try {
			ScanPayQueryReqData scanPayQueryReqData = new ScanPayQueryReqData("", queryOrderStatusRequest.getOrderId());
			payQueryResponseString = scanPayQueryService.request(scanPayQueryReqData);

			// 将从API返回的XML数据映射到Java对象
			ScanPayQueryResData scanPayQueryResData = (ScanPayQueryResData) Util
					.getObjectFromXML(payQueryResponseString, ScanPayQueryResData.class);

			if (scanPayQueryResData == null || scanPayQueryResData.getReturn_code() == null
					|| scanPayQueryResData.getReturn_code().equals("FAIL")) {
				SmartHomeResponse<Object> smartHomeResponseT = MyResponseutils.geResponse(null);
				smartHomeResponseT.setErrCode(ResponseStatus.STAUS_ERROR);
				smartHomeResponseT.setErrMsg("查询失败，请仔细检测传过去的每一个参数是否合法");
				return smartHomeResponseT;
			}

			// 查询成功,收到API的返回数据的时候得先验证一下数据有没有被第三方篡改，确保安全
			if (!Signature.checkIsSignValidFromResponseString(payQueryResponseString)) {
				statusBean.setRet_code(ResponseStatus.STAUS_ERROR);
				statusBean.setRet_msg("支付请求API返回的数据签名验证失败，有可能数据被篡改了");
				return MyResponseutils.geResponse(statusBean);
			}

			// 获取错误码
			String errorCode = scanPayQueryResData.getErr_code();
			String errorCodeDes = scanPayQueryResData.getErr_code_des();

			if (!scanPayQueryResData.getResult_code().equals("SUCCESS")) {
				statusBean.setRet_code(ResponseStatus.STAUS_ORDER_FAILED);
				statusBean.setRet_msg(errorCodeDes);
				return MyResponseutils.geResponse(statusBean);
			}

			if (scanPayQueryResData.getTrade_state().equals("SUCCESS")) {
				PhiCloudData cloudData = GetPhiCloudMacUid.getRouterMacBindMsg(queryOrderStatusRequest.getRouterMac());
				if (cloudData == null || StringUtil.isNullOrEmpty(cloudData.getUid())) {
					logger.error("Mac[{}] 向斐讯云获取uid失败", queryOrderStatusRequest.getRouterMac());
					statusBean.setRet_code(ResponseStatus.STAUS_ERROR);
					statusBean.setRet_msg("路由器没有绑定用户");
					return MyResponseutils.geResponse(statusBean);
				}

				// 更新收入分成
				AppUserIncomeDaoModel appUserIncomeDaoModel = new AppUserIncomeDaoModel();
				appUserIncomeDaoModel.setOrderId(queryOrderStatusRequest.getOrderId());
				appUserIncomeDaoModel.setRouterMac(queryOrderStatusRequest.getRouterMac());
				appUserIncomeDaoModel.setCreateTime(System.currentTimeMillis() / 1000);
				appUserIncomeDaoModel.setUid(cloudData.getUid());
				appUserIncomeDaoModel.setUpdateTime(System.currentTimeMillis() / 1000);
				appUserIncomeDaoModel.setTodayIncome("0.02");
				appUserIncomeDaoModel.setTotalIncome("0.02");
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
				String todayDate = df.format(new Date());
				appUserIncomeDaoModel.setTodayDate(todayDate);

				int affected = userIncomeTransactionalImpl.updateUserIncomeTransaction(appUserIncomeDaoModel);
				if (affected <= 0) {
					logger.error("订单查询失败 order[{}]", queryOrderStatusRequest.getOrderId());
					statusBean.setRet_code(ResponseStatus.STAUS_ERROR);
					statusBean.setRet_msg("订单查询失败");
					return MyResponseutils.geResponse(statusBean);
				}

				statusBean.setRet_code(ResponseStatus.STAUS_ORDER_PAYOK);

			} else if (scanPayQueryResData.getTrade_state().equals("NOTPAY")) {
				// 未支付
				statusBean.setRet_code(ResponseStatus.STAUS_ORDER_NOTPAY);
			} else if (scanPayQueryResData.getTrade_state().equals("CLOSED")) {
				// 已关闭
				statusBean.setRet_code(ResponseStatus.STAUS_ORDER_CLOSED);
			} else if (scanPayQueryResData.getTrade_state().equals("USERPAYING")) {
				// 用户支付中
				statusBean.setRet_code(ResponseStatus.STAUS_ORDER_USERPAYING);
			} else if (scanPayQueryResData.getTrade_state().equals("PAYERROR")) {
				// 支付失败
				statusBean.setRet_code(ResponseStatus.STAUS_ORDER_PAYERROR);
			} else if (scanPayQueryResData.getTrade_state().equals("REFUND")) {
				// 转入退款
				statusBean.setRet_code(ResponseStatus.STAUS_ORDER_REFUND);
			} else if (scanPayQueryResData.getTrade_state().equals("REVOKED")) {
				// 已撤销（刷卡支付）
				statusBean.setRet_code(ResponseStatus.STAUS_ORDER_REVOKED);
			} else {
				statusBean.setRet_code(ResponseStatus.STAUS_ORDER_PAYERROR);
			}
			statusBean.setRet_msg(scanPayQueryResData.getTrade_state_desc());

		} catch (Exception e) {
			logger.error(e);
			statusBean.setRet_msg("查询出错");
		}

		return MyResponseutils.geResponse(statusBean);

	}
}
