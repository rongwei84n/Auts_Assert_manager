/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.sharedwifi.dao.GuestMapper;
import com.phicomm.smarthome.sharedwifi.service.GuestService;

/**
 * @author wenhua.tang
 *
 */
@Service
public class GuestServiceImpl implements GuestService {
	private static Logger logger = LoggerFactory.getLogger(GuestServiceImpl.class);
	@Autowired
	private GuestMapper guestMapper;

	@Override
	public long findByName(String routerMac, String deviceMac) {
		Long guestId = null;
		try {
			guestId = guestMapper.findByName(routerMac, deviceMac);
		} catch (Exception e) {
			logger.error(null, e);
		}
		if (guestId == null) {
			guestId = 0L;
		}
		return guestId.longValue();
	}

}
