package com.api.sys.controller;

import com.api.sys.requestEntity.*;
import com.api.sys.service.ClientUserService;
import com.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;


@RestController
@Api(tags = "登录")
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private ClientUserService clientUserService;

    @PostMapping(value = "/sendSms")
    @ApiOperation(value = "发送短信")
    public Result sendSms(@Validated @RequestBody RequestSendSmsEntity req) {
        return Result.toRow(clientUserService.sendSms(req.getPhone()));
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "登录")
    public Result login(@Validated @RequestBody RequestLoginEntity req) {
        return Result.ok(clientUserService.login(req));
    }

    @PostMapping(value = "/logOut")
    @ApiOperation(value = "退出登录")
    public Result logOut(HttpServletRequest request) {
        return Result.toRow(clientUserService.logOut(request));
    }

    @PostMapping(value = "/sendEmailCode")
    @ApiOperation(value = "找回密码-传入邮箱地址发送验证码")
    public Result sendEmailCode(@Validated @RequestBody RequestSendEmailCodeEntity req){
        return Result.toRow(clientUserService.sendEmailCode(req.getEmail()));
    }

    @PostMapping(value = "/emailCodeIsCorrect")
    @ApiOperation(value = "找回密码-验证验证码是否正确")
    public Result emailCodeIsCorrect(@Validated @RequestBody RequestSendEmailCodeCorrectEntity req){
        return Result.toRow(clientUserService.emailCodeIsCorrect(req));
    }

    @PostMapping(value = "/updatePassword2")
    @ApiOperation(value = "找回密码-验证通过修改密码")
    public Result updatePassword2(@Validated @RequestBody RequestClientUserUpdatePassword2Entity req) {
        return Result.toRow(clientUserService.updatePassword2(req));
    }

}
