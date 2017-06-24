package com.phicomm.smarthome.sharedwifi.service.impl;

import com.phicomm.smarthome.sharedwifi.model.router.RouterConfigModel;
import com.phicomm.smarthome.sharedwifi.repository.RouterConfigModelJpaGerepository;
import com.phicomm.smarthome.sharedwifi.service.RouterConfigModelJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
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
public class RouterConfigModelJpaServiceImpl implements RouterConfigModelJpaService {

    @Autowired
    private RouterConfigModelJpaGerepository routerConfigModelJpaGerepository;

    /**
     * ApiService 4 ConfigPingBeatResultModel
     *
     * @param id
     */
    @Override
    public RouterConfigModel getRouterConfigModelByIdJpaApi(long id) {
        return routerConfigModelJpaGerepository.findById(id);
    }

    @Override
    public RouterConfigModel getRouterConfigModelByConfigVersionJpaApi(String configVersion) {
        return routerConfigModelJpaGerepository.findByConfigVersion(configVersion);
    }

    @Override
    public List<RouterConfigModel> getRouterConfigModelByCreateTimeJpaApi(String createTime) {
        return routerConfigModelJpaGerepository.findByCreateTime(createTime);
    }

    @Override
    public List<RouterConfigModel> getRouterConfigModelByIsForbiddenJpaApi(int isForbidden) {
        return routerConfigModelJpaGerepository.findByIsForbidden(isForbidden);
    }

    @Override
    public List<RouterConfigModel> getRouterConfigModelByLoginUrlJpaApi(String loginUrl) {
        return routerConfigModelJpaGerepository.findByLoginUrl(loginUrl);
    }

    @Override
    public List<RouterConfigModel> getRouterConfigModelByUpdaterTimeJpaApi(String updaterTime) {
        return routerConfigModelJpaGerepository.findByUpdaterTime(updaterTime);
    }

    @Override
    public RouterConfigModel setPingBeatResultJpaApi(RouterConfigModel routerConfigModel) {
        return routerConfigModelJpaGerepository.saveAndFlush(routerConfigModel);
    }

    @Override
    public RouterConfigModel setPingBeatResultJpaApi(long routerId, String configVersion, int isForbidden, String loginUrl) {
        return routerConfigModelJpaGerepository.saveAndFlush(new RouterConfigModel(configVersion, routerId, isForbidden, loginUrl));
    }

    @Override
    public RouterConfigModel deletePingBeatResultJpaApi(RouterConfigModel routerConfigModel) {
        routerConfigModelJpaGerepository.delete(routerConfigModel);
        return routerConfigModelJpaGerepository.findByConfigVersion(routerConfigModel.getConfigVersion());
    }

    @Override
    public List<RouterConfigModel> isExistsRouterConfigModelJpaApi(long routerId,
                                                                   String configVersion,
                                                                   int isForbidden,
                                                                   String portalUrl){
        return routerConfigModelJpaGerepository.isExistsRouterConfigModel(routerId, configVersion, isForbidden, portalUrl);
    }
}
