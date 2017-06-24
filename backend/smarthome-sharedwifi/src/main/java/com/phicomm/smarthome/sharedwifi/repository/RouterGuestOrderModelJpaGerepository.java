package com.phicomm.smarthome.sharedwifi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.phicomm.smarthome.sharedwifi.model.h5web.GuestOrder;

import java.util.List;

/**
 * PROJECT_NAME: liang04-smarthome-sharedwifi
 * PACKAGE_NAME: com.phicomm.smarthome.sharedwifi.repository
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/13
 */

public interface RouterGuestOrderModelJpaGerepository extends JpaRepository<GuestOrder, Long> {

	@Query(value = "SELECT curOrder FROM GuestOrder curOrder WHERE (curOrder.status = 0) AND curOrder.guestId =:guestId AND curOrder.orderId =:orderId")
	public GuestOrder findValidByGuestIdAndOrderId(@Param("guestId") long guestId, @Param("orderId") String orderId);

	@Query(value = "SELECT curOrder FROM GuestOrder curOrder WHERE  curOrder.guestId =:guestId AND curOrder.orderId =:orderId")
	public GuestOrder findAllByGuestIdAndOrderId(@Param("guestId") long guestId, @Param("orderId") String orderId);

	@Query(value = "SELECT curOrder FROM GuestOrder curOrder WHERE curOrder.guestId =:guestId AND curOrder.orderStatus =:orderStatus")
	public List<GuestOrder> findPassAuthorizationByGuestIdAndOrderStatus(@Param("guestId")long guestId, @Param("orderStatus")int orderStatus);

}
