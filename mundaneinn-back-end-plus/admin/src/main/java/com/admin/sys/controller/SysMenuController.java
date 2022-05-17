package com.admin.sys.controller;

import com.admin.sys.service.SysMenuService;
import com.common.utils.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台系统菜单管理
 */
@RestController
@RequestMapping("sys/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 平台端-菜单权限-查询
     * @return
     */
    @GetMapping("/treeMenuList")
    @RequiresPermissions(value = "sys:menu:list")
    public Result treeMenuList() {
        return Result.ok(sysMenuService.getTreeMenuList());
    }


}
