/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.phicomm.smarthome.sharedwifi.web.service.impl.GuestServiceImpl;
import com.phicomm.smarthome.util.StringUtil;

/**
 * @author wenhua.tang
 *
 */
@Controller
public class PortalPage {
	private static final Logger logger = LogManager.getLogger(PortalPage.class);
	@Autowired
	private GuestServiceImpl guestServiceImpl;

	@RequestMapping("/login.html")
	public String helloHtml(Map<String, Object> map, @RequestParam("device_ip") String device_ip,
			@RequestParam("device_mac") String device_mac, @RequestParam("router_ip") String router_ip,
			@RequestParam("router_mac") String router_mac, @RequestParam("router_port") String router_port,
			HttpServletRequest request) {

		logger.debug("devicemac[%s]", device_mac);
		map.put("deviceIp", device_ip);
		map.put("deviceMac", device_mac);
		map.put("routerIp", router_ip);
		map.put("routerMac", router_mac);
		map.put("routerPort", router_port);

		map.put("onlineTimeUnit", "0.1");
		map.put("onlineTimeUnitPrice", "0.03");
		map.put("sign", "1236123#$#%1");
		map.put("timestamp", System.currentTimeMillis() / 1000);

		guestServiceImpl.insertGuest(device_mac, router_mac);

		String userAgent = request.getHeader("User-Agent");
		if (StringUtil.isNullOrEmpty(userAgent)) {
			return "login";
		}

		userAgent = userAgent.toLowerCase();
		if (!StringUtil.isMobile(userAgent)) {
			return "login";
		}
		return "login_h5";
	}
}
