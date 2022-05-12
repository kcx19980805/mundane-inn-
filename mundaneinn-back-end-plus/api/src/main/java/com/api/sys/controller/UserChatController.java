package com.api.sys.controller;

import com.api.sys.requestEntity.RequestUserChatListEntity;
import com.api.sys.service.ClientUserChatService;
import com.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author kcx
 * @description:
 * @date 2022/5/4 18:48
 */
@RestController
@Api(tags = "用户聊天")
@RequestMapping("/api")
public class UserChatController {

    @Autowired
    private ClientUserChatService clientUserChatService;

    @GetMapping("/clientUserChatList")
    @ApiOperation(value = "查询2个用户之间的聊天记录")
    public Result clientUserChatList(@Validated RequestUserChatListEntity req) {
        return Result.ok(clientUserChatService.clientUserChatList(req));
    }

    @GetMapping("/unReadTotal")
    @ApiOperation(value = "用户聊天记录未读总数")
    public Result unReadTotal(@Validated RequestUserChatListEntity req) {
        return Result.ok(clientUserChatService.unReadTotal(req));
    }

    @GetMapping("/userList")
    @ApiOperation(value = "查询与当前用户存在聊天记录的所有用户和最后一条消息")
    public Result unReadMessageList(@Validated RequestUserChatListEntity req) {
        return Result.ok(clientUserChatService.userList(req));
    }

    @PostMapping("/deleteClientUserChat")
    @ApiOperation(value = "删除聊天记录")
    public Result deleteClientUserChatById(@RequestBody @Validated RequestUserChatListEntity req) {
        return Result.toRow(clientUserChatService.deleteClientUserChat(req));
    }

    @PostMapping("/isReadChat")
    @ApiOperation(value = "已读聊天记录")
    public Result isReadChat(@RequestBody @Validated RequestUserChatListEntity  req) {
        return Result.toRow(clientUserChatService.isReadChat(req));
    }
}
