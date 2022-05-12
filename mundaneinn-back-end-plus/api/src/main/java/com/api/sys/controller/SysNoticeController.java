package com.api.sys.controller;

import com.api.sys.requestEntity.RequestNoticeDeleteEntity;
import com.api.sys.service.SysNoticeService;
import com.common.constant.Constants;
import com.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "系统通知")
@RequestMapping("/api")
public class SysNoticeController {

    @Autowired
    private SysNoticeService sysNoticeService;

    @GetMapping("/sysNoticeList")
    @ApiOperation(value = "系统通列表")
    public Result sysNoticeList(HttpServletRequest request) {
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        return Result.ok(sysNoticeService.sysNoticeList(userId));
    }

    @PostMapping("/deleteSysNoticeById")
    @ApiOperation(value = "删除通知")
    public Result deleteSysNoticeById(@Validated @RequestBody RequestNoticeDeleteEntity req){
        return Result.ok(sysNoticeService.deleteSysNoticeById(req));
    }

    @PostMapping("/sysNoticeIsRead")
    @ApiOperation(value = "通知已读")
    public Result sysNoticeIsRead(@Validated @RequestBody RequestNoticeDeleteEntity req) {
        return Result.ok(sysNoticeService.sysNoticeIsRead(req));
    }
}
