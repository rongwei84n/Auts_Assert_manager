/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.service;

import java.util.List;

import com.phicomm.smarthome.sharedwifi.model.h5web.GuestOrder;

/**
 * @author wenhua.tang
 *
 */
public interface GuestOrderStatusService {
	// 获取订单的支付状态
	List<GuestOrder> getOrderPaiedStatus(String orderId);

	// 插入订单到db中
	int insertGuestOrder(GuestOrder guestOrder);

	// 更新订单
	long updateGuestOrder(GuestOrder guestOrder);
}
