package com.phicomm.smarthome.sharedwifi.service.impl.monitor;

import com.phicomm.smarthome.sharedwifi.model.monitor.CurServiceMonitorModel;
import com.phicomm.smarthome.sharedwifi.service.monitor.ServiceMonitorNativeService;
import org.springframework.stereotype.Service;

/**
 * PROJECT_NAME: liang04-smarthome-sharedwifi
 * PACKAGE_NAME: com.phicomm.smarthome.sharedwifi.service.impl.monitor
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/20
 */

@Service
public class ServiceMonitorNativeServiceImpl implements ServiceMonitorNativeService {

    @Override
    public CurServiceMonitorModel getMonitorCurrentData() {
        return new CurServiceMonitorModel("Sharedwifi-Service",
                                        "Version: 0.0.1-SNAPSHOT",
                                        "running",
                                        "DB_Sharedwifi",
                                        "running");
    }
}
