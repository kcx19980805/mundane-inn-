package com.admin.sys.dao;

import com.admin.sys.entity.SysMenuEntity;
import com.admin.sys.responseEntity.ResponseMenuListEntity;
import com.admin.sys.responseEntity.ResponseMenuListEntity2;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限
 * @author
 * @email
 * @date 2021-03-08 14:12:52
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

    /**
     * 查询管理员所有的菜单列表
     *
     * @param type 所属管理组(存sys_permission_group表id 默认0超级管理员)
     * @return
     */
    List<ResponseMenuListEntity> getAdminMenuList(@Param("type") String type);


    /**
     * 查询管理员所有的操作权限(增删改查)
     *
     * @param type 所属管理组(存sys_permission_group表id 默认0超级管理员)
     * @return
     */
    List<String> getPermsList(@Param("type") String type);


    /**
     * 平台端 查询所有菜单-按钮
     *
     * @return
     */
    List<ResponseMenuListEntity2> getTreeMenuList();


}
