package com.phicomm.smarthome.sharedwifi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.sharedwifi.dao.AppIncomeDetailsMapper;
import com.phicomm.smarthome.sharedwifi.model.app.AppIncomeDetailsDaoModel;
import com.phicomm.smarthome.sharedwifi.service.AppIncomeDetails;

@Service
public class AppIncomeDetailsImpl implements AppIncomeDetails {

	@Autowired
	AppIncomeDetailsMapper appIncomeDetailsMapper;

	@Override
	public List<AppIncomeDetailsDaoModel> findIncomeDetailItems(String uid) {
		return appIncomeDetailsMapper.findIncomeDetailItems(uid);
	}

	@Override
	public int insertOne(AppIncomeDetailsDaoModel incomeDetail) {
		return appIncomeDetailsMapper.insertOne(incomeDetail);
	}
}
