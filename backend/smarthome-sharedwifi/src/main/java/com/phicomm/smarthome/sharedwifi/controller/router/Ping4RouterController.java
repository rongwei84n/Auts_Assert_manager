package com.phicomm.smarthome.sharedwifi.controller.router;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.consts.Const;
import com.phicomm.smarthome.sharedwifi.model.router.RequestPingBeatsBodyModel;
import com.phicomm.smarthome.sharedwifi.model.router.ResponsePingResultModel;
import com.phicomm.smarthome.sharedwifi.model.router.RouterConfigModel;
import com.phicomm.smarthome.sharedwifi.model.router.RouterWhiteListModel;

import com.phicomm.smarthome.sharedwifi.service.RouterConfigModelJpaService;
import com.phicomm.smarthome.sharedwifi.service.PingBeatResponseInstrumentService;
import com.phicomm.smarthome.sharedwifi.service.RouterWhiteListModelJpaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.jpa
 * PACKAGE_NAME: com.fhicomm.sharedwifi.controller
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/6
 */
@RestController
@ComponentScan
@EnableSwagger2
public class Ping4RouterController {

    @Resource
    private RouterConfigModelJpaService routerConfigModelJpaService;

    @Resource
    private RouterWhiteListModelJpaService routerWhiteListModelJpaService;

    @Resource
    private PingBeatResponseInstrumentService pingBeatResponseInstrumentService;


    @ApiOperation(value = "路由器向打赏后台发送Ping心跳，返回配置信息及对应白名单", httpMethod ="POST",
            notes = "Service<->Router:路由器主动PingBeats Service获取配置信息,路由器携带ConfigPingBeatsModel对象")
    @RequestMapping(value = "/router/ping", produces = { "application/json" })
    public SmartHomeResponse getPingBeatResponseObject4Router(@RequestBody RequestPingBeatsBodyModel requestPingBeatsBodyModel)
    {
        ResponsePingResultModel responsePingResultModel = new ResponsePingResultModel();
        RouterConfigModel routerConfigModel = pingBeatResponseInstrumentService.verifyValidityRouterItemApi(requestPingBeatsBodyModel);

        responsePingResultModel.setRouterConfigModel(routerConfigModel);
        responsePingResultModel.setRouterWhiteListModelList(pingBeatResponseInstrumentService.getRouterWhiteListModelByConfigIdApi(routerConfigModel));

        return new SmartHomeResponse(Const.ResponseStatus.STATUS_OK, Const.ResponseStatus.STATUS_OK_STR,responsePingResultModel);
    }

    @ApiOperation(value = "SSP向后台新增路由器Ping心跳请求后的响应结果-Item方式", httpMethod ="POST",
            notes = "Service<->Service:Item方式配置服务后台ConfigPingBeatsModel对象")
    @RequestMapping(value = "setConfigPingResultModelItem4SSP")
    public SmartHomeResponse setConfigPingResultModel4SSP(
            @ApiParam(value = "路由器ID", required = true) @RequestParam long routerId,
            @ApiParam(value = "配置信息版本号", required = true) @RequestParam(defaultValue = "sw1.2") String configVersion,
            @ApiParam(value = "打赏功能开关", required = true) @RequestParam(defaultValue = "true") int isForbidden,
            @ApiParam(value = "Protal页URL", required = true) @RequestParam String loginUrl)
    {
        if(routerConfigModelJpaService.isExistsRouterConfigModelJpaApi(routerId, configVersion, isForbidden, loginUrl).size()>0){
            return new SmartHomeResponse(Const.ResponseStatus.STATUS_COMMON_NULL, Const.ResponseStatus.STATUS_COMMON_NULL_STR,null);
        }

        return new SmartHomeResponse(Const.ResponseStatus.STATUS_OK, Const.ResponseStatus.STATUS_OK_STR,
                routerConfigModelJpaService.setPingBeatResultJpaApi(routerId, configVersion, isForbidden, loginUrl));
    }

    @ApiOperation(value = "SSP向后台新增路由器Ping心跳请求后的响应结果-ObjBody方式", httpMethod ="POST",
            notes = "Service->Service:配置服务后台ConfigPingBeatsModel对象")
    @RequestMapping(value = "setConfigPingResultModel4SSP")
    public SmartHomeResponse setConfigPingResultModel4SSP(@RequestBody RouterConfigModel routerConfigModel){

        /** Request body verification */
        if(routerConfigModelJpaService.isExistsRouterConfigModelJpaApi(routerConfigModel.getRouterId(),
                                                                        routerConfigModel.getConfigVersion(),
                                                                        routerConfigModel.getIsForbidden(),
                                                                        routerConfigModel.getPortalUrl()).size()>0){
            return new SmartHomeResponse(Const.ResponseStatus.STATUS_COMMON_NULL, Const.ResponseStatus.STATUS_COMMON_NULL_STR,null);
        }

        return new SmartHomeResponse(Const.ResponseStatus.STATUS_OK, Const.ResponseStatus.STATUS_OK_STR,
                routerConfigModelJpaService.setPingBeatResultJpaApi(routerConfigModel));
    }

    @ApiOperation(value = "SSP向后台删除路由器Ping心跳请求后的响应结果-ObjBody方式", httpMethod ="POST",
            notes = "Service->Service:删除服务后台ConfigPingBeatsModel对象")
    @RequestMapping(value = "deletePingBeatResult4SSP")
    public SmartHomeResponse deletePingBeatResult4SSP(@RequestBody RouterConfigModel routerConfigModel){
        return new SmartHomeResponse(Const.ResponseStatus.STATUS_OK, Const.ResponseStatus.STATUS_OK_STR,
                routerConfigModelJpaService.deletePingBeatResultJpaApi(routerConfigModel));
    }

