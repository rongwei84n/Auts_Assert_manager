package com.phicomm.smarthome.sharedwifi.service.impl;

import com.phicomm.smarthome.sharedwifi.model.router.RouterWhiteListModel;
import com.phicomm.smarthome.sharedwifi.repository.RouterWhiteListModelJpaGerepository;
import com.phicomm.smarthome.sharedwifi.service.RouterWhiteListModelJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.jpa
 * PACKAGE_NAME: com.fhicomm.sharedwifi.impl
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/6
 */
@Transactional
@Service
public class RouterWhiteListModelJpaServiceImpl implements RouterWhiteListModelJpaService {

    @Autowired
    private RouterWhiteListModelJpaGerepository routerWhiteListModelJpaGerepository;

    /**
     * ApiService 4 RouterWhiteListModel
     */
    @Override
    public RouterWhiteListModel getWhiteListModelByIdJpaApi(long id) {
        return routerWhiteListModelJpaGerepository.findById(id);
    }

    @Override
    public List<RouterWhiteListModel> getWhiteListModelByConfigIdJpaApi(long configId) {
        return routerWhiteListModelJpaGerepository.findByConfigId(configId);
    }

    @Override
    public List<RouterWhiteListModel> getWhiteListModelByCreateTimeJpaApi(long createTime) {
        return routerWhiteListModelJpaGerepository.findByCreateTime(createTime);
    }

    @Override
    public List<RouterWhiteListModel> getWhiteListModelByHostJpaApi(String host) {
        return routerWhiteListModelJpaGerepository.findByHost(host);
    }

    @Override
    public List<RouterWhiteListModel> getWhiteListModelByProtocolJpaApi(String protocol) {
        return routerWhiteListModelJpaGerepository.findByProtocol(protocol);
    }

    @Override
    public List<RouterWhiteListModel> getWhiteListModelByUpdaterTimeJpaApi(long updaterDate) {
        return routerWhiteListModelJpaGerepository.findByUpdaterTime(updaterDate);
    }

    @Override
    public RouterWhiteListModel setWhiteListModelJpaApi(RouterWhiteListModel routerWhiteListModel) {
        return routerWhiteListModelJpaGerepository.saveAndFlush(routerWhiteListModel);
    }

    @Override
    public List<RouterWhiteListModel> deleteWhiteListModelJpaApi(RouterWhiteListModel routerWhiteListModel) {
        routerWhiteListModelJpaGerepository.delete(routerWhiteListModel);
        return routerWhiteListModelJpaGerepository.findByConfigId(routerWhiteListModel.getConfigId());
    }

    @Override
    public List<RouterWhiteListModel> isExistsWhiteListModelJpaApi(RouterWhiteListModel routerWhiteListModel) {
        return routerWhiteListModelJpaGerepository.isExistsRouterWhiteListModel(routerWhiteListModel.getConfigId(),
                                                                                routerWhiteListModel.getHost(),
                                                                                routerWhiteListModel.getPort(),
                                                                                routerWhiteListModel.getProtocol());
    }


}
