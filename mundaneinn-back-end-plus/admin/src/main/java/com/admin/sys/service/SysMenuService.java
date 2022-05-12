package com.admin.sys.service;

import com.admin.sys.entity.SysMenuEntity;
import com.admin.sys.responseEntity.ResponseMenuListEntity;
import com.admin.sys.responseEntity.ResponseMenuListEntity2;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 平台系统菜单管理
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:12
 */
public interface SysMenuService extends IService<SysMenuEntity> {


    /**
     * 查询管理员所有的菜单列表
     *
     * @param type 所属管理组(存sys_permission_group表id 默认0超级管理员)
     * @return
     */
    List<ResponseMenuListEntity> getAdminMenuList(String type);

    /**
     * 查询管理员所有的操作权限(增删改查)
     *
     * @param type 所属管理组(存sys_permission_group表id 默认0超级管理员)
     * @return
     */
    List<String> getPermsList(String type);

    /**
     * 平台端 新增/修改权限组时 查询所有菜单-按钮树形图
     *
     * @return
     */
    List<ResponseMenuListEntity2> getTreeMenuList();
}


