/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.model.Guest;
import com.phicomm.smarthome.sharedwifi.web.dao.GuestMapper;
import com.phicomm.smarthome.sharedwifi.web.service.GuestService;

/**
 * @author wenhua.tang
 *
 */
@Service
public class GuestServiceImpl implements GuestService {
	@Autowired
	private GuestMapper guestMapper;

	public int insertGuest(String deviceMac, String routerMac) {
		int num = guestMapper.findByName(routerMac, deviceMac);
		if (num <= 0) {
			Guest guest = new Guest();
			guest.setDeviceMac(deviceMac);
			guest.setRouterMac(routerMac);
			guest.setCreateTime(System.currentTimeMillis() / 1000);
			guest.setUpdateTime(System.currentTimeMillis() / 1000);
			num = guestMapper.insertGuest(guest);
		}
		return num;
	}

}
