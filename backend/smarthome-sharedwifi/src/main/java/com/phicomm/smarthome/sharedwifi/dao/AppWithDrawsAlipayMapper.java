package com.phicomm.smarthome.sharedwifi.dao;

import java.util.List;

import com.phicomm.smarthome.sharedwifi.model.app.AppWithDrawsAlipayDaoModel;

public interface AppWithDrawsAlipayMapper {
    public void add(AppWithDrawsAlipayDaoModel model);
    public List<AppWithDrawsAlipayDaoModel> queryGreaterThanCreate(long time);
}
