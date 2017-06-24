package com.phicomm.smarthome.sharedwifi.model.app.request;

import java.io.Serializable;

/**
 * 收入明细 接收的请求参数
 * @author rongwei.huang
 *
 */
public class UserIncomeDetailRequestModel implements Serializable {
    private int page; //页码(1,2,3...)
    private int timestamp; //最新记录的时间戳
    private String token; //云账户

    public UserIncomeDetailRequestModel() {
        super();
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
