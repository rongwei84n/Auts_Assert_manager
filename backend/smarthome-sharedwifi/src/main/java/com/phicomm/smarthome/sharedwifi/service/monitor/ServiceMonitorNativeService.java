package com.phicomm.smarthome.sharedwifi.service.monitor;

import com.phicomm.smarthome.sharedwifi.model.monitor.CurServiceMonitorModel;

/**
 * PROJECT_NAME: liang04-smarthome-sharedwifi
 * PACKAGE_NAME: com.phicomm.smarthome.sharedwifi.service.monitor
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/20
 */
public interface ServiceMonitorNativeService {
    public CurServiceMonitorModel getMonitorCurrentData();

}
