package com.admin.sys.dao;

import com.admin.sys.entity.SysPermissionGroupEntity;
import com.admin.sys.requestEntity.RequestPermissionGroupListEntity;
import com.admin.sys.responseEntity.ResponsePermissionGroupInfoEntity;
import com.admin.sys.responseEntity.ResponsePermissionGroupListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 平台管理组信息表
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:13
 */
@Mapper
public interface SysPermissionGroupDao extends BaseMapper<SysPermissionGroupEntity> {


    /**
     * 管理组列表
     *
     * @param requestPermissionGroupListEntity
     * @return
     */
    List<ResponsePermissionGroupListEntity> permissionGroupList(RequestPermissionGroupListEntity requestPermissionGroupListEntity);

    /**
     * 管理组列表 总数
     *
     * @param requestPermissionGroupListEntity
     * @return
     */
    Integer permissionGroupListTotal(RequestPermissionGroupListEntity requestPermissionGroupListEntity);

    /**
     * 管理组信息-查询
     *
     * @param permissionGroupId 管理组id
     * @return
     */
    ResponsePermissionGroupInfoEntity permissionGroupInfo(String permissionGroupId);

    /**
     * 校验 管理组 组名是否重复
     *
     * @param permissionGroupId 管理组id
     * @param name              管理组名
     * @return
     */
    int checkPermissionGroupNameUnique(@Param("permissionGroupId") String permissionGroupId, @Param("name") String name);

    /**
     * 管理组与菜单关联数据-新增
     *
     * @param permissionGroupId 管理组id
     * @param menuIdList        菜单id 数组
     * @return
     */
    int savePermissionGroupMenu(@Param("permissionGroupId") String permissionGroupId, @Param("menuIdList") List<String> menuIdList);

    /**
     * 管理组-真删除
     *
     * @param permissionGroupId 管理组id 多个逗号隔开
     * @return
     */
    int deletePermissionGroup(String permissionGroupId);
}
