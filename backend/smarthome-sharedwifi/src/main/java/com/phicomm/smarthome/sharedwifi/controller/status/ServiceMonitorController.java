package com.phicomm.smarthome.sharedwifi.controller.status;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.service.monitor.ServiceMonitorNativeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * PROJECT_NAME: liang04-smarthome-sharedwifi
 * PACKAGE_NAME: com.phicomm.smarthome.sharedwifi.controller.status
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/20
 */

@RestController
@ComponentScan
@EnableSwagger2
public class ServiceMonitorController {

    @Autowired
    private ServiceMonitorNativeService serviceMonitorNativeService;


    @ApiOperation(value = "服务运行状态监控", httpMethod ="POST",
            notes = "SSP<->Service:服务运行状态监控，获取当前服务状态及对应数据库连接状态")
    @RequestMapping(value = "/monitor/sharedwifi", produces = { "application/json" })
    public SmartHomeResponse getServiceMonitorStatus()
    {
        return new SmartHomeResponse(0, "Monitor Message",serviceMonitorNativeService.getMonitorCurrentData());
    }

}
