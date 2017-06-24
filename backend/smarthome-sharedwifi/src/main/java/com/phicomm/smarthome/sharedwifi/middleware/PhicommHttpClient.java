package com.phicomm.smarthome.sharedwifi.middleware;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.phicomm.smarthome.sharedwifi.consts.Const;

/**
 * DESCRIPTION: 网络请求接口，http(s)的post/get请求 AUTHOR: liang04.zhang DATE: 2017/6/5
 */
public class PhicommHttpClient {
	private static final Logger logger = LogManager.getLogger(PhicommHttpClient.class);

	public static HttpEntity httpPost(String postUrl, String defaultCharset) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(postUrl);
		CloseableHttpResponse closeableHttpResponse = null;
		HttpEntity httpEntity = null;
		try {
			closeableHttpResponse = httpclient.execute(httpPost);
			httpEntity = closeableHttpResponse.getEntity();
			logger.debug("POST_Response content: {}", EntityUtils.toString(httpEntity, defaultCharset));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (closeableHttpResponse != null) {
					closeableHttpResponse.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return httpEntity;
	}

	public static String httpGet(String getUrl, String defaultCharset) throws KeyManagementException, NoSuchAlgorithmException {
		return httpGet(getUrl, defaultCharset, null);
	}

	public static String httpGet(String getUrl, String defaultCharset, Map<String, String> headerParas)
			throws KeyManagementException, NoSuchAlgorithmException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		return getInner(getUrl, defaultCharset, headerParas, httpclient);
	}

	public static String httpsGet(String getUrl, String defaultCharset) throws KeyManagementException, NoSuchAlgorithmException {
		return httpsGet(getUrl, defaultCharset, null);
	}

	public static String httpsGet(String getUrl, String defaultCharset, Map<String, String> headerParas)
			throws KeyManagementException, NoSuchAlgorithmException {
		// 采用绕过验证的方式处理https请求
		SSLContext sslcontext = createIgnoreVerifySSL();

		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", new SSLConnectionSocketFactory(sslcontext))
				.build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);

		// 创建自定义的httpclient对象
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager).build();

		return getInner(getUrl, defaultCharset, headerParas, httpclient);
	}

	private static String getInner(String getUrl, String defaultCharset, Map<String, String> headerParas, CloseableHttpClient httpclient) {
		HttpGet httpGet = new HttpGet(getUrl);
		if (headerParas != null && !headerParas.isEmpty()) {
			for (Map.Entry<String, String> entry : headerParas.entrySet()) {
				httpGet.addHeader(entry.getKey(), entry.getValue());
			}
		}

		CloseableHttpResponse closeableHttpResponse = null;
		HttpEntity httpEntity = null;
		try {
			closeableHttpResponse = httpclient.execute(httpGet);
			httpEntity = closeableHttpResponse.getEntity();
			return EntityUtils.toString(httpEntity, Const.DEFAULT_CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (closeableHttpResponse != null) {
					closeableHttpResponse.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate, String paramString)
					throws CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate, String paramString)
					throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}
}
