package com.phicomm.smarthome.sharedwifi.controller.app;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.phicomm.smarthome.sharedwifi.model.app.AppUserIncomeDaoModel;
import com.phicomm.smarthome.sharedwifi.model.app.request.AppGetSharedWifiIncomeRequestModel;
import com.phicomm.smarthome.sharedwifi.model.app.responses.AppGetSharedWifiIncomeResponseModel;
import com.phicomm.smarthome.sharedwifi.model.common.PhicommAccountDetailModel;
import com.phicomm.smarthome.sharedwifi.service.impl.AppUserIncomeServiceImpl;
import com.phicomm.smarthome.sharedwifi.util.Util;
import com.phicomm.smarthome.util.StringUtil;

/**
 * 
 * 查询收益接口 今天收入/总收入
 * @author wenhua.tang
 *
 */
@RestController
public class AppGetSharedWifiIncome extends BaseController{
    private final Logger logger = LogManager.getLogger(getClass());
    
    @Autowired
    AppUserIncomeServiceImpl incomeSerice;

	@RequestMapping(value = "/app/get_sharedwifi_income", method = RequestMethod.POST, produces = { "application/json" })
	public SmartHomeResponse<Object> getSharedWifiIncome(
	        @RequestBody AppGetSharedWifiIncomeRequestModel requestParas) {
	    logger.debug("getSharedWifiIncome begin.");
	    
	    if (requestParas == null) {
            logger.error("Request params is null");
            return errorResponse(ResponseStatus.STAUS_NO_PARA_IN_REQUEST);
        }
	    if (StringUtil.isNullOrEmpty(requestParas.getRouterMac())) {
            logger.error("No router mac in request");
            return errorResponse(ResponseStatus.STAUS_NO_ROUTER_MAC_IN_REQUEST);
        }
	    logger.info("Router mac {}", requestParas.getRouterMac());
	    
	    if (StringUtil.isNullOrEmpty(requestParas.getToken())) {
	        logger.error("No token in request");
            return errorResponse(ResponseStatus.STAUS_NO_TOKEN_IN_REQUEST);
        }
	    
	    AppGetSharedWifiIncomeResponseModel rspObj = new AppGetSharedWifiIncomeResponseModel();
	    
	    // 通过token获取斐讯云uid
        Map<String, String> headerParas = new HashMap<>(1);
        headerParas.put(Const.AUTHORIZATION, requestParas.getToken());
        
        PhicommAccountDetailModel parsedObj = Util.getPhicommAccountByToken(headerParas);
        if (parsedObj == null) {
            logger.error("Failed to get phicomm account parsedObj null");
            rspObj.setRetCode(ResponseStatus.STAUS_FAILED_GET_PHICOMM_ACCOUNT);
            return successResponse(rspObj);
        }
        
        String uid = parsedObj.getData().getUid();

        if (StringUtil.isNullOrEmpty(uid)) {
            logger.error("Failed to get phicomm account uid null");
            rspObj.setRetCode(ResponseStatus.STAUS_FAILED_GET_PHICOMM_ACCOUNT);
            return successResponse(rspObj);
        }
        logger.debug("uid: {}", uid);
        
        List<AppUserIncomeDaoModel> incomeResult = null;
        try {
            incomeResult = incomeSerice.findByUid(uid);
        } catch (Exception e) {
            logger.error(e);
            return errorResponse(ResponseStatus.STAUS_DATABASE_OPERATE_ERROR);
        }
        
        if (incomeResult == null || incomeResult.isEmpty()) {
            logger.error("No result be found in db");
            return successResponse(rspObj);
        }

        String today = getTodayAsString();
        
        logger.debug("today: {}", today);
        
        double todayIncome = 0;
        double totalIncome = 0;
        
        for (AppUserIncomeDaoModel item : incomeResult) {
            if (StringUtil.equals(item.getRouterMac(), requestParas.getRouterMac())) {
                if (StringUtil.equals(today, item.getTodayDate())) {
                    todayIncome += Double.parseDouble(item.getTodayIncome());
                }
                
                double totalTemp = Double.parseDouble(item.getTotalIncome());
                if (totalIncome < totalTemp) {
                    totalIncome = totalTemp;
                }
            }
        }
        
        logger.info("getSharedWifiIncome done todayIncome {} totalIncome {} ", todayIncome, totalIncome);
        rspObj.setTodayIncome(StringUtil.formatNumberWithTwoPoint(todayIncome));
        rspObj.setTotalIncome(StringUtil.formatNumberWithTwoPoint(totalIncome));
	    
        return successResponse(rspObj);
	}
	
	private String getTodayAsString() {
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	    return format.format(new Date());
	}
}
