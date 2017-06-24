/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.service;

import com.phicomm.smarthome.sharedwifi.model.app.AppUserIncomeDaoModel;

/**
 * @author wenhua.tang
 *
 */
public interface UserIncomeTransactional {

	int updateUserIncomeTransaction(AppUserIncomeDaoModel income);

}