    @ApiOperation(value = "SSP向后台新增路由器白名单结果集合-ObjBody方式", httpMethod ="POST",
            notes = "Service->Service:设置服务后台WhiteListModel对象")
    @RequestMapping(value = "setWhiteListModel4SSP")
    public SmartHomeResponse setWhiteListModel4SSP(@RequestBody RouterWhiteListModel routerWhiteListModel){

        /** Request body verification */
        if(routerWhiteListModelJpaService.isExistsWhiteListModelJpaApi(routerWhiteListModel).size() > 0){
            return new SmartHomeResponse(Const.ResponseStatus.STATUS_COMMON_NULL
                    , Const.ResponseStatus.STATUS_COMMON_NULL_STR,
                    null);
        }

        return new SmartHomeResponse(Const.ResponseStatus.STATUS_OK, Const.ResponseStatus.STATUS_OK_STR,
                routerWhiteListModelJpaService.setWhiteListModelJpaApi(routerWhiteListModel));
    }

    @ApiOperation(value = "SSP向后台删除路由器白名单结果集合-ObjBody方式", httpMethod ="POST",
            notes = "Service->Service:删除服务后台WhiteListModel对象")
    @RequestMapping(value = "deleteWhiteListModel4SSP")
    public SmartHomeResponse deleteWhiteListModel4SSP(RouterWhiteListModel routerWhiteListModel){
        return new SmartHomeResponse(Const.ResponseStatus.STATUS_OK, Const.ResponseStatus.STATUS_OK_STR,
                routerWhiteListModelJpaService.deleteWhiteListModelJpaApi(routerWhiteListModel));
    }

    @ApiOperation(value = "SSP通过主键ID向后台获取路由器白名单结果集合-Item方式", httpMethod ="POST",
            notes = "Service->Service:获取服务后台Whit" +
                    "eListModel对象")
    @RequestMapping(value = "getWhiteListModelById4SSP")
    public SmartHomeResponse getWhiteListModelById4SSP(long id){

        return new SmartHomeResponse(Const.ResponseStatus.STATUS_OK, Const.ResponseStatus.STATUS_OK_STR,
                routerWhiteListModelJpaService.getWhiteListModelByIdJpaApi(id));
    }

    @ApiOperation(value = "SSP通过主键ID向后台查询路由器白名单结果集合-Item方式", httpMethod ="POST",
            notes = "Service->Service:获取服务后台WhiteListModel对象")
    @RequestMapping(value = "getWhiteListModelByCreateTime4SSP")
    public SmartHomeResponse getWhiteListModelByCreateTime4SSP(long createTime){

        return new SmartHomeResponse(Const.ResponseStatus.STATUS_OK, Const.ResponseStatus.STATUS_OK_STR,
                routerWhiteListModelJpaService.getWhiteListModelByCreateTimeJpaApi(createTime));
    }

    @ApiOperation(value = "SSP通过Model向后台查询路由器白名单结果集合-Item方式", httpMethod ="POST",
            notes = "Service->Service:获取服务后台WhiteListModel对象")
    @RequestMapping(value = "getWhiteListModelByHost4SSP")
    public SmartHomeResponse getWhiteListModelByHost4SSP(String host){

        return new SmartHomeResponse(Const.ResponseStatus.STATUS_OK, Const.ResponseStatus.STATUS_OK_STR,
                routerWhiteListModelJpaService.getWhiteListModelByHostJpaApi(host));
    }

    @ApiOperation(value = "SSP通过protocol向后台查询路由器白名单结果集合-Item方式", httpMethod ="POST",
            notes = "Service->Service:获取服务后台WhiteListModel对象")
    @RequestMapping(value = "getWhiteListModelByProtocol4SSP")
    public SmartHomeResponse getWhiteListModelByProtocol4SSP(String protocol){

        return new SmartHomeResponse(Const.ResponseStatus.STATUS_OK, Const.ResponseStatus.STATUS_OK_STR,
                routerWhiteListModelJpaService.getWhiteListModelByProtocolJpaApi(protocol));
    }

    @ApiOperation(value = "SSP通过updaterTime向后台查询路由器白名单结果集合-Item方式", httpMethod ="POST",
            notes = "Service->Service:获取服务后台WhiteListModel对象")
    @RequestMapping(value = "getWhiteListModelByUpdaterTime4SSP")
    public SmartHomeResponse getWhiteListModelByUpdaterTime4SSP(long updaterTime){

        return new SmartHomeResponse(Const.ResponseStatus.STATUS_OK, Const.ResponseStatus.STATUS_OK_STR,
                routerWhiteListModelJpaService.getWhiteListModelByUpdaterTimeJpaApi(updaterTime));
    }

    @ApiOperation(value = "SSP通过configId向后台查询路由器白名单结果集合-Item方式", httpMethod ="POST",
            notes = "Service->Service:获取服务后台WhiteListModel对象")
    @RequestMapping(value = "getWhiteListModelByConfigId4SSP")
    public SmartHomeResponse getWhiteListModelByConfigId4SSP(long configId){

        return new SmartHomeResponse(Const.ResponseStatus.STATUS_OK, Const.ResponseStatus.STATUS_OK_STR,
                routerWhiteListModelJpaService.getWhiteListModelByConfigIdJpaApi(configId));
    }
}
