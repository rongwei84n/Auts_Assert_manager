package com.phicomm.smarthome.sharedwifi.controller.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.consts.Const.ResponseStatus;
import com.phicomm.smarthome.sharedwifi.model.app.request.UserAppWithDrawsRequestModel;
import com.phicomm.smarthome.sharedwifi.model.common.BaseResponseModel;

/**
 * 提现到微信帐号
 *
 * @author wenhua.tang
 *
 */
@RestController
public class AppWithDraws {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/app/with_draws", method = RequestMethod.POST, produces = { "application/json" })
	public SmartHomeResponse<BaseResponseModel> withDraws(@RequestBody UserAppWithDrawsRequestModel model) {
		SmartHomeResponse<BaseResponseModel> smartHomeResponse = new SmartHomeResponse<BaseResponseModel>();
		BaseResponseModel userWithDraws = new BaseResponseModel();

		logger.debug("withDraws amount: " + model.getAmount());
		int result = drawPreCheck(userWithDraws);

		userWithDraws.setRetCode(ResponseStatus.STAUS_OK);

		// 保存订单到数据库
		if (result == ResponseStatus.STAUS_OK) {
			result = saveOrderIntoDatabase(userWithDraws);
		}

		// 提现到微信
		if (result == ResponseStatus.STAUS_OK) {
			result = drawToWeChat();
		}

		smartHomeResponse.setErrCode(result);
		smartHomeResponse.setResult(userWithDraws);
		logger.debug("withDraws done");
		return smartHomeResponse;
	}

	/**
	 * 提现预检查 1. 这次提现和上次提现间隔时间，不能在同一个自然月 2. 提现金额不能大于目前的余额(客户端多次提交提现订单)
	 *
	 * @param userWithDraws
	 * @return
	 */
	private int drawPreCheck(BaseResponseModel userWithDraws) {
		return ResponseStatus.STAUS_OK;
	}

	private int saveOrderIntoDatabase(BaseResponseModel userWithDraws) {
		userWithDraws.setRetCode(ResponseStatus.STAUS_OK);
		userWithDraws.setRetMsg("");
		return ResponseStatus.STAUS_OK;
	}

	private int drawToWeChat() {
		// 提现到微信账号，临时方案先不实现。
		return ResponseStatus.STAUS_OK;
	}
}