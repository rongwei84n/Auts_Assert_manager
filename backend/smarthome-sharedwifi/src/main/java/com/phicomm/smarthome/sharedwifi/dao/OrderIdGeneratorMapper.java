/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.dao;

import org.apache.ibatis.annotations.Param;

import com.phicomm.smarthome.sharedwifi.model.h5web.OrderIdGenerater;

/**
 * @author wenhua.tang
 *
 */
public interface OrderIdGeneratorMapper {
	// @Insert("insert into sw_order_id_generator(create_time,createTime)
	// values(#{orderIdGenerater.createTime},#{orderIdGenerater.updateTime})")
	// @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	long getOrderId(@Param("orderIdGenerater") OrderIdGenerater orderIdGenerater);
}
