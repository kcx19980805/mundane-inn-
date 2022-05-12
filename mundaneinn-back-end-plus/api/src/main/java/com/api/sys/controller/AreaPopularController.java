package com.api.sys.controller;

import com.api.sys.requestEntity.RequestAreaPopularListEntity;
import com.api.sys.service.ClientAreaPopularService;
import com.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "用户常用地区")
@RequestMapping("/api")
public class AreaPopularController {

    @Autowired
    private ClientAreaPopularService clientAreaPopularService;

    @GetMapping("/clientAreaPopularList")
    @ApiOperation(value = "用户常用地区列表")
    public Result sysAreaList(@Validated RequestAreaPopularListEntity req) {
        return Result.ok(clientAreaPopularService.clientAreaPopularList(req));
    }
}
