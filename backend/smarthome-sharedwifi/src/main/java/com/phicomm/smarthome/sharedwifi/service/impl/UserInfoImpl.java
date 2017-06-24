package com.phicomm.smarthome.sharedwifi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.smarthome.sharedwifi.dao.UserMapper;
import com.phicomm.smarthome.sharedwifi.model.app.UserDaoModel;
import com.phicomm.smarthome.sharedwifi.service.UserInfo;

@Service
public class UserInfoImpl implements UserInfo{

    @Autowired
    private UserMapper userDao;

    @Override
    public List<UserDaoModel> queryUidByOpenId(String openId) {
        return userDao.queryUidByOpenId(openId);
    }

    @Override
    public void add(UserDaoModel model) {
        userDao.add(model);
    }
}
