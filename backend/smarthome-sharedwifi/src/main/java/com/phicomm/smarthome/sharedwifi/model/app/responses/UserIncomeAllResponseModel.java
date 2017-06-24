package com.phicomm.smarthome.sharedwifi.model.app.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.sharedwifi.model.common.BaseResponseModel;

public class UserIncomeAllResponseModel extends BaseResponseModel{

    @JsonProperty("total_income")
    private String totalIncome;

    public UserIncomeAllResponseModel() {
        super();
    }

    public UserIncomeAllResponseModel(String totalIncome) {
        super();
        this.totalIncome = totalIncome;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }
}
