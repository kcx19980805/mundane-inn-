package com.api.sys.controller;

import com.api.sys.requestEntity.RequestUserAmountRecordDeleteEntity;
import com.api.sys.requestEntity.RequestUserAmountRecordListEntity;
import com.api.sys.responseEntity.ResponseUserAmountRecordListEntity;
import com.api.sys.service.ClientUserAmountRecordService;
import com.common.constant.Constants;
import com.common.utils.Result;
import com.common.utils.page.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "余额变更记录")
@RequestMapping("/api")
@Slf4j
public class ClientUserAmountRecordController {

    @Autowired
    private ClientUserAmountRecordService clientUserAmountRecordService;

    @GetMapping("/clientUserAmountRecordList")
    @ApiOperation(value = "用户余额变更记录列表")
    public Result<PageData<ResponseUserAmountRecordListEntity>> clientUserAmountRecordList(HttpServletRequest request, @Validated RequestUserAmountRecordListEntity req){
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        req.setUserId(userId);
        return Result.ok(clientUserAmountRecordService.clientUserAmountRecordList(req));
    }

    @PostMapping("/deleteAmountRecord")
    @ApiOperation(value = "用户余额变更记录-删除")
    public Result deleteAmountRecord(@Validated @RequestBody RequestUserAmountRecordDeleteEntity req){
        return Result.toRow(clientUserAmountRecordService.deleteAmountRecord(req));
    }

}
