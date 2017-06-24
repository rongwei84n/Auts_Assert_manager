package com.phicomm.smarthome.sharedwifi.service;

import java.util.List;

import com.phicomm.smarthome.sharedwifi.model.app.UserDaoModel;

/**
 * sw_user表的操作接口
 * @author rongwei.huang
 *
 */
public interface UserInfo {
    /*
     * 根据openId查询关联的uid，也就是查询用户微信号是否绑定了斐讯帐号
     */
    List<UserDaoModel> queryUidByOpenId(String openId);

    void add(UserDaoModel model);
}
