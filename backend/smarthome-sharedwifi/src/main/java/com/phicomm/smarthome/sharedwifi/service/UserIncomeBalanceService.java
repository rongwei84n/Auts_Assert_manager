/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.service;

import com.phicomm.smarthome.model.table.UserIncomeBalanceModel;

/**
 * @author wenhua.tang
 *
 */
public interface UserIncomeBalanceService {

	int updateUserTotalBalance(String uid, String totalBalance, String oldTotalBalance);

	int insertUserBalance(UserIncomeBalanceModel userIncomeBalanceModel);

	String selectUidIncome(String uid);

	int updateUserTotalBalanceTransactional(UserIncomeBalanceModel userIncomeBalanceModel);
}
