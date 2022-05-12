package com.api.sys.controller;

import com.api.sys.service.ClientAreaTypeService;
import com.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "地区分类")
@RequestMapping("/api")
public class AreaTypeController {

    @Autowired
    private ClientAreaTypeService clientAreaTypeService;

    @GetMapping("/clientAreaTypeList")
    @ApiOperation(value = "地区分类列表")
    public Result sysAreaList() {
        return Result.ok(clientAreaTypeService.clientAreaTypeList());
    }
}
