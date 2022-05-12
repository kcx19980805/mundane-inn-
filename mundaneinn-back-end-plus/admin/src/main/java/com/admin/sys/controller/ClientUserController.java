package com.admin.sys.controller;

import com.admin.sys.requestEntity.RequestClientUserAddEntity;
import com.admin.sys.requestEntity.RequestClientUserDeleteEntity;
import com.admin.sys.requestEntity.RequestClientUserListEntity;
import com.admin.sys.requestEntity.RequestClientUserUpdateEntity;
import com.admin.sys.responseEntity.ResponseClientUserListEntity;
import com.admin.sys.service.ClientUserService;
import com.common.utils.Result;
import com.common.utils.page.PageData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * 用户管理
 * @author Administrator
 */
@RestController
@RequestMapping("sys/clientUser")
public class ClientUserController {
    @Autowired
    private ClientUserService clientUserService;
    /**
     * 用户管理-列表-查询
     */
    @GetMapping("/list")
    @RequiresPermissions(value = "sys:clientuser:list")
    public Result<PageData<ResponseClientUserListEntity>> userList(@Validated RequestClientUserListEntity requestClientUserListEntity) {
        return Result.ok(clientUserService.userList(requestClientUserListEntity));
    }

    /**
     * 用户管理-单个信息
     * @param userId
     * @return
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions(value = "sys:clientuser:info")
    public Result userInfo(@PathVariable String userId){
        return  Result.ok(clientUserService.userInfo(userId));
    }

    /**
     * 用户管理-修改
     * @param requestClientUserUpdateEntity
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions(value = "sys:clientuser:update")
    public Result updateUser(@Validated @RequestBody RequestClientUserUpdateEntity requestClientUserUpdateEntity){
        return  Result.toRow(clientUserService.updateUser(requestClientUserUpdateEntity));
    }

    /**
     * 用户管理-添加
     * @param requestClientUserAddEntity
     * @return
     */
    @PostMapping("/add")
    @RequiresPermissions(value = "sys:clientuser:save")
    public Result addUser(@Validated @RequestBody RequestClientUserAddEntity requestClientUserAddEntity){
        return  Result.toRow(clientUserService.addUser(requestClientUserAddEntity));
    }

    /**
     * 用户管理-假删除
     * @param requestClientUserDeleteEntity
     * @return
     */
    @PostMapping("/delete")
    @RequiresPermissions(value = "sys:clientuser:delete")
    public Result deleteUser(@Validated @RequestBody RequestClientUserDeleteEntity requestClientUserDeleteEntity){
        return Result.toRow(clientUserService.deleteUser(requestClientUserDeleteEntity));
    }

    /**
     * 用户管理-禁用
     */
    @PostMapping("/disable/{userId}")
    @RequiresPermissions("sys:clientuser:disable")
    public Result disableUser(@PathVariable String userId) {
        return Result.toRow(clientUserService.disableUser(userId));
    }

    /**
     * 用户管理-启用
     */
    @PostMapping("/restore/{userId}")
    @RequiresPermissions("sys:clientuser:restore")
    public Result restoreUser(@PathVariable String userId) {
        return Result.toRow(clientUserService.restoreUser(userId));
    }

    /**
     * 用户管理-导入
     * @param file
     * @return
     */
    @Transactional
    @PostMapping("/import")
    @RequiresPermissions("sys:clientuser:save")
    public Result  saveClientUserList(@RequestBody MultipartFile file) {
        return Result.toRow(clientUserService.saveClientUserList(file));
    }
}
