package com.phicomm.smarthome.sharedwifi.service.impl;

import com.phicomm.smarthome.sharedwifi.protocol.unifiedorder.UnifiedorderReqData;
import com.phicomm.smarthome.sharedwifi.util.Configure;
import com.phicomm.smarthome.sharedwifi.util.wxutil.BaseService;

/**
 * User: wenhua.tang Date: 2017/06/18 Time: 16:03
 */

public class UnifiedorderService extends BaseService {

	public UnifiedorderService() {
		super(Configure.UNIFIEDORDER_API);
	}

	/**
	 * 请求支付服务
	 * 
	 * @param scanPayReqData
	 *            这个数据对象里面包含了API要求提交的各种数据字段
	 * @return API返回的数据
	 * @throws Exception
	 */
	public String request(UnifiedorderReqData unifiedorderReqData) throws Exception {

		// --------------------------------------------------------------------
		// 发送HTTPS的Post请求到API地址
		// --------------------------------------------------------------------
		String responseString = sendPost(unifiedorderReqData);

		return responseString;
	}
}
