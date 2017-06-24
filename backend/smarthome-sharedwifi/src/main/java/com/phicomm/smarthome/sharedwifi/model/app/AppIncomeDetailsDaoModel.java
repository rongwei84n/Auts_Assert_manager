package com.phicomm.smarthome.sharedwifi.model.app;

import com.phicomm.smarthome.sharedwifi.model.common.BaseDaoModel;

/**
 * 后台零钱明细数据库对应实体类
 * @author rongwei.huang
 *
 */
public class AppIncomeDetailsDaoModel extends BaseDaoModel{
    //用户id
    private String uid;

    //路由器mac地址
    private String routerMac;

    //操作名称
    private String actionName;

    //操作金额
    private String cost;

    //创建时间
    private Long createTime;

    //更新时间
    private Long updateTime;

    //状态 0 正常 -1 删除
    private Byte status;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRouterMac() {
        return routerMac;
    }

    public void setRouterMac(String routerMac) {
        this.routerMac = routerMac;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
