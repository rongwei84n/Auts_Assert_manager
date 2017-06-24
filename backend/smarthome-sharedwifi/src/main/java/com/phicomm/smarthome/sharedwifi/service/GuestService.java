/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.service;

/**
 * @author wenhua.tang
 *
 */
public interface GuestService {
	long findByName(String routerMac, String deviceMac);
}
