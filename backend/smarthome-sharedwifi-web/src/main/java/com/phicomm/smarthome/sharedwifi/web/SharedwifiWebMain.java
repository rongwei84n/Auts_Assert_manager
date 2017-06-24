package com.phicomm.smarthome.sharedwifi.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = { "com.phicomm.smarthome.sharedwifi.**" })
@MapperScan("com.phicomm.smarthome.sharedwifi.web.dao.**")
public class SharedwifiWebMain {
	public static void main(String[] args) {
		SpringApplication.run(SharedwifiWebMain.class, args);
	}
}