package com.phicomm.smarthome.sharedwifi.service;

import com.phicomm.smarthome.sharedwifi.model.router.RouterConfigModel;
import java.util.List;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.jpa
 * PACKAGE_NAME: com.fhicomm.sharedwifi.service
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/6
 */
public interface RouterConfigModelJpaService {

    /**
     *  ApiService 4 ConfigPingBeatResultModel
     * */
    public RouterConfigModel getRouterConfigModelByIdJpaApi(long id);
    public RouterConfigModel getRouterConfigModelByConfigVersionJpaApi(String configVersion);
    public List<RouterConfigModel> getRouterConfigModelByCreateTimeJpaApi(String createTime);
    public List<RouterConfigModel> getRouterConfigModelByIsForbiddenJpaApi(int isForbidden);
    public List<RouterConfigModel> getRouterConfigModelByLoginUrlJpaApi(String loginUrl);
    public List<RouterConfigModel> getRouterConfigModelByUpdaterTimeJpaApi(String updaterTime);

    public RouterConfigModel setPingBeatResultJpaApi(RouterConfigModel routerConfigModel);
    public RouterConfigModel setPingBeatResultJpaApi(long routerId, String configVersion, int isForbidden, String loginUrl);
    public RouterConfigModel deletePingBeatResultJpaApi(RouterConfigModel routerConfigModel);
    public List<RouterConfigModel> isExistsRouterConfigModelJpaApi(long routerId,
                                                                   String configVersion,
                                                                   int isForbidden,
                                                                   String portalUrl);

}
