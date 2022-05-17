package com.api.sys.controller;

import com.api.sys.requestEntity.RequestCollectUpdateEntity;
import com.api.sys.service.ClientUserCollectService;
import com.common.constant.Constants;
import com.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "用户收藏")
@RequestMapping("/api")
public class UserCollectController {

    @Autowired
    private ClientUserCollectService clientUserCollectService;

    @GetMapping("/userCollectList")
    @ApiOperation(value = "用户收藏列表")
    public Result clientUserCollectList(HttpServletRequest request) {
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        return Result.ok(clientUserCollectService.clientUserCollectList(userId));
    }

    @PostMapping("/saveUserCollect")
    @ApiOperation(value = "收藏房源")
    public Result saveClientUserCollect(HttpServletRequest request,@RequestBody @Validated RequestCollectUpdateEntity req) {
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        return Result.toRow(clientUserCollectService.saveClientUserCollect(userId,req.getHouseId()));
    }

    @PostMapping("/deleteUserCollect")
    @ApiOperation(value = "取消收藏")
    public Result deleteUserCollect(HttpServletRequest request,@RequestBody @Validated RequestCollectUpdateEntity req) {
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        return Result.toRow(clientUserCollectService.deleteClientUserCollectById(userId,req.getHouseId()));
    }

}
