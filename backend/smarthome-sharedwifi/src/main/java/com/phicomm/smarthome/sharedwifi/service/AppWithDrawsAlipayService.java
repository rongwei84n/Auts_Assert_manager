package com.phicomm.smarthome.sharedwifi.service;

import java.util.List;

import com.phicomm.smarthome.sharedwifi.model.app.AppWithDrawsAlipayDaoModel;

public interface AppWithDrawsAlipayService {
    public void add(AppWithDrawsAlipayDaoModel model);

    public List<AppWithDrawsAlipayDaoModel> queryGreaterThanCreate(long createTime);
}
