package com.api.sys.controller;

import com.api.sys.service.ClientTypeResidenceService;
import com.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "房源房型类型")
@RequestMapping("/api")
public class TypeResidenceController {
    @Autowired
    private ClientTypeResidenceService clientTypeResidenceService;

    @GetMapping("/clientTypeResidenceList")
    @ApiOperation(value = "房源房型类型列表")
    public Result clientTypeResidenceList() {
        return Result.ok(clientTypeResidenceService.clientTypeResidenceList());
    }

}
