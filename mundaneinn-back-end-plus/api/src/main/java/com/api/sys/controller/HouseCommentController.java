package com.api.sys.controller;

import com.api.sys.requestEntity.RequestHouseCommentAddEntity;
import com.api.sys.requestEntity.RequestHouseCommentListEntity;
import com.api.sys.responseEntity.ResponseHouseCommentListEntity;
import com.api.sys.service.ClientHouseCommentService;
import com.common.utils.Result;
import com.common.utils.page.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@Api(tags = "评论")
@RequestMapping("/api")
public class HouseCommentController {

    @Autowired
    private ClientHouseCommentService clientHouseCommentService;

    @GetMapping("/houseCommentList")
    @ApiOperation(value = "评论列表")
    public Result<PageData<ResponseHouseCommentListEntity>> clientHouseCommentList(@Validated RequestHouseCommentListEntity req) {
        return Result.ok(clientHouseCommentService.clientHouseCommentList(req));
    }

    @PostMapping("/saveClientHouseComment")
    @ApiOperation(value = "新增评论")
    public Result<PageData<ResponseHouseCommentListEntity>> saveClientHouseComment(HttpServletRequest request,@Validated  @RequestBody RequestHouseCommentAddEntity req) {
        return Result.toRow(clientHouseCommentService.saveClientHouseComment(request,req));
    }
}
