package com.phicomm.smarthome.sharedwifi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.phicomm.smarthome.sharedwifi.model.app.AppUserIncomeDaoModel;

public interface AppUserIncomeMapper {
	List<AppUserIncomeDaoModel> findByUid(String uid);

	void updateById(AppUserIncomeDaoModel income);

	@Insert("insert into sw_user_income(uid,router_mac,today_income,total_income,today_date,order_id,create_time,update_time)"
			+ "values(#{income.uid},#{income.routerMac},#{income.todayIncome},#{income.totalIncome},#{income.todayDate},#{income.orderId},"
			+ "#{income.createTime},#{income.updateTime})")
	int insertUserIncome(@Param("income") AppUserIncomeDaoModel income);

	@Select("SELECT total_income FROM sw_user_income WHERE uid=#{uid} and router_mac = #{routerMac} and status=0 order by today_date desc limit 1")
	String selectUserTodayIncome(@Param("uid") String uid, @Param("routerMac") String routerMac, @Param("todayDate") String todayDate);

	@Update("update sw_user_income set total_income = #{totalIncome} WHERE uid=#{uid} and router_mac = #{routerMac} and today_date=#{todayDate} and status=0")
	int updateUserTotalIncome(@Param("uid") String uid, @Param("routerMac") String routerMac, @Param("todayDate") String todayDate,
			@Param("totalIncome") String totalIncome);

}
