/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.phicomm.smarthome.sharedwifi.model.h5web.GuestOrder;

/**
 * @author wenhua.tang
 *
 */
public interface GuestOrderMapper {
	List<GuestOrder> findByOrderId(@Param(value = "orderId") String orderId);

	int insertGuestOrder(@Param(value = "guestOrder") GuestOrder guestOrder);

	int updateGuestOrder(@Param(value = "guestOrder") GuestOrder guestOrder);
}
