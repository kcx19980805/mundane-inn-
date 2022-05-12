package com.api.sys.controller;

import com.api.sys.requestEntity.RequestHouseInfoEntity;
import com.api.sys.requestEntity.RequestHouseListEntity;
import com.api.sys.requestEntity.RequestHouseUpdateEntity;
import com.api.sys.responseEntity.ResponseHouseInfoEntity;
import com.api.sys.responseEntity.ResponseHouseListEntity;
import com.api.sys.service.ClientHouseService;
import com.common.constant.Constants;
import com.common.utils.Result;
import com.common.utils.page.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "房源")
@RequestMapping("/api")
public class HouseController {

    @Autowired
    private ClientHouseService clientHouseService;

    @GetMapping("/clientHouseList")
    @ApiOperation(value = "房源列表")
    public Result<PageData<ResponseHouseListEntity>> clientHouseList(HttpServletRequest request, @Validated RequestHouseListEntity req) {
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        req.setUserId(userId);
        return Result.ok(clientHouseService.clientHouseList(req));
    }

    @GetMapping("/clientHouseInfo")
    @ApiOperation(value = "房源单个信息")
    public Result clientHouseInfo(RequestHouseInfoEntity req) {
        return Result.ok(clientHouseService.clientHouseInfo(req));
    }

    @GetMapping("/landlordHouseList")
    @ApiOperation(value = "房东房源列表")
    public Result landlordHouseList(HttpServletRequest request, @Validated RequestHouseListEntity req) {
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        req.setUserId(userId);
        return Result.ok(clientHouseService.landlordHouseList(req));
    }

    @PostMapping("/saveClientHouse")
    @ApiOperation(value = "房源新增")
    public Result saveClientHouse(HttpServletRequest request, @Validated @RequestBody RequestHouseUpdateEntity req) {
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        req.setUserId(userId);
        return Result.ok(clientHouseService.saveClientHouse(req));
    }

    @PostMapping("/deleteClientHouseById")
    @ApiOperation(value = "房源删除")
    public Result deleteClientHouseById( @Validated @RequestBody RequestHouseInfoEntity req) {
        return Result.ok(clientHouseService.deleteClientHouseById(req));
    }

    @PostMapping("/updateClientHouse")
    @ApiOperation(value = "房源修改")
    public Result updateClientHouse( @Validated @RequestBody RequestHouseUpdateEntity req) {
        return Result.ok(clientHouseService.updateClientHouse(req));
    }
}
