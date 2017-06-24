/**
 * @author wenhua.tang
 * 
 * 
 */

package com.phicomm.smarthome.gataway.filter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

//@Component
public class ThrowExceptionFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(ThrowExceptionFilter.class);

	@Override
	public String filterType() {
		return "post";
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
		log.info("This is a post filter, system throw a RuntimeException");
		RequestContext ctx = RequestContext.getCurrentContext();
		try {
			doSomething();
		} catch (Exception e) {
			/*
			 * ctx.set("error.status_code",
			 * HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			 * ctx.set("error.exception", e); ctx.set("error.message",
			 * "有一些错误发生");
			 */
			ctx.setResponseStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ctx.setResponseBody("{\"result\":\"server error\"}");// 返回错误内容
		}
		return null;
	}

	private void doSomething() {
		throw new RuntimeException("Exist some errors...");
	}

}
