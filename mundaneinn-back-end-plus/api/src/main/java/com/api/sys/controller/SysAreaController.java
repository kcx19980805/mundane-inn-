package com.api.sys.controller;

import com.api.sys.requestEntity.RequestCityListEntity;
import com.api.sys.service.SysAreaService;
import com.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = "全国地区")
@RequestMapping("/api")
public class SysAreaController {
    @Autowired
    private SysAreaService sysAreaService;

    @GetMapping("/sysAreaList")
    @ApiOperation(value = "城市列表")
    public Result sysAreaList(@Validated RequestCityListEntity req) {
        return Result.ok(sysAreaService.sysAreaList(req));
    }
}
