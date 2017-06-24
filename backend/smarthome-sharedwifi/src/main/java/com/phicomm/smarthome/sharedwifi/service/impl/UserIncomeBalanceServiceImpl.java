/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.model.table.UserIncomeBalanceModel;
import com.phicomm.smarthome.sharedwifi.dao.UserIncomeBalanceMapper;
import com.phicomm.smarthome.sharedwifi.service.UserIncomeBalanceService;
import com.phicomm.smarthome.util.StringUtil;

/**
 * @author wenhua.tang
 *
 */
@Service
public class UserIncomeBalanceServiceImpl implements UserIncomeBalanceService {
	@Autowired
	private UserIncomeBalanceMapper userIncomeBalanceMapper;

	@Override
	public int updateUserTotalBalance(String uid, String totalBalance, String oldTotalBalance) {
		return userIncomeBalanceMapper.updateUserTotalBalance(uid, totalBalance, oldTotalBalance);
	}

	@Override
	public int insertUserBalance(UserIncomeBalanceModel userIncomeBalanceModel) {

		return userIncomeBalanceMapper.insertUserBalance(userIncomeBalanceModel);
	}

	@Override
	public String selectUidIncome(String uid) {

		return userIncomeBalanceMapper.selectUidIncome(uid);
	}

	@Override
	public int updateUserTotalBalanceTransactional(UserIncomeBalanceModel userIncomeBalanceModel) {
		String oldTotalBalance = userIncomeBalanceMapper.selectUidIncome(userIncomeBalanceModel.getUid());
		if (StringUtil.isNullOrEmpty(oldTotalBalance)) {
			return userIncomeBalanceMapper.insertUserBalance(userIncomeBalanceModel);
		}

		String updateTotalBalance = String
				.valueOf(Double.valueOf(oldTotalBalance) + Double.valueOf(userIncomeBalanceModel.getTotalBalance()));
		return userIncomeBalanceMapper.updateUserTotalBalance(userIncomeBalanceModel.getUid(), updateTotalBalance, oldTotalBalance);
	}

}
