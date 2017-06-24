package com.phicomm.smarthome.sharedwifi.controller.app;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.consts.Const.ResponseStatus;
import com.phicomm.smarthome.sharedwifi.controller.common.BaseController;
import com.phicomm.smarthome.sharedwifi.model.app.UserDaoModel;
import com.phicomm.smarthome.sharedwifi.model.app.request.UserAppGetUserBindMsgRequestModel;
import com.phicomm.smarthome.sharedwifi.model.common.BaseResponseModel;
import com.phicomm.smarthome.sharedwifi.service.impl.UserInfoImpl;
import com.phicomm.smarthome.sharedwifi.util.MyResponseutils;
import com.phicomm.smarthome.util.StringUtil;

/**
 * 查询用户微信号是否绑定了斐讯帐号
 *
 * @author wenhua.tang
 *
 */
@RestController
public class AppGetUserBindMsg extends BaseController {
    private final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private UserInfoImpl userInfoImpl;

	@RequestMapping(value = "/app/get_user_bind_msg", method = RequestMethod.POST, produces = { "application/json" })
	public SmartHomeResponse<Object> getUserBindMsg(@RequestBody UserAppGetUserBindMsgRequestModel model) {
	    logger.debug("getUserBindMsg begin");

		if (model == null) {
		    logger.error("Request paras is null");
			return errorResponse(ResponseStatus.STAUS_NO_PARA_IN_REQUEST);
		}

		if (StringUtil.isNullOrEmpty(model.getOpenId())) {
		    logger.error("No open id in request");
			return errorResponse(ResponseStatus.STAUS_NO_OPEN_ID_IN_REQUEST);
		}

		BaseResponseModel rspObj = new BaseResponseModel();

		List<UserDaoModel> result = null;
		try {
		    result = userInfoImpl.queryUidByOpenId(model.getOpenId());
        } catch (Exception e) {
            logger.error(e);
            rspObj.setRetCode(ResponseStatus.STAUS_DATABASE_OPERATE_ERROR);
            return successResponse(rspObj);
        }
		
		if (result == null || result.isEmpty()) {
		    logger.error("open id {} not bind", model.getOpenId());
			rspObj.setRetCode(ResponseStatus.STAUS_ERROR);// 未绑定
			rspObj.setRetMsg(MyResponseutils.parseMsg(ResponseStatus.STAUS_COMMON_FAILED));
		}

		logger.info("getUserBindMsg done");
		return successResponse(rspObj);
	}
}