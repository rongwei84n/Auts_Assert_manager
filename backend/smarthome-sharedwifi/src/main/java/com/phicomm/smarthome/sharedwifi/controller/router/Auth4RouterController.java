package com.phicomm.smarthome.sharedwifi.controller.router;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.consts.Const;
import com.phicomm.smarthome.sharedwifi.model.h5web.GuestOrder;
import com.phicomm.smarthome.sharedwifi.model.router.RequestAuthBodyModel;
import com.phicomm.smarthome.sharedwifi.model.router.ResponseAuthResultModel;
import com.phicomm.smarthome.sharedwifi.service.AuthBeatResponseModelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;



/**
 * PROJECT_NAME: fhicomm.smarthome.model.sharedwifi
 * PACKAGE_NAME: com.fhicomm.sharedwifi.controller
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/8
 */

@RestController
@ComponentScan
@EnableSwagger2
public class Auth4RouterController {

    @Resource
    private AuthBeatResponseModelService authBeatResponseModelService;

    @ApiOperation(value = "路由器向后台发送授权心跳，获取路由器接入终端的授权结果", httpMethod ="POST",
                  notes = "Service->Router:路由器主动AuthBeats Service获取配置信息，路由器携带Model参数")
    @RequestMapping(value = "/router/auth", produces = { "application/json" })
    public SmartHomeResponse getAuthBeatResponseObject4Router(@RequestBody RequestAuthBodyModel requestAuthBodyModel)
    {
        return authBeatResponseModelService.executorBus4AuthRequestApi(requestAuthBodyModel);
    }

    @ApiOperation(value = "临时接口:_手动修正后台订单的支付状态(order_pay_status),默认修改为支付成功(1)，", httpMethod ="POST",
                  notes = "Service->Service:手动配置当前订单Order对象的订单支付状态，setOrderPayStatus(1)，使用时请保证Order对象各参完整")
    @RequestMapping(value = "TEMPORARY4SSP_inspectOrderStatusFromTencentWeChatServiceApi")
    public SmartHomeResponse TEMPORARY4SSP_inspectOrderStatusFromTencentWeChatServiceApi(@RequestBody GuestOrder order){

        return new SmartHomeResponse(Const.ResponseStatus.STATUS_OK, Const.ResponseStatus.STATUS_OK_STR,
                authBeatResponseModelService.TEMPORARY4SSP_inspectOrderStatusFromTencentWeChatServiceApi(order));
    }
}
