/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.middleware;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phicomm.smarthome.consts.PhiCloudStatus;
import com.phicomm.smarthome.phiclouds.model.BindAccountResponseModel;
import com.phicomm.smarthome.phiclouds.model.PhiCloudData;
import com.phicomm.smarthome.sharedwifi.consts.Const;
import com.phicomm.smarthome.sharedwifi.consts.Const.ThirdParty;
import com.phicomm.smarthome.util.StringUtil;

/**
 * @author wenhua.tang
 *
 */
public class GetPhiCloudMacUid {
	private static final Logger logger = LogManager.getLogger(GetPhiCloudMacUid.class);
	private static final String authcode = "feixun.SH_7";

	public static PhiCloudData getRouterMacBindMsg(String routerMac) {

		if (StringUtil.isNullOrEmpty(routerMac)) {
			logger.error("routerMac is empty");
			return null;
		}

		PhiCloudData cloudData = null;

		try {
			String accountJson = PhicommHttpClient.httpsGet(
					ThirdParty.PHICOMM_CLOUD_BIND_ACCOUNT + "?authorizationcode=" + authcode + "&devType=1&devSN=" + routerMac,
					Const.DEFAULT_CHARSET, null);

			logger.info("phicomm msg:[{}]", accountJson);

			if (StringUtil.isNotEmpty(accountJson)) {
				ObjectMapper objectMapper = new ObjectMapper();
				BindAccountResponseModel parsedObj = objectMapper.readValue(accountJson, BindAccountResponseModel.class);
				if (parsedObj == null || parsedObj.getData() == null) {
					return null;
				}

				if (!StringUtil.equals(parsedObj.getError(), PhiCloudStatus.OK)) {
					logger.error("get phicomm router mac uid error " + parsedObj.getError());
					return null;
				}

				List<PhiCloudData> phiCloudDataList = parsedObj.getData();
				if (phiCloudDataList.size() <= 0) {
					logger.error("get phicomm router mac uid error " + parsedObj.getError());
					return null;
				}

				return phiCloudDataList.get(0);
			}
		} catch (Exception e) {
			logger.error(e);
		}

		return cloudData;
	}

	public static void main(String[] args) {
		PhiCloudData data = getRouterMacBindMsg("02:0C:43:75:20:58");

		System.out.printf("%s", data.getUid());
	}
}
