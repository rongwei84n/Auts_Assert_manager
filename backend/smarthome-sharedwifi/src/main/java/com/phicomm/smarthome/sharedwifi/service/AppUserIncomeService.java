package com.phicomm.smarthome.sharedwifi.service;

import java.util.List;

import com.phicomm.smarthome.sharedwifi.model.app.AppUserIncomeDaoModel;

public interface AppUserIncomeService {
	public List<AppUserIncomeDaoModel> findByUid(String uid);

	public void updateById(AppUserIncomeDaoModel income);

	// ---------------------------------------------------------------------------
	int insertUserIncome(AppUserIncomeDaoModel income);

	String selectUserTodayIncome(String uid, String routerMac, String todayDate);

	int updateUserTotalIncome(String uid, String routerMac, String todayDate, String totalIncome);

	int updateUserIncomeTransactional(AppUserIncomeDaoModel income);
}
