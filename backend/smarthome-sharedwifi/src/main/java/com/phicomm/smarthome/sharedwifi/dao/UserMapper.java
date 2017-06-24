package com.phicomm.smarthome.sharedwifi.dao;

import java.util.List;

import com.phicomm.smarthome.sharedwifi.model.app.UserDaoModel;

public interface UserMapper {
    public List<UserDaoModel> queryUidByOpenId(String openId);
    public void add(UserDaoModel model);
}
