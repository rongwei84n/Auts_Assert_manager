/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.sharedwifi.dao.GuestOrderMapper;
import com.phicomm.smarthome.sharedwifi.model.h5web.GuestOrder;
import com.phicomm.smarthome.sharedwifi.service.GuestOrderStatusService;

/**
 * @author wenhua.tang
 *
 */
@Service
public class GuestOrderStatusServiceImpl implements GuestOrderStatusService {
	private final static Logger logger = LogManager.getLogger(GuestOrderStatusServiceImpl.class);

	@Autowired
	private GuestOrderMapper guestOrderMapper;

	@Override
	public List<GuestOrder> getOrderPaiedStatus(String orderId) {
		List<GuestOrder> guestOrderList = null;
		try {
			guestOrderList = guestOrderMapper.findByOrderId(orderId);
		} catch (Exception e) {
			logger.error(e);
		}
		return guestOrderList;
	}

	/**
	 * @return 0 表示未插入成功
	 */
	@Override
	public int insertGuestOrder(GuestOrder guestOrder) {
		int affected = 0;
		try {
			affected = (int) guestOrderMapper.insertGuestOrder(guestOrder);
		} catch (Exception e) {
			logger.error(e);
		}
		return affected;
	}

	@Override
	public long updateGuestOrder(GuestOrder guestOrder) {
		int affected = 0;
		try {
			affected = (int) guestOrderMapper.updateGuestOrder(guestOrder);
		} catch (Exception e) {
			logger.error(e);
		}
		return affected;
	}
}
