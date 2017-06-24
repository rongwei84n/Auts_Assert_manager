package com.phicomm.smarthome.sharedwifi.controller.app;

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
import com.phicomm.smarthome.sharedwifi.model.app.AppWithDrawsAlipayDaoModel;
import com.phicomm.smarthome.sharedwifi.model.app.request.UserAppWithDrawsAlipayRequestModel;
import com.phicomm.smarthome.sharedwifi.model.common.BaseResponseModel;
import com.phicomm.smarthome.sharedwifi.model.common.PhicommAccountDetailModel;
import com.phicomm.smarthome.sharedwifi.service.impl.AppWithDrawsAlipayServiceImpl;
import com.phicomm.smarthome.sharedwifi.service.impl.UserIncomeBalanceServiceImpl;
import com.phicomm.smarthome.sharedwifi.util.Util;
import com.phicomm.smarthome.util.StringUtil;

/**
 * 提现前查询是否可以提现，一个月内不能重复提现，余额大于10元
 * @author rongwei.huang
 *
 */
@RestController
public class AppWithDrawsPrecheck extends BaseController {
    private final Logger logger = LogManager.getLogger(getClass());
    
    @Autowired
    private AppWithDrawsAlipayServiceImpl withDrawsAlipayService;
    
    @Autowired
    UserIncomeBalanceServiceImpl incomeBalanceService;
    
    @RequestMapping(value = "/app/with_draws_precheck", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> withDraws(@RequestBody UserAppWithDrawsAlipayRequestModel requestParas) {
        logger.debug("withDraws check begin");

        if (requestParas == null) {
            logger.error("Request paras is null");
            return errorResponse(ResponseStatus.STAUS_NO_PARA_IN_REQUEST);
        }

        if (StringUtil.isNullOrEmpty(requestParas.getToken())) {
            logger.error("No token in request");
            return errorResponse(ResponseStatus.STAUS_NO_TOKEN_IN_REQUEST);
        }
        
        BaseResponseModel rspObj = new BaseResponseModel();

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
            logger.error("Failed to get phicomm account");
            rspObj.setRetCode(ResponseStatus.STAUS_FAILED_GET_PHICOMM_ACCOUNT);
            return successResponse(rspObj);
        }

        logger.debug("uid: {}", uid);

        int resultCode = ResponseStatus.STAUS_OK;
        try {
            resultCode = drawPreCheck(uid, requestParas);
        } catch (Exception e) {
            logger.error(e);
            rspObj.setRetCode(ResponseStatus.STAUS_DATABASE_OPERATE_ERROR);
            return successResponse(rspObj);
        }
        
        if (resultCode != ResponseStatus.STAUS_OK) {
            rspObj.setRetCode(ResponseStatus.STAUS_WITH_DRAW_TOO_OFEN);
            return successResponse(rspObj);
        }
        
        
        // 查询可以提现的总金额--- sw_user_income_balance
        double incomeBalance = 0;
        try {
            String balance = incomeBalanceService.selectUidIncome(uid);
            if (StringUtil.isNullOrEmpty(balance)) {
                balance = "0";
            }
            incomeBalance = Double.parseDouble(balance);
        }catch (Exception e) {
            logger.error(e);
            rspObj.setRetCode(ResponseStatus.STAUS_DATABASE_OPERATE_ERROR);
            return successResponse(rspObj);
        }
        logger.info("incomeBalance: {}", incomeBalance);
        
        if (incomeBalance < 0.04D) {//TODO 为了测试改成10->0.04
            logger.error("Draw with no balance");
            rspObj.setRetCode(ResponseStatus.STAUS_WITH_DRAW_NO_BALANCE);
            return successResponse(rspObj);
        }

        return successResponse(rspObj);
    }
    
    private int drawPreCheck(String uid, UserAppWithDrawsAlipayRequestModel requestParas) {
        List<AppWithDrawsAlipayDaoModel> result = withDrawsAlipayService.queryGreaterThanCreate(getFirstDayAslong());

        if (result == null || result.isEmpty()) {
            return ResponseStatus.STAUS_OK;
        }

        boolean drawFlag = false;
        for (AppWithDrawsAlipayDaoModel item : result) {
            if (StringUtil.equals(item.getUid(), uid)) {
                drawFlag = true;
                break;
            }
        }
        System.out.println("with draw done uid: " + uid);
        return drawFlag? ResponseStatus.STAUS_WITH_DRAW_TOO_OFEN: ResponseStatus.STAUS_OK;
    }
    
    /**
     * TODO 时间间隔5分钟内不能体现
     * @return
     */
    private long getFirstDayAslong() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND,0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        Date sdate = calendar.getTime();
//        return sdate.getTime()/1000;
        
        
        Date date= new Date();
        return (date.getTime() - 1000*60*5)/1000;
    }
}
