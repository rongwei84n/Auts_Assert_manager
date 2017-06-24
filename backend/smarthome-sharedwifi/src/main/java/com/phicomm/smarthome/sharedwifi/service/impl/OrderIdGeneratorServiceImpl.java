/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.sharedwifi.consts.Const;
import com.phicomm.smarthome.sharedwifi.dao.OrderIdGeneratorMapper;
import com.phicomm.smarthome.sharedwifi.model.h5web.OrderIdGenerater;
import com.phicomm.smarthome.sharedwifi.service.OrderIdGeneratorService;

/**
 * @author wenhua.tang
 *
 */
@Service
public class OrderIdGeneratorServiceImpl implements OrderIdGeneratorService {

	private static final Logger logger = LogManager.getLogger(OrderIdGeneratorServiceImpl.class);

	@Autowired
	private OrderIdGeneratorMapper orderIdGeneratorMapper;

	@Override
	public String getOrderId() {
		OrderIdGenerater orderIdGenerater = new OrderIdGenerater();
		long createOrdertime = System.currentTimeMillis() / 1000;
		orderIdGenerater.setCreateTime(createOrdertime);
		orderIdGenerater.setUpdateTime(createOrdertime);
		int affected = 0;
		try {
			affected = (int) orderIdGeneratorMapper.getOrderId(orderIdGenerater);
		} catch (Exception e) {
			logger.error(e);
		}

		if (affected <= 0) {
			return Const.STRING_EMPTY;
		}

		return Const.STRING_SHARED_WIFI_CODE + String.format("%030d", orderIdGenerater.getId());
	}

}
