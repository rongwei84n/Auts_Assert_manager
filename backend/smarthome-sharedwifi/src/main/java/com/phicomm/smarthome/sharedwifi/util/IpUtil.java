package com.phicomm.smarthome.sharedwifi.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @see {TODO 类说明}
 * @author 80095447:{作者名}
 * @Date 2015年9月11日
 */
public class IpUtil {
	/**
	 * 未知IP的定义
	 */
	public static final String UNKNOWN_IP = "unknown";
	private static final Logger logger = LogManager.getLogger(IpUtil.class);

	/**
	 * @see 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		logger.info("get client ip[{}] by x-forwarded-for", ip);
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.info("get client ip[{}] by Proxy-Client-IP", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			logger.info("get client ip[{}] byWL-Proxy-Client-IP", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			logger.info("get client ip[{}] by HTTP_CLIENT_IP", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			logger.info("get client ip[{}] by HTTP_X_FORWARDED_FOR", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		logger.info("get client ip[{}] by getRemoteAddr", ip);
		return getRealIp(ip, false);
	}

	/**
	 * @see 解析真实Ip
	 * @param ip
	 * @param forwarded
	 * @return
	 */
	private static String getRealIp(String ip, boolean forwarded) {
		if (forwarded) {
			// 使用代理时将逗号前的截取为最终ip
			int index = ip.indexOf(',');
			if (index == -1) {
				return ip;
			} else {
				return ip.substring(0, index);
			}
		} else {
			int index0 = ip.indexOf('/');
			int index1 = ip.indexOf(':', index0);
			if (index1 >= 0) {
				return ip.substring(index0 + 1, index1);
			} else {
				return ip.substring(index0 + 1);
			}
		}
	}

	/**
	 * @see IP转换为数字
	 * @param ipAddress
	 * @return
	 */
	public static long ipToNum(String ipAddress) {
		long result = 0;
		String[] ipAddressInArray = ipAddress.split("\\.");
		for (int i = 3; i >= 0; i--) {
			long ip = Long.parseLong(ipAddressInArray[3 - i]);
			// left shifting 24,16,8,0 and bitwise OR
			// 1. 192 << 24
			// 1. 168 << 16
			// 1. 1 << 8
			// 1. 2 << 0
			result |= ip << (i * 8);
		}
		return result;
	}

	/**
	 * @see 数字转换为IP
	 * @param i
	 * @return
	 */
	public static String numToIp(long i) {
		return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
	}

	public static void main(String[] args) {
		System.out.println(getRealIp("1823/asd:df", false));
		System.out.println(getRealIp("1823/asd", false));
		System.out.println(getRealIp("::ffff:202.120.2.30", false));
		System.out.println(getRealIp("1823asd", false));
		System.out.println(getRealIp("s/", false));
	}
}
