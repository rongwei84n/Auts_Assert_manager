/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.controller.h5web;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.sharedwifi.service.impl.GuestOrderStatusServiceImpl;
import com.phicomm.smarthome.sharedwifi.util.Util;
import com.phicomm.smarthome.sharedwifi.util.wxutil.WxPaiedNotifyRequest;
import com.phicomm.smarthome.sharedwifi.util.wxutil.WxPaiedNotifyResponse;
import com.phicomm.smarthome.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * @author wenhua.tang
 *
 */

@RestController
public class WxPaiedNotifyController {

	private static final Logger logger = LogManager.getLogger(WxPaiedNotifyController.class);
	@Autowired
	private GuestOrderStatusServiceImpl guestOrderStatusServiceImpl;

	@RequestMapping(value = "/h5web/wxpay.action", method = RequestMethod.POST)
	public String recieveNotify(HttpServletRequest request) {
		WxPaiedNotifyResponse wxPaiedNotifyResponse = new WxPaiedNotifyResponse();

		String wxPaiedNotifyRequestStr = "";
		try {
			wxPaiedNotifyRequestStr = charReader(request);
			logger.info("receive wx request[{}]", wxPaiedNotifyRequestStr);
		} catch (IOException e) {
			logger.error(e);
		}

		if (StringUtil.isNullOrEmpty(wxPaiedNotifyRequestStr)) {
			logger.error("wx requst empty");
			wxPaiedNotifyResponse.setReturn_code("FAIL");
			wxPaiedNotifyResponse.setReturn_msg("ERROR");
		} else {

			WxPaiedNotifyRequest wxPaiedNotifyRequest = (WxPaiedNotifyRequest) Util.getObjectFromXML(wxPaiedNotifyRequestStr,
					WxPaiedNotifyRequest.class);

			if (!wxPaiedNotifyRequest.getResult_code().equals("SUCCESS")) {
				wxPaiedNotifyResponse.setReturn_code("FAIL");
				wxPaiedNotifyResponse.setReturn_msg("ERROR");
			} else {
				// 更新订单结果
				// GuestOrder guestOrder = new GuestOrder();
				// guestOrder.setOrderId(wxPaiedNotifyRequest.getOut_trade_no());
				// guestOrder.setOrderPayStatus(OrderPayStatusInterfaces.ORDER_PAY_STATUS_PAID);
				// guestOrder.setOrderStatus(OrderStatusInterfaces.ORDER_STATUS_PAIDINCOMMON);

				// int affected = (int)
				// guestOrderStatusServiceImpl.updateGuestOrder(guestOrder);
				int affected = 1;
				if (affected <= 0) {
					wxPaiedNotifyResponse.setReturn_code("FAIL");
					wxPaiedNotifyResponse.setReturn_msg("ERROR");
				} else {
					wxPaiedNotifyResponse.setReturn_code("SUCCESS");
					wxPaiedNotifyResponse.setReturn_msg("OK");
				}
			}
		}

		// 解决XStream对出现双下划线的bug
		XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xStreamForRequestPostData.alias("xml", WxPaiedNotifyResponse.class);
		String xmlobject = xStreamForRequestPostData.toXML(wxPaiedNotifyResponse);
		return xmlobject;
	}

	private String charReader(HttpServletRequest request) throws IOException {

		BufferedReader br = request.getReader();

		String str, wholeStr = "";
		while ((str = br.readLine()) != null) {
			wholeStr += str;
		}

		return wholeStr;

	}

}
