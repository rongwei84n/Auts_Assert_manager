/**
 * @author wenhua.tang
 * 
 * 
 */

package com.phicomm.smarthome.gataway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;

public class AccessFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		/*
		 * RequestContext ctx = RequestContext.getCurrentContext();
		 * HttpServletRequest request = ctx.getRequest();
		 * 
		 * log.info("send {} request to {}", request.getMethod(),
		 * request.getRequestURL().toString());
		 * 
		 * Object accessToken = request.getParameter("accessToken"); if
		 * (accessToken == null) { log.warn("access token is empty");
		 * ctx.setSendZuulResponse(false); ctx.setResponseStatusCode(401);
		 * 
		 * ctx.setResponseBody("{\"result\":\"access token is empty\"}");//
		 * 返回错误内容 ctx.set("isSuccess", false); return null; }
		 * log.info("access token ok"); return null;
		 */
		return null;
	}

}
