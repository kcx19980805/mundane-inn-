package com.admin.sys.controller;

import com.admin.sys.requestEntity.RequestPermissionGroupAddEntity;
import com.admin.sys.requestEntity.RequestPermissionGroupDeleteEntity;
import com.admin.sys.requestEntity.RequestPermissionGroupListEntity;
import com.admin.sys.requestEntity.RequestPermissionGroupUpdateEntity;
import com.admin.sys.responseEntity.ResponsePermissionGroupListEntity;
import com.admin.sys.service.SysPermissionGroupService;
import com.common.utils.Result;
import com.common.utils.page.PageData;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 平台管理组信息表
 */
@RestController
@RequestMapping("sys/permissionGroup")
public class SysPermissionGroupController {

    @Autowired
    private SysPermissionGroupService sysPermissionGroupService;

    /**
     * 管理组-新增
     */
    @Transactional
    @PostMapping("/save")
    @RequiresPermissions("sys:permissiongroup:save")
    public Result savePermissionGroup(@Validated @RequestBody RequestPermissionGroupAddEntity requestPermissionGroupAddEntity) {
        return Result.toRow(sysPermissionGroupService.savePermissionGroup(requestPermissionGroupAddEntity));
    }

    /**
     * 管理组-真删除
     */
    @Transactional
    @PostMapping("/delete")
    @RequiresPermissions("sys:permissiongroup:delete")
    public Result deletePermissionGroup(@Validated @RequestBody RequestPermissionGroupDeleteEntity requestPermissionGroupDeleteEntity) {
        return Result.toRow(sysPermissionGroupService.deletePermissionGroup(requestPermissionGroupDeleteEntity));
    }

    /**
     * 管理组-修改
     */
    @Transactional
    @PostMapping("/update")
    @RequiresPermissions("sys:permissiongroup:update")
    public Result updatePermissionGroup(@Validated @RequestBody RequestPermissionGroupUpdateEntity requestPermissionGroupUpdateEntity) {
        return Result.toRow(sysPermissionGroupService.updatePermissionGroup(requestPermissionGroupUpdateEntity));
    }

    /**
     * 管理组-列表-查询
     * @param requestPermissionGroupListEntity
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions(value = "sys:permissiongroup:list")
    public Result<PageData<ResponsePermissionGroupListEntity>> permissionGroupList(@Validated RequestPermissionGroupListEntity requestPermissionGroupListEntity) {
        return Result.ok(sysPermissionGroupService.permissionGroupList(requestPermissionGroupListEntity));
    }

    /**
     * 管理组与菜单-信息查询(单个)
     */
    @GetMapping(value = "/info/{permissionGroupId}")
    @RequiresPermissions("sys:permissiongroup:info")
    public Result groupInfo(@PathVariable String permissionGroupId) {
        if (StringUtils.isBlank(permissionGroupId)) {
            return Result.error("管理组id不能为空");
        }
        return Result.ok(sysPermissionGroupService.permissionGroupInfo(permissionGroupId));
    }


}
