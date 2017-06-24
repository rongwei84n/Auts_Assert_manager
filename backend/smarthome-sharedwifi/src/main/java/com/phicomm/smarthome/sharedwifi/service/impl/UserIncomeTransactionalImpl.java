/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.phicomm.smarthome.model.table.UserIncomeBalanceModel;
import com.phicomm.smarthome.sharedwifi.consts.Const.OrderPayStatusInterfaces;
import com.phicomm.smarthome.sharedwifi.consts.Const.OrderStatusInterfaces;
import com.phicomm.smarthome.sharedwifi.dao.AppIncomeDetailsMapper;
import com.phicomm.smarthome.sharedwifi.dao.AppUserIncomeMapper;
import com.phicomm.smarthome.sharedwifi.dao.GuestOrderMapper;
import com.phicomm.smarthome.sharedwifi.dao.UserIncomeBalanceMapper;
import com.phicomm.smarthome.sharedwifi.exception.SharedWifiIncomeException;
import com.phicomm.smarthome.sharedwifi.model.app.AppIncomeDetailsDaoModel;
import com.phicomm.smarthome.sharedwifi.model.app.AppUserIncomeDaoModel;
import com.phicomm.smarthome.sharedwifi.model.h5web.GuestOrder;
import com.phicomm.smarthome.sharedwifi.service.UserIncomeTransactional;
import com.phicomm.smarthome.util.StringUtil;

/**
 * @author wenhua.tang
 *
 */
@Service
public class UserIncomeTransactionalImpl implements UserIncomeTransactional {
	private static final Logger logger = LogManager.getLogger(UserIncomeTransactionalImpl.class);
	@Autowired
	private UserIncomeBalanceMapper userIncomeBalanceMapper;

	@Autowired
	private AppUserIncomeMapper userIncomeMapper;

	@Autowired
	private AppIncomeDetailsMapper appIncomeDetailsMapper;

	@Autowired
	private GuestOrderMapper guestOrderMapper;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateUserIncomeTransaction(AppUserIncomeDaoModel income) {
		logger.info("--------------------");
		logger.info("开始更新...");

		int affected = 0;
		String maxTotalIncome = userIncomeMapper.selectUserTodayIncome(income.getUid(), income.getRouterMac(),
				income.getTodayDate());

		logger.info("[{}] uid[{}] 已有收入[{}]", income.getTodayDate(), income.getUid(), maxTotalIncome);
		logger.info("[{}] uid[{}] 新增收入[{}]", income.getTodayDate(), income.getUid(), income.getTodayIncome());
		affected = userIncomeMapper.insertUserIncome(income);
		if (affected <= 0) {
			logger.error("insert income err[{}]", income);
			throw new SharedWifiIncomeException();
		}

		if (!StringUtil.isNullOrEmpty(maxTotalIncome)) {
			logger.info("[{}] uid[{}] 更新设备[{}]总收入", income.getTodayDate(), income.getUid(), income.getRouterMac());
			affected = userIncomeMapper.updateUserTotalIncome(income.getUid(), income.getRouterMac(),
					income.getTodayDate(),
					String.valueOf(Double.valueOf(maxTotalIncome) + Double.valueOf(income.getTodayIncome())));
			if (affected <= 0) {
				logger.error("update uid[{}] device[{}] err", income.getUid(), income.getRouterMac());
				throw new SharedWifiIncomeException();
			}
		}

		UserIncomeBalanceModel userIncomeBalanceModel = new UserIncomeBalanceModel();
		userIncomeBalanceModel.setCreateTime(System.currentTimeMillis() / 1000);
		userIncomeBalanceModel.setUpdateTime(System.currentTimeMillis() / 1000);
		userIncomeBalanceModel.setUid(income.getUid());
		userIncomeBalanceModel.setTotalBalance(income.getTotalIncome());
		String oldTotalBalance = userIncomeBalanceMapper.selectUidIncome(userIncomeBalanceModel.getUid());
		logger.info("[{}] uid[{}] 账户余额[{}]", income.getTodayDate(), income.getUid(), oldTotalBalance);
		if (StringUtil.isNullOrEmpty(oldTotalBalance)) {
			affected = userIncomeBalanceMapper.insertUserBalance(userIncomeBalanceModel);
		} else {
			String updateTotalBalance = String.valueOf(
					Double.valueOf(oldTotalBalance) + Double.valueOf(userIncomeBalanceModel.getTotalBalance()));
			affected = userIncomeBalanceMapper.updateUserTotalBalance(userIncomeBalanceModel.getUid(),
					updateTotalBalance, oldTotalBalance);
		}
		if (affected <= 0) {
			logger.error("update uid[{}] total balance err", income.getUid());
			throw new SharedWifiIncomeException();
		}

		logger.info("[{}] uid[{}] 新增收益[{}]", income.getTodayDate(), income.getUid(),
				userIncomeBalanceModel.getTotalBalance());

		logger.info("[{}] uid[{}] 新增打赏明细", income.getTodayDate(), income.getUid());
		AppIncomeDetailsDaoModel appIncomeDetailsDaoModel = new AppIncomeDetailsDaoModel();
		appIncomeDetailsDaoModel.setActionName("打赏");
		appIncomeDetailsDaoModel.setCreateTime(System.currentTimeMillis() / 1000);
		appIncomeDetailsDaoModel.setUpdateTime(System.currentTimeMillis() / 1000);
		appIncomeDetailsDaoModel.setCost("+" + income.getTodayIncome());
		appIncomeDetailsDaoModel.setUid(income.getUid());
		appIncomeDetailsDaoModel.setRouterMac(income.getRouterMac());
		appIncomeDetailsDaoModel.setStatus((byte) 0);
		affected = appIncomeDetailsMapper.insertOne(appIncomeDetailsDaoModel);

		if (affected <= 0) {
			logger.error("insert  uid[{}] income detail err", income.getUid());
			throw new SharedWifiIncomeException();
		}

		logger.info("[{}] uid[{}] 更新订单[{}]支付状态", income.getTodayDate(), income.getUid(), income.getOrderId());
		// 入订单库
		GuestOrder guestOrder = new GuestOrder();
		guestOrder.setOrderId(income.getOrderId());
		guestOrder.setOrderStatus(OrderStatusInterfaces.ORDER_STATUS_PAIDINCOMMON);
		guestOrder.setOrderPayStatus(OrderPayStatusInterfaces.ORDER_PAY_STATUS_PAID);
		guestOrder.setBuyTime(System.currentTimeMillis() / 1000);
		guestOrder.setUpdateTime(System.currentTimeMillis() / 1000);
		affected = guestOrderMapper.updateGuestOrder(guestOrder);

		if (affected <= 0) {
			logger.error("update uid[{}] order[{}] err", income.getUid(), income.getOrderId());
			throw new SharedWifiIncomeException();
		}

		logger.info("更新结束...");
		return affected;
	}
}
