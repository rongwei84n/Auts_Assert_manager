package com.phicomm.smarthome.sharedwifi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.phicomm.smarthome.sharedwifi.model.app.AppIncomeDetailsDaoModel;

/**
 * 查询收入明细的dao接口
 * 
 * @author rongwei.huang
 *
 */
public interface AppIncomeDetailsMapper {
	List<AppIncomeDetailsDaoModel> findIncomeDetailItems(@Param(value = "uid") String uid);

	int insertOne(AppIncomeDetailsDaoModel incomeDetail);
}
