package com.api.sys.controller;

import com.api.sys.service.ClientTypeMatchService;
import com.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "房源配套类型")
@RequestMapping("/api")
public class TypeMatchController {
    @Autowired
    private ClientTypeMatchService clientTypeMatchService;

    @GetMapping("/clientTypeMatchList")
    @ApiOperation(value = "房源配套类型列表")
    public Result clientTypeMatchList() {
        return Result.ok(clientTypeMatchService.clientTypeMatchList());
    }

}
