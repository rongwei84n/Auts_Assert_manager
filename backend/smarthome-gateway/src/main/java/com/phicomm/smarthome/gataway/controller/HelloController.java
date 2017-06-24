/**
 * @author wenhua.tang
 */

package com.phicomm.smarthome.gataway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/dohello")
	public String hello() {
		return "Hello World Local";
	}

}