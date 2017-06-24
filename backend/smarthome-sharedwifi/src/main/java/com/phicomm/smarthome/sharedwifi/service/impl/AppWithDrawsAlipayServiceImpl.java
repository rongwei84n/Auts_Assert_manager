package com.phicomm.smarthome.sharedwifi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.sharedwifi.dao.AppWithDrawsAlipayMapper;
import com.phicomm.smarthome.sharedwifi.model.app.AppWithDrawsAlipayDaoModel;
import com.phicomm.smarthome.sharedwifi.service.AppWithDrawsAlipayService;

@Service
public class AppWithDrawsAlipayServiceImpl implements AppWithDrawsAlipayService {

    @Autowired
    private AppWithDrawsAlipayMapper alipayDaoMapper;

    @Override
    public void add(AppWithDrawsAlipayDaoModel model) {
        alipayDaoMapper.add(model);
    }

    @Override
    public List<AppWithDrawsAlipayDaoModel> queryGreaterThanCreate(long createTime) {
        return alipayDaoMapper.queryGreaterThanCreate(createTime);
    }
}
