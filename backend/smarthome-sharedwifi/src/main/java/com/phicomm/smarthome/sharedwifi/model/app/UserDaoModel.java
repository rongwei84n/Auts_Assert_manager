package com.phicomm.smarthome.sharedwifi.model.app;

import com.phicomm.smarthome.sharedwifi.model.common.BaseDaoModel;
import com.phicomm.smarthome.sharedwifi.model.common.PhicommAccountDetailModel;
import com.phicomm.smarthome.util.StringUtil;

/**
 * sw_user表的Dao对应bean类
 * @author rongwei.huang
 *
 */
public class UserDaoModel extends BaseDaoModel {
    //用户uid，用来对应斐讯云的uid
    private String uid;

    //微信open_id
    private String openId;

    //微信昵称
    private String wxNickName;

    //微信头像url
    private String wxHeadIconUrl;

    //手机电话号码
    private String iPhone;

    //账户名
    private String accountName;

    //年龄
    private String age;

    //图片地址
    private String img;

    //邮箱地址
    private String mailAddress;

    //用户在斐讯的昵称
    private String phiNickName;

    //用户真实姓名
    private String realName;

    //1 代表男 2代表女
    private int sex;

    //邮编
    private String zipCode;

    //区域
    private String zone;

    //创建时间
    private Long createTime;

    //更新时间
    private Long updateTime;

    //状态 0 正常 -1 删除
    private int status;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

    public String getWxHeadIconUrl() {
        return wxHeadIconUrl;
    }

    public void setWxHeadIconUrl(String wxHeadIconUrl) {
        this.wxHeadIconUrl = wxHeadIconUrl;
    }

    public String getiPhone() {
        return iPhone;
    }

    public void setiPhone(String iPhone) {
        this.iPhone = iPhone;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPhiNickName() {
        return phiNickName;
    }

    public void setPhiNickName(String phiNickName) {
        this.phiNickName = phiNickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserDaoModel() {}

    public UserDaoModel(PhicommAccountDetailModel phiAccount, String openId) {
        uid = phiAccount.getData().getUid();
        this.openId = openId;
        wxNickName = ""; //TODO 微信昵称
        wxHeadIconUrl = "";//TODO 微信头像Url

        iPhone = phiAccount.getData().getPhonenumber();
        accountName = phiAccount.getData().getAccountname();
        age = phiAccount.getData().getAge();
        img = phiAccount.getData().getImg();
        mailAddress = phiAccount.getData().getMailaddress();
        phiNickName = phiAccount.getData().getNickname();
        realName = phiAccount.getData().getRealname();

        if (StringUtil.isDigitsOnly(phiAccount.getData().getSex())) {
            sex = Integer.parseInt(phiAccount.getData().getSex());
        }else {
            sex = 1;
        }
        zipCode = phiAccount.getData().getZipcode();
        zone = phiAccount.getData().getZone();
        createTime = System.currentTimeMillis()/1000;
        updateTime = createTime;
        status = 0;
    }
}
