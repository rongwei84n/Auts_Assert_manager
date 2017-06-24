package com.phicomm.smarthome.sharedwifi.service;

import com.phicomm.smarthome.sharedwifi.model.router.RouterWhiteListModel;

import java.util.List;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.jpa
 * PACKAGE_NAME: com.fhicomm.sharedwifi.service
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/6
 */
public interface RouterWhiteListModelJpaService {

    /**
     *  ApiService 4 RouterWhiteListModel
     * */

    public RouterWhiteListModel getWhiteListModelByIdJpaApi(long id);
    public List<RouterWhiteListModel> getWhiteListModelByConfigIdJpaApi(long configId);
    public List<RouterWhiteListModel> getWhiteListModelByCreateTimeJpaApi(long createTime);
    public List<RouterWhiteListModel> getWhiteListModelByHostJpaApi(String host);
    public List<RouterWhiteListModel> getWhiteListModelByProtocolJpaApi(String protocol);
    public List<RouterWhiteListModel> getWhiteListModelByUpdaterTimeJpaApi(long updaterTime);

    public RouterWhiteListModel setWhiteListModelJpaApi(RouterWhiteListModel routerWhiteListModel);
    public List<RouterWhiteListModel> deleteWhiteListModelJpaApi(RouterWhiteListModel routerWhiteListModel);
    public List<RouterWhiteListModel>isExistsWhiteListModelJpaApi(RouterWhiteListModel routerWhiteListModel);
}
