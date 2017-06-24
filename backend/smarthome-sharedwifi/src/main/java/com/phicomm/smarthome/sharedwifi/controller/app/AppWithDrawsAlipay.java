package com.phicomm.smarthome.sharedwifi.controller.app;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.consts.Const;
import com.phicomm.smarthome.sharedwifi.consts.Const.ResponseStatus;
import com.phicomm.smarthome.sharedwifi.controller.common.BaseController;
import com.phicomm.smarthome.sharedwifi.model.app.AppIncomeDetailsDaoModel;
import com.phicomm.smarthome.sharedwifi.model.app.AppWithDrawsAlipayDaoModel;
import com.phicomm.smarthome.sharedwifi.model.app.request.UserAppWithDrawsAlipayRequestModel;
import com.phicomm.smarthome.sharedwifi.model.common.BaseResponseModel;
import com.phicomm.smarthome.sharedwifi.model.common.PhicommAccountDetailModel;
import com.phicomm.smarthome.sharedwifi.service.impl.AppIncomeDetailsImpl;
import com.phicomm.smarthome.sharedwifi.service.impl.AppWithDrawsAlipayServiceImpl;
import com.phicomm.smarthome.sharedwifi.service.impl.UserIncomeBalanceServiceImpl;
import com.phicomm.smarthome.sharedwifi.util.Util;
import com.phicomm.smarthome.util.StringUtil;

/**
 * 提现到支付宝帐号
 *
 * @author rongwei.huang
 *
 */
@RestController
public class AppWithDrawsAlipay extends BaseController {
    private final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private AppWithDrawsAlipayServiceImpl withDrawsAlipayService;

	@Autowired
	private AppIncomeDetailsImpl incomeDetailService;
	
	@Autowired
	UserIncomeBalanceServiceImpl incomeBalanceService;

	@Transactional
	@RequestMapping(value = "/app/with_draws_alipay", method = RequestMethod.POST, produces = { "application/json" })
	public SmartHomeResponse<Object> withDraws(@RequestBody UserAppWithDrawsAlipayRequestModel requestParas) {
		logger.debug("withDraws alipay begin");

		if (requestParas == null) {
		    logger.error("Request paras is null");
			return errorResponse(ResponseStatus.STAUS_NO_PARA_IN_REQUEST);
		}

		if (StringUtil.isNullOrEmpty(requestParas.getToken())) {
		    logger.error("No token in request");
			return errorResponse(ResponseStatus.STAUS_NO_TOKEN_IN_REQUEST);
		}

		if (StringUtil.isNullOrEmpty(requestParas.getAlipayAccount())) {
		    logger.error("no alipay account in request");
			return errorResponse(ResponseStatus.STAUS_ALIPAY_ACCOUNT_IN_REQUEST);
		}
		logger.debug("alipay account: {}", requestParas.getAlipayAccount());
		
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
		    logger.error("With draw to ofen");
			rspObj.setRetCode(ResponseStatus.STAUS_WITH_DRAW_TOO_OFEN);
            return successResponse(rspObj);
		}

		// 查询可以提现的总金额--- sw_user_income_balance
		double incomeBalance = 0;
		String incomeBalanceS = null;
		try {
		    incomeBalanceS = incomeBalanceService.selectUidIncome(uid);
		    if (StringUtil.isNullOrEmpty(incomeBalanceS)) {
		        incomeBalanceS = "0";
            }
		    incomeBalance = Double.parseDouble(incomeBalanceS);
        }catch (Exception e) {
            logger.error(e);
            rspObj.setRetCode(ResponseStatus.STAUS_DATABASE_OPERATE_ERROR);
            return successResponse(rspObj);
        }
		
		logger.debug("incomeBalance: {}", incomeBalance);
		
		if (incomeBalance < 0.04D) { //TODO 为了测试改成10->0.04
		    logger.error("Draw with no balance");
		    rspObj.setRetCode(ResponseStatus.STAUS_WITH_DRAW_NO_BALANCE);
            return successResponse(rspObj);
		}

		//更新数据库
        try {
            updateDatabase(incomeBalance, uid, incomeBalanceS, requestParas.getAlipayAccount(), parsedObj.getData().getPhonenumber(),
                    parsedObj.getData().getNickname());
        } catch (Exception e) {
            logger.error(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            
            rspObj.setRetCode(ResponseStatus.STAUS_DATABASE_OPERATE_ERROR);
            return successResponse(rspObj);
        }

		logger.info("withDraws alipay done");
		return successResponse(rspObj);
	}
	
	@Transactional
    public int updateDatabase(double totalIncome, String uid, String oldBalance, String alipayAccount, String phone, String nickName) throws Exception {
        //插入零钱明细表
        AppIncomeDetailsDaoModel incomeDetail = new AppIncomeDetailsDaoModel();
        incomeDetail.setActionName("提现");
        incomeDetail.setCost("-" + totalIncome);
        long curTime = System.currentTimeMillis()/1000;
        incomeDetail.setCreateTime(curTime);
        incomeDetail.setRouterMac("");
        incomeDetail.setStatus((byte) 0);
        incomeDetail.setUid(uid);
        incomeDetail.setUpdateTime(curTime);
        incomeDetailService.insertOne(incomeDetail);

        //总余额清零 sw_user_income_balance
        int result = incomeBalanceService.updateUserTotalBalance(
                uid, 
                StringUtil.formatNumberWithTwoPoint(0), 
                oldBalance);
        if (result <= 0) {
            //更新数据库没有影响一行记录，Rollback
            logger.error("Update balance table unsuccess, rollback");
            throw new Exception("No updated rows");
        }
        
        //插入提现到支付宝数据库
        AppWithDrawsAlipayDaoModel daoModel = new AppWithDrawsAlipayDaoModel();
        daoModel.setUid(uid);
        daoModel.setCost(String.valueOf(totalIncome));
        daoModel.setAlipayAccount(alipayAccount);
        long currentTime = System.currentTimeMillis()/1000;
        daoModel.setCreateTime(currentTime);
        daoModel.setUpdateTime(currentTime);
        daoModel.setStatus(0);
        daoModel.setPhoneNum(phone);
        daoModel.setNickname(nickName);
        daoModel.setOrderNum("");//支付宝订单号，客服人员转账到支付宝时输入
        
        withDrawsAlipayService.add(daoModel);

        return ResponseStatus.STAUS_OK;
    }

	/**
     * 提现预检查
     * 1. 这次提现和上次提现间隔时间，不能在同一个自然月
     * @param userWithDraws
     * @return
     */
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
        System.out.println("drawFlag: " + drawFlag + " uid: " + uid);
        return drawFlag? ResponseStatus.STAUS_WITH_DRAW_TOO_OFEN: ResponseStatus.STAUS_OK;
    }

    /**
     * TODO 为了测试改成前5分钟
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