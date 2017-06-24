package com.phicomm.smarthome.registerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * SmartHome
 */
@EnableEurekaServer
@SpringBootApplication
public class SmartHomeRegisterMain {
	public static void main(String[] args) {
		// new
		// SpringApplicationBuilder(SmartHomeRegisterMain.class).web(true).run(args);
		SpringApplication.run(SmartHomeRegisterMain.class, args);
	}
}
