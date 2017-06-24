package com.phicomm.smarthome.sharedwifi.controller.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.consts.Const;
import com.phicomm.smarthome.sharedwifi.consts.Const.ResponseStatus;
import com.phicomm.smarthome.sharedwifi.controller.common.BaseController;
import com.phicomm.smarthome.sharedwifi.model.app.responses.UserIncomeAllResponseModel;
import com.phicomm.smarthome.sharedwifi.model.app.request.UsereAppGetIncomeAllRequestModel;
import com.phicomm.smarthome.sharedwifi.model.common.PhicommAccountDetailModel;
import com.phicomm.smarthome.sharedwifi.service.impl.UserIncomeBalanceServiceImpl;
import com.phicomm.smarthome.sharedwifi.util.Util;
import com.phicomm.smarthome.util.StringUtil;

/**
 * 【后台】查询用户账户总余额 也就是可以提现的数量
 * 
 * 数据库设计更新 查询sw_user_income_balance表
 *
 * @author rongwei.huang
 *
 */
@RestController
public class AppGetUserAccountIncomeAll extends BaseController {
    private final Logger logger = LogManager.getLogger(getClass());

    
    @Autowired
    UserIncomeBalanceServiceImpl incomeBalanceService;

	@RequestMapping(value = "/app/get_sharedwifi_income_all", method = RequestMethod.POST, produces = { "application/json" })
	public SmartHomeResponse<Object> getIncomeall(@RequestBody UsereAppGetIncomeAllRequestModel model) {
	    logger.debug("getIncomeall begin ");

		if (model == null) {
			logger.error("Request para is null");
			return errorResponse(ResponseStatus.STAUS_NO_PARA_IN_REQUEST);
		}

		if (StringUtil.isNullOrEmpty(model.getToken())) {
			logger.error("No token in request");
			return errorResponse(ResponseStatus.STAUS_NO_TOKEN_IN_REQUEST);
		}

		UserIncomeAllResponseModel rspObj = new UserIncomeAllResponseModel(StringUtil.formatNumberWithTwoPoint(0));
		
		// 通过token获取斐讯云uid
		Map<String, String> headerParas = new HashMap<>(1);
		headerParas.put(Const.AUTHORIZATION, model.getToken());
		PhicommAccountDetailModel parsedObj = Util.getPhicommAccountByToken(headerParas);
		if (parsedObj == null) {
		    logger.error("failed to get phicomm account parseObj null");
            rspObj.setRetCode(ResponseStatus.STAUS_FAILED_GET_PHICOMM_ACCOUNT);
            return successResponse(rspObj);
        }
		
		String uid = parsedObj.getData().getUid();
		if (StringUtil.isNullOrEmpty(uid)) {
			logger.error("failed to get phicomm account");
		    rspObj.setRetCode(ResponseStatus.STAUS_FAILED_GET_PHICOMM_ACCOUNT);
            return successResponse(rspObj);
		}
		logger.debug("uid: {} ", uid);
		
		//sw_user_income_balance表一个uid肯定只最多对应一条记录
		String incomeBalanceS = "";
		try {
		    incomeBalanceS = incomeBalanceService.selectUidIncome(uid);
		    if (StringUtil.isNullOrEmpty(incomeBalanceS)) {
                incomeBalanceS = "0";
            }
		    rspObj.setTotalIncome(StringUtil.formatNumberWithTwoPoint(Double.parseDouble(incomeBalanceS)));
        } catch (Exception e) {
            logger.error(e);
            rspObj.setRetCode(ResponseStatus.STAUS_DATABASE_OPERATE_ERROR);
            return successResponse(rspObj);
        }
		
		logger.info("incomeBalanceS {} done", incomeBalanceS);
		return successResponse(rspObj);
	}
}
