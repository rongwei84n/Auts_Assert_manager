package com.phicomm.smarthome.gataway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author wenhua.tang
 */
@EnableZuulProxy
@SpringCloudApplication
public class SmartHomeGateWayMain {
	public static void main(String[] args) {
		// new
		// SpringApplicationBuilder(SmartHomeGateWayMain.class).web(true).run(args);
		SpringApplication.run(SmartHomeGateWayMain.class, args);
	}

	// @Bean
	// public AccessFilter accessFilter() {
	// return new AccessFilter();
	// }

	// @Bean
	// public PatternServiceRouteMapper serviceRouteMapper() {
	// return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)",
	// "${version}/${name}");
	// }

	// @Bean
	// public ErrorFilter errorFilter() {
	// return new ErrorFilter();
	// }
}
