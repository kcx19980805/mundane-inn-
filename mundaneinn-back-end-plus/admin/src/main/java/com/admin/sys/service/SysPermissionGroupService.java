package com.admin.sys.service;

import com.admin.sys.entity.SysPermissionGroupEntity;
import com.admin.sys.requestEntity.RequestPermissionGroupAddEntity;
import com.admin.sys.requestEntity.RequestPermissionGroupDeleteEntity;
import com.admin.sys.requestEntity.RequestPermissionGroupListEntity;
import com.admin.sys.requestEntity.RequestPermissionGroupUpdateEntity;
import com.admin.sys.responseEntity.ResponsePermissionGroupInfoEntity;
import com.admin.sys.responseEntity.ResponsePermissionGroupListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.page.PageData;

/**
 * 平台管理组信息表
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:13
 */
public interface SysPermissionGroupService extends IService<SysPermissionGroupEntity> {

    /**
     * 管理组-新增
     *
     * @param requestPermissionGroupAddEntity
     * @return
     */
    int savePermissionGroup(RequestPermissionGroupAddEntity requestPermissionGroupAddEntity);

    /**
     *  管理组-修改
     *
     * @param requestPermissionGroupUpdateEntity
     * @return
     */
    int updatePermissionGroup(RequestPermissionGroupUpdateEntity requestPermissionGroupUpdateEntity);

    /**
     *  管理组-真删除
     *
     * @param requestPermissionGroupDeleteEntity
     * @return
     */
    int deletePermissionGroup(RequestPermissionGroupDeleteEntity requestPermissionGroupDeleteEntity);


    /**
     * 管理组-列表-查询
     *
     * @param requestPermissionGroupListEntity
     * @return
     */
    PageData<ResponsePermissionGroupListEntity> permissionGroupList(RequestPermissionGroupListEntity requestPermissionGroupListEntity);


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
     * @param groupId   管理组id
     * @param groupName 管理组名
     * @return
     */
    int checkPermissionGroupNameUnique(String groupId, String groupName);

}


