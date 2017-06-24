package com.phicomm.smarthome.sharedwifi.model.app;

import com.phicomm.smarthome.sharedwifi.model.common.BaseDaoModel;

/**
 * 支付宝提现申请对应数据库表sw_user_with_draws_aliplay
 *
 * @author rongwei.huang
 *
 */
public class AppWithDrawsAlipayDaoModel extends BaseDaoModel {

    //斐讯云账号uid
    private String uid;

    //提现金额
    private String cost;

    //支付宝账号
    private String alipayAccount;

    //创建时间
    private long createTime;

    //更新时间
    private long updateTime;

    //状态
    private int status;

    //手机号
    private String phoneNum;

    //昵称
    private String nickname;

    //订单号
    private String orderNum;
    
    private String withDrawTime;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getWithDrawTime() {
        return withDrawTime;
    }

    public void setWithDrawTime(String withDrawTime) {
        this.withDrawTime = withDrawTime;
    }
}
