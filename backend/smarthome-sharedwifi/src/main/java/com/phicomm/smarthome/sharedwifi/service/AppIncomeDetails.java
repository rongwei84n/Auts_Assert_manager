package com.phicomm.smarthome.sharedwifi.service;

import java.util.List;

import com.phicomm.smarthome.sharedwifi.model.app.AppIncomeDetailsDaoModel;

public interface AppIncomeDetails {
	List<AppIncomeDetailsDaoModel> findIncomeDetailItems(String uid);

	int insertOne(AppIncomeDetailsDaoModel incomeDetail);
}
