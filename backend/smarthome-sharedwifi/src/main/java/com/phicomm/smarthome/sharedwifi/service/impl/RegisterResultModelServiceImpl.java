package com.phicomm.smarthome.sharedwifi.service.impl;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.consts.Const;
import com.phicomm.smarthome.sharedwifi.model.router.RequestRegisterBodyModel;
import com.phicomm.smarthome.sharedwifi.model.router.ResponseRegisterResultModel;

import com.phicomm.smarthome.sharedwifi.model.router.RouterItemModel;
import com.phicomm.smarthome.sharedwifi.repository.RouterItemModelJpaGerepository;
import com.phicomm.smarthome.sharedwifi.service.RegisterResultModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.sharedwifi
 * PACKAGE_NAME: com.fhicomm.sharedwifi.impl
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/8
 */
@Service
public class RegisterResultModelServiceImpl implements RegisterResultModelService {

    @Autowired
    private RouterItemModelJpaGerepository routerItemModelJpaGerepository;

    @Override
    public SmartHomeResponse<Object> getRegisterResultModelApi(RequestRegisterBodyModel requestRegisterBodyModel) {

        if(null == verificationParameter4RequestRegisterBodyModel(requestRegisterBodyModel))
        {
            return new SmartHomeResponse(Const.ResponseStatus.STATUS_COMMON_NULL, Const.ResponseStatus.STATUS_COMMON_NULL_STR,null);
        }

        /* 按IP、MAC、Model、Rom_version插入数据库前查询当前 Model 对应表内是否已存在，去除处理 */
        if(routerItemModelJpaGerepository.isExistsRouterModel(requestRegisterBodyModel.getRouterIp(),
                requestRegisterBodyModel.getRouterMac(),
                requestRegisterBodyModel.getModel(),
                requestRegisterBodyModel.getRomVersion()).size() > 0){

            return new SmartHomeResponse(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED, Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED_STR,
                    new ResponseRegisterResultModel(0,0,"当前路由器已经注册",30*24*3600));

        }

        /* 按MAC作为路由器唯一标示插入数据库前查询当前 Model 对应表内是否已存在，去除处理 */
        if(routerItemModelJpaGerepository.isExistsByFindRouterMac4RouterModel(requestRegisterBodyModel.getRouterMac()).size() > 0)
        {

            return new SmartHomeResponse(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED, Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED_STR,
                    new ResponseRegisterResultModel(0,0,"当前路由器已经注册",30*24*3600));

        }

        if(null != routerItemModelJpaGerepository.saveAndFlush(new RouterItemModel(
                                                                        requestRegisterBodyModel.getRouterIp(),
                                                                        requestRegisterBodyModel.getRouterMac(),
                                                                        requestRegisterBodyModel.getModel(),
                                                                        requestRegisterBodyModel.getRomVersion())
        ))
        {

            return new SmartHomeResponse(Const.ResponseStatus.STATUS_OK, Const.ResponseStatus.STATUS_OK_STR,
                    new ResponseRegisterResultModel(0,0,"注册成功，一个月后再次注册",30*24*3600));
        }

        return new SmartHomeResponse(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED,
                Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED_STR,null);
    }

    @Override
    public RouterItemModel insertRouterItemModelJpaApi(RequestRegisterBodyModel requestRegisterBodyModel) {
        if(null == requestRegisterBodyModel){return null;}
        if(routerItemModelJpaGerepository.isExistsRouterModel(requestRegisterBodyModel.getRouterIp(),
                                                              requestRegisterBodyModel.getRouterMac(),
                                                              requestRegisterBodyModel.getModel(),
                                                              requestRegisterBodyModel.getRomVersion()).size() > 0){
            return null;
        }
        return routerItemModelJpaGerepository.saveAndFlush(new RouterItemModel(requestRegisterBodyModel.getRouterIp(),
                                                                                requestRegisterBodyModel.getRouterMac(),
                                                                                requestRegisterBodyModel.getModel(),
                                                                                requestRegisterBodyModel.getRomVersion()));
    }

    public RequestRegisterBodyModel verificationParameter4RequestRegisterBodyModel(RequestRegisterBodyModel requestRegisterBodyModel){

        if(null == requestRegisterBodyModel){return null;}
        if(((null == requestRegisterBodyModel.getModel()||(("".equals(requestRegisterBodyModel.getModel()))))) ||
                ((null == requestRegisterBodyModel.getRomVersion()||(("".equals(requestRegisterBodyModel.getRomVersion()))))) ||
                ((null == requestRegisterBodyModel.getRouterIp()||(("".equals(requestRegisterBodyModel.getRouterIp()))))) ||
                ((null == requestRegisterBodyModel.getRouterMac()||(("".equals(requestRegisterBodyModel.getRouterMac())))))
                )
        {return null;}

            return requestRegisterBodyModel;

    }

}
