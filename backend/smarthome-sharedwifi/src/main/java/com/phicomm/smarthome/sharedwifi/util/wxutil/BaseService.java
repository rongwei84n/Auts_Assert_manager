package com.phicomm.smarthome.sharedwifi.util.wxutil;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.phicomm.smarthome.sharedwifi.service.IServiceRequest;
import com.phicomm.smarthome.sharedwifi.util.Configure;

/**
 * User: wenhua.tang
 * 
 * Date: 2017/06/18 Time: 15:44 服务的基类
 */
public class BaseService {
	private static final Logger logger = LogManager.getLogger(BaseService.class);
	// API的地址
	private String apiURL;

	// 发请求的HTTPS请求器
	private IServiceRequest serviceRequest;

	public BaseService(String api) {
		apiURL = api;
		try {
			Class c = Class.forName(Configure.HttpsRequestClassName);
			serviceRequest = (IServiceRequest) c.newInstance();
		} catch (Exception e) {
			logger.error(e);
		}

	}

	protected String sendPost(Object xmlObj) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException,
			KeyStoreException, KeyManagementException {
		return serviceRequest.sendPost(apiURL, xmlObj);
	}

	/**
	 * 供商户想自定义自己的HTTP请求器用
	 * 
	 * @param request
	 *            实现了IserviceRequest接口的HttpsRequest
	 */
	public void setServiceRequest(IServiceRequest request) {
		serviceRequest = request;
	}
}
