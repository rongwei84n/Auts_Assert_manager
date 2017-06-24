/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.phicomm.smarthome.model.table.UserIncomeBalanceModel;

/**
 * @author wenhua.tang
 *
 */
public interface UserIncomeBalanceMapper {

	@Update("update sw_user_income_balance set total_balance=#{totalBalance} where uid=#{uid} and total_balance =#{oldTotalBalance} and status=0")
	int updateUserTotalBalance(@Param("uid") String uid, @Param("totalBalance") String totalBalance,
			@Param("oldTotalBalance") String oldTotalBalance);

	@Insert("INSERT into sw_user_income_balance(uid,total_balance,create_time,update_time)values(#{userIncomeBalanceModel.uid},"
			+ "#{userIncomeBalanceModel.totalBalance},#{userIncomeBalanceModel.createTime},#{userIncomeBalanceModel.updateTime})")
	int insertUserBalance(@Param("userIncomeBalanceModel") UserIncomeBalanceModel userIncomeBalanceModel);

	@Select("select total_balance from sw_user_income_balance where uid=#{uid} and status=0")
	String selectUidIncome(@Param("uid") String uid);
}
