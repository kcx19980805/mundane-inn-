package com.api.sys.controller;


import com.api.sys.requestEntity.RequestClientUserInfoEntity;
import com.api.sys.requestEntity.RequestClientUserUpdatePassword1Entity;
import com.api.sys.responseEntity.ResponseClientUserInfoEntity;
import com.api.sys.service.ClientUserService;
import com.api.sys.requestEntity.RequestClientUserUpdateEntity;
import com.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

@RestController
@Api(tags = "我的信息")
@RequestMapping("/api")
public class ClientUserController {
    @Autowired
    private ClientUserService clientUserService;

    @GetMapping("/getUserInfo")
    @ApiOperation(value = "当前用户信息")
    public Result<ResponseClientUserInfoEntity> getUserInfo(HttpServletRequest request) {
        return Result.ok(clientUserService.getUserInfo(request));
    }

    @GetMapping("/getOtherUserInfo")
    @ApiOperation(value = "指定用户信息")
    public Result<ResponseClientUserInfoEntity> getOtherUserInfo(@Validated RequestClientUserInfoEntity req) {
        return Result.ok(clientUserService.getOtherUserInfo(req));
    }

    @PostMapping("/updateUserInfo")
    @ApiOperation(value = "修改用户信息")
    public Result updateUserInfo(HttpServletRequest request, @Validated @RequestBody RequestClientUserUpdateEntity req) {
        return Result.toRow(clientUserService.updateUserInfo(request,req));
    }

    @PostMapping("/updatePassword")
    @ApiOperation(value = "修改密码")
    public Result updatePassword(HttpServletRequest request,@Validated @RequestBody RequestClientUserUpdatePassword1Entity req) {
        return Result.toRow(clientUserService.updatePassword(request,req));
    }
}
