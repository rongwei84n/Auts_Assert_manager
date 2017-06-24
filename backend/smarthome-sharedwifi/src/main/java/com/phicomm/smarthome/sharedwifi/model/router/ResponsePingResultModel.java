package com.phicomm.smarthome.sharedwifi.model.router;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.jpa
 * PACKAGE_NAME: com.fhicomm.sharedwifi.model
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/6
 */
public class ResponsePingResultModel {

    @ApiModelProperty(value = "服务后台ping心跳响应实体配置文件对象")
    @JsonProperty("router_config")
    private RouterConfigModel routerConfigModel;

    @ApiModelProperty(value = "服务后台ping心跳响应实体白名单对象")
    @JsonProperty("white_list")
    private List<RouterWhiteListModel> routerWhiteListModelList;

    public ResponsePingResultModel(){}

    public ResponsePingResultModel(RouterConfigModel routerConfigModel,
                                   List<RouterWhiteListModel> routerWhiteListModelList)
    {
        this.routerConfigModel = routerConfigModel;
        this.routerWhiteListModelList = routerWhiteListModelList;
    }

    public RouterConfigModel getRouterConfigModel() {
        return routerConfigModel;
    }

    public void setRouterConfigModel(RouterConfigModel routerConfigModel) {
        this.routerConfigModel = routerConfigModel;
    }

    public List<RouterWhiteListModel> getRouterWhiteListModelList() {
        return routerWhiteListModelList;
    }

    public void setRouterWhiteListModelList(List<RouterWhiteListModel> routerWhiteListModelList) {
        this.routerWhiteListModelList = routerWhiteListModelList;
    }
}
