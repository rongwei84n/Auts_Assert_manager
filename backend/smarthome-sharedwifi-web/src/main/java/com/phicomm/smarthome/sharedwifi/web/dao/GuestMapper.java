package com.phicomm.smarthome.sharedwifi.web.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.phicomm.smarthome.model.Guest;

public interface GuestMapper {
	@Insert("insert into sw_guest(open_id,device_mac,router_ip,router_mac,wx_nick_name,wx_head_icon_url,iphone,sex,zip_code,zone,create_time,update_time)"
			+ "values(#{guest.openId},#{guest.deviceMac},#{guest.routerIp},#{guest.routerMac},#{guest.wxNickName},#{guest.wxHeadIconUrl},#{guest.iphone},"
			+ "#{guest.sex},#{guest.zipCode},#{guest.zone},#{guest.createTime},#{guest.updateTime})")
	int insertGuest(@Param("guest") Guest guest);

	@Select("SELECT count(1) FROM sw_guest WHERE device_mac = #{deviceMac} and router_mac=#{routerMac} and status=0")
	int findByName(@Param("routerMac") String routerMac, @Param("deviceMac") String deviceMac);
}
