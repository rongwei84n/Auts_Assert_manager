package com.phicomm.smarthome.sharedwifi.model.app.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.sharedwifi.model.common.BaseResponseModel;
import com.phicomm.smarthome.util.StringUtil;

/**
 * 【后台】查询收益  返回的参数实体类
 * @author rongwei.huang
 *
 */
public class AppGetSharedWifiIncomeResponseModel extends BaseResponseModel {
    @JsonProperty("today_income")
    private String todayIncome;
    
    @JsonProperty("total_income")
    private String totalIncome;
    
    public AppGetSharedWifiIncomeResponseModel() {
        super();
        todayIncome = totalIncome = StringUtil.formatNumberWithTwoPoint(0);
    }

    public String getTodayIncome() {
        return todayIncome;
    }

    public void setTodayIncome(String todayIncome) {
        this.todayIncome = todayIncome;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }
}
