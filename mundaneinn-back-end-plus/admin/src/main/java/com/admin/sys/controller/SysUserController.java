package com.admin.sys.controller;

import com.admin.config.shiro.ShiroUtils;
import com.admin.sys.requestEntity.*;
import com.admin.sys.responseEntity.ResponseUserListEntity;
import com.admin.sys.service.SysUserService;
import com.common.utils.Result;
import com.common.utils.page.PageData;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 账号管理
 */
@RestController
@RequestMapping("sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    /**
     * 账号管理-新增
     */
    @Transactional
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    public Result saveUser(@Validated @RequestBody RequestUserAddEntity requestUserAddEntity) {
        return Result.toRow(sysUserService.saveUser(requestUserAddEntity));
    }

    /**
     * 账号管理-软删除
     */
    @Transactional
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public Result deleteUnRealUser(@Validated @RequestBody RequestUserDeleteEntity requestUserDeleteEntity) {
        return Result.toRow(sysUserService.deleteUnRealUser(requestUserDeleteEntity));
    }

    /**
     * 账号管理-修改
     */
    @Transactional
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public Result updateUser(@Validated @RequestBody RequestUserUpdateEntity requestUserUpdateEntity) {
        return Result.toRow(sysUserService.updateUser(requestUserUpdateEntity));
    }

    /**
     * 修改密码
     */
    @Transactional
    @PostMapping("/updatePassword")
    @RequiresPermissions("sys:user:updatePassword")
    public Result updatePassword(@Validated @RequestBody RequestUserUpdatePasswordEntity requestUserUpdatePasswordEntity) {
        //如果为空则是修改当前账号的密码
        if("".equals(requestUserUpdatePasswordEntity.getUserId()) || requestUserUpdatePasswordEntity.getUserId()==null){
            requestUserUpdatePasswordEntity.setUserId(ShiroUtils.getUserId().toString());
        }
        return Result.toRow(sysUserService.updatePassword(requestUserUpdatePasswordEntity));
    }


    /**
     * 账号管理-列表-查询
     */
    @GetMapping("/list")
    @RequiresPermissions(value = "sys:user:list")
    public Result<PageData<ResponseUserListEntity>> userList(@Validated RequestUserListEntity requestUserListEntity) {
        return Result.ok(sysUserService.userList(requestUserListEntity));
    }


    /**
     * 账号管理-查询信息
     */
    @GetMapping(value = "/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public Result userInfo(@PathVariable String userId) {
        if (StringUtils.isBlank(userId)) {
            return Result.error("账号id不能为空");
        }
        return Result.ok(sysUserService.userInfo(userId));
    }

    /**
     * 账号管理-禁用
     */
    @Transactional
    @PostMapping("/disable")
    @RequiresPermissions("sys:user:disable")
    public Result disableUser(@Validated @RequestBody RequestUserDeleteEntity requestUserDeleteEntity) {
        return Result.toRow(sysUserService.disableUser(requestUserDeleteEntity));
    }

    /**
     * 账号管理-启用
     */
    @Transactional
    @PostMapping("/restore")
    @RequiresPermissions("sys:user:restore")
    public Result restoreUser(@Validated @RequestBody RequestUserDeleteEntity requestUserDeleteEntity) {
        return Result.toRow(sysUserService.restoreUser(requestUserDeleteEntity));
    }

}
