package com.phicomm.smarthome.sharedwifi.model.router;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.sharedwifi
 * PACKAGE_NAME: com.fhicomm.sharedwifi.model
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/7
 */

public class ResponseAuthResultModel{

    @ApiModelProperty(value = "服务后台授权功能响应的终端列表")
    @JsonProperty("auth_result_list")
    private List<ItemAuthResultModel> itemAuthResultModelList;

    public ResponseAuthResultModel(){}

    public ResponseAuthResultModel(List<ItemAuthResultModel> itemAuthResultModelList){
        this.itemAuthResultModelList = itemAuthResultModelList;
    }

    public List<ItemAuthResultModel> getItemAuthResultModelList() {
        return itemAuthResultModelList;
    }

    public void setItemAuthResultModelList(List<ItemAuthResultModel> itemAuthResultModelList) {
        this.itemAuthResultModelList = itemAuthResultModelList;
    }

}
