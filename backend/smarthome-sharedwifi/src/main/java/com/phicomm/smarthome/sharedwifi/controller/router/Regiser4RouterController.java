package com.phicomm.smarthome.sharedwifi.controller.router;

import com.phicomm.smarthome.sharedwifi.consts.Const;
import com.phicomm.smarthome.sharedwifi.model.router.RequestRegisterBodyModel;
import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.sharedwifi.service.RegisterResultModelService;
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
public class Regiser4RouterController {

    @Resource
    private RegisterResultModelService registerResultModelService;

    @ApiOperation(value = "路由器向后台认证接口", httpMethod ="POST",
            notes = "Router<->Service:路由器携参向Service认证，路由器携带RequestRegisterBodyModel参数")
    @RequestMapping(value = "/router/register", produces = { "application/json" })
    public SmartHomeResponse getRegisterResponseObject4Router(@RequestBody RequestRegisterBodyModel requestRegisterBodyModel)
    {
        return registerResultModelService.getRegisterResultModelApi(requestRegisterBodyModel);
    }

}
