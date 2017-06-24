package com.phicomm.smarthome.sharedwifi.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.consts.Const;
import com.phicomm.smarthome.sharedwifi.consts.Const.ResponseStatus;
import com.phicomm.smarthome.sharedwifi.controller.common.BaseController;
import com.phicomm.smarthome.sharedwifi.model.app.AppIncomeDetailsDaoModel;
import com.phicomm.smarthome.sharedwifi.model.app.request.UserIncomeDetailRequestModel;
import com.phicomm.smarthome.sharedwifi.model.app.responses.UserIncomeDetailResponseModel;
import com.phicomm.smarthome.sharedwifi.model.common.PhicommAccountDetailModel;
import com.phicomm.smarthome.sharedwifi.service.impl.AppIncomeDetailsImpl;
import com.phicomm.smarthome.sharedwifi.util.MyListUtils;
import com.phicomm.smarthome.sharedwifi.util.Util;
import com.phicomm.smarthome.util.StringUtil;

/**
 * 查询零钱明细
 *
 * @author wenhua.tang
 *
 */
@RestController
public class AppIncomeDetail extends BaseController {
    private final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private AppIncomeDetailsImpl appIncomeDetailImpl;

	@RequestMapping(value = "/app/income_detail", method = RequestMethod.POST, produces = { "application/json" })
	public SmartHomeResponse<Object> incomeDetail(@RequestBody UserIncomeDetailRequestModel model) {
		logger.debug("query incomeDetail begin");
		if (model == null) {
			logger.error("Request para is null");
			return errorResponse(ResponseStatus.STAUS_NO_PARA_IN_REQUEST);
		}

		if (StringUtil.isNullOrEmpty(model.getToken())) {
			logger.error("No token in request");
			return errorResponse(ResponseStatus.STAUS_NO_TOKEN_IN_REQUEST);
		}
		
		UserIncomeDetailResponseModel rspObj = new UserIncomeDetailResponseModel();

		// 通过token获取斐讯云uid
		Map<String, String> headerParas = new HashMap<>(1);
		headerParas.put(Const.AUTHORIZATION, model.getToken());
		PhicommAccountDetailModel parsedObj = Util.getPhicommAccountByToken(headerParas);
		if (parsedObj == null) {
		    logger.error("Failed to get phicomm account parsedObj null");
            rspObj.setRetCode(ResponseStatus.STAUS_FAILED_GET_PHICOMM_ACCOUNT);
            return successResponse(rspObj);
        }
		
		String uid = parsedObj.getData().getUid();
		if (StringUtil.isNullOrEmpty(uid)) {
			logger.error("uid is empty");
			rspObj.setRetCode(ResponseStatus.STAUS_FAILED_GET_PHICOMM_ACCOUNT);
            return successResponse(rspObj);
		}
		logger.debug("uid: {}", uid);

		List<AppIncomeDetailsDaoModel> items = null;
        try {
            items =  appIncomeDetailImpl.findIncomeDetailItems(uid);
        } catch (Exception e) {
            logger.error(e);
            rspObj.setRetCode(ResponseStatus.STAUS_DATABASE_OPERATE_ERROR);
            return successResponse(rspObj);
        }
        
		if (MyListUtils.isEmpty(items)) {
			logger.error("items is empty");
            return successResponse(rspObj);
		}

		// fill and filter data...
		List<UserIncomeDetailResponseModel.Details> details = new ArrayList<>(items.size());
		for (AppIncomeDetailsDaoModel item : items) {
			// TODO 分页逻辑后续实现

		    UserIncomeDetailResponseModel.Details detail = new UserIncomeDetailResponseModel.Details(item);
			details.add(detail);
		}
		rspObj.setIncomeDetails(details);

		logger.info("incomeDetail done");
		return successResponse(rspObj);
	}
}