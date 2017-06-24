package com.phicomm.smarthome.sharedwifi.model.router;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.sharedwifi
 * PACKAGE_NAME: com.fhicomm.sharedwifi.model
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/8
 */
public class RequestAuthBodyModel {

    /** 动作（login|query|logout） */
    private String action;

    /** 路由器mac */
    @ApiModelProperty(value = "路由器MAC地址")
    @JsonProperty("router_mac")
    private String routerMac;

    /** auth_list:[
     *              device_mac 设备mac,
     *              order_id   终端的订单id
     *            ]
     * */
    @ApiModelProperty(value = "路由器请求授权的终端列表")
    @JsonProperty("auth_list")
    private List<ItemAuthBeatModel> itemAuthBeatModelList;

    public RequestAuthBodyModel(){}

    public RequestAuthBodyModel(String action, String routerMac, List<ItemAuthBeatModel> itemAuthBeatModelList){
        this.action = action;
        this.routerMac = routerMac;
        this.itemAuthBeatModelList = itemAuthBeatModelList;
    }

    public List<ItemAuthBeatModel> getItemAuthBeatModelList() {
        return itemAuthBeatModelList;
    }

    public void setItemAuthBeatModelList(List<ItemAuthBeatModel> itemAuthBeatModelList) {
        this.itemAuthBeatModelList = itemAuthBeatModelList;
    }

    public String getRouterMac() {
        return routerMac;
    }

    public void setRouterMac(String routerMac) {
        this.routerMac = routerMac;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
