/**
 *
 */
package com.phicomm.smarthome.sharedwifi.controller.app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.model.app.UserSharedWifPara;

/**
 * @author wenhua.tang
 *
 */
@RestController
public class AppGetSharedWifiPara {
	// private final Logger logger = Logger.getLogger(getClass());

	@RequestMapping(value = "/app/get_sharedwifi_para", method = RequestMethod.POST, produces = { "application/json" })
	public SmartHomeResponse<UserSharedWifPara> getSharedWifiPara() {
		SmartHomeResponse<UserSharedWifPara> smartHomeResponse = new SmartHomeResponse<UserSharedWifPara>();
		UserSharedWifPara sharedWifPara = new UserSharedWifPara();
		sharedWifPara.setSharedPara("0.1小时/0.03元");
		smartHomeResponse.setResult(sharedWifPara);
		return smartHomeResponse;
	}
}
