package com.phicomm.smarthome.sharedwifi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.phicomm.smarthome.sharedwifi.dao.AppUserIncomeMapper;
import com.phicomm.smarthome.sharedwifi.model.app.AppUserIncomeDaoModel;
import com.phicomm.smarthome.sharedwifi.service.AppUserIncomeService;
import com.phicomm.smarthome.util.StringUtil;

@Service
public class AppUserIncomeServiceImpl implements AppUserIncomeService {

	@Autowired
	private AppUserIncomeMapper userIncomeMapper;

	@Override
	public List<AppUserIncomeDaoModel> findByUid(String uid) {
		return userIncomeMapper.findByUid(uid);
	}

	@Override
	public void updateById(AppUserIncomeDaoModel income) {
		userIncomeMapper.updateById(income);
	}

	@Override
	public int insertUserIncome(AppUserIncomeDaoModel income) {

		return userIncomeMapper.insertUserIncome(income);
	}

	@Override
	public String selectUserTodayIncome(String uid, String routerMac, String todayDate) {

		return userIncomeMapper.selectUserTodayIncome(uid, routerMac, todayDate);
	}

	@Override
	public int updateUserTotalIncome(String uid, String routerMac, String todayDate, String totalIncome) {

		return userIncomeMapper.updateUserTotalIncome(uid, routerMac, todayDate, totalIncome);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateUserIncomeTransactional(AppUserIncomeDaoModel income) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
		String todayDate = df.format(new Date());

		String maxTotalIncome = userIncomeMapper.selectUserTodayIncome(income.getUid(), income.getRouterMac(), todayDate);
		if (StringUtil.isNullOrEmpty(maxTotalIncome)) {
			return userIncomeMapper.insertUserIncome(income);
		}

		return userIncomeMapper.updateUserTotalIncome(income.getUid(), income.getRouterMac(), todayDate, todayDate);
	}
}
