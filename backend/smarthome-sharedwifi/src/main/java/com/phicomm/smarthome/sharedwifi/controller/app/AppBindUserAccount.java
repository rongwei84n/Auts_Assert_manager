package com.phicomm.smarthome.sharedwifi.controller.app;

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
import com.phicomm.smarthome.sharedwifi.model.app.responses.UserBindAccountResponseModel;
import com.phicomm.smarthome.sharedwifi.model.app.UserDaoModel;
import com.phicomm.smarthome.sharedwifi.model.app.request.UserAppBindUserAccountRequestModel;
import com.phicomm.smarthome.sharedwifi.model.common.PhicommAccountDetailModel;
import com.phicomm.smarthome.sharedwifi.service.UserInfo;
import com.phicomm.smarthome.sharedwifi.util.Util;
import com.phicomm.smarthome.util.StringUtil;

/**
 * 绑定斐讯帐号和微信的OpenId 后台接口
 * 
 * @author wenhua.tang
 *
 */
@RestController
public class AppBindUserAccount extends BaseController {
	private final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private UserInfo userInfo;

	@RequestMapping(value = "/app/bind_user_account", method = RequestMethod.POST, produces = { "application/json" })
	public SmartHomeResponse<Object> bindUserAccount(@RequestBody UserAppBindUserAccountRequestModel requestParas) {
	    logger.debug("bindUserAccount begin");

		if (requestParas == null) {
		    logger.error("Request paras is null");
			return errorResponse(ResponseStatus.STAUS_NO_PARA_IN_REQUEST);
		}
		if (StringUtil.isNullOrEmpty(requestParas.getOpenId())) {
		    logger.error("No open id in request");
			return errorResponse(ResponseStatus.STAUS_NO_OPEN_ID_IN_REQUEST);
		}
		if (StringUtil.isNullOrEmpty(requestParas.getToken())) {
		    logger.error("No token in request");
			return errorResponse(ResponseStatus.STAUS_NO_TOKEN_IN_REQUEST);
		}
		
		UserBindAccountResponseModel rspObj = new UserBindAccountResponseModel(requestParas.getOpenId(), requestParas.getToken());

		// 通过open_id查询数据库，查看是否已经绑定过
		List<UserDaoModel> queryResult = null;
		try {
			queryResult = userInfo.queryUidByOpenId(requestParas.getOpenId());
		} catch (Exception e) {
			logger.error(e);
			rspObj.setRetCode(ResponseStatus.STAUS_DATABASE_OPERATE_ERROR);
			return successResponse(rspObj);
		}
		if (queryResult != null && !queryResult.isEmpty()) {
			// 已经绑定过，直接返回
			// TODO 这个逻辑也许要改
		    
		    logger.error("open id {} already in binded", requestParas.getOpenId());
		    rspObj.setRetCode(ResponseStatus.STAUS_BIND_OPENID_UID_ALREADY);
		    return successResponse(rspObj);
		}

		// 根据token尝试获取斐讯云账号信息
		Map<String, String> headerParas = new HashMap<>(1);
		headerParas.put(Const.AUTHORIZATION, requestParas.getToken());
		PhicommAccountDetailModel parsedObj = Util.getPhicommAccountByToken(headerParas);
		if (parsedObj == null) {
		    logger.error("failed to get phicomm account");
		    rspObj.setRetCode(ResponseStatus.STAUS_FAILED_GET_PHICOMM_ACCOUNT);
            return successResponse(rspObj);
		}

		// 保存数据到sw_user表
		try {
			updateDatabase(parsedObj, requestParas.getOpenId());
		} catch (Exception e) {
			logger.error(e);
			rspObj.setRetCode(ResponseStatus.STAUS_DATABASE_OPERATE_ERROR);
            return successResponse(rspObj);
		}
		
		logger.info("bindUserAccount done ");
		return successResponse(rspObj);
	}

	private int updateDatabase(PhicommAccountDetailModel parsedObj, String openId) {
		UserDaoModel userModel = new UserDaoModel(parsedObj, openId);
		userInfo.add(userModel);
		return ResponseStatus.STAUS_OK;
	}
}
