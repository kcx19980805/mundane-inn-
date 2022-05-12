package com.admin.sys.service;

import com.admin.sys.entity.SysUserEntity;
import com.admin.sys.requestEntity.*;
import com.admin.sys.responseEntity.ResponseUserInfoEntity;
import com.admin.sys.responseEntity.ResponseUserListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.page.PageData;

/**
 * 账号管理(平台)
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:12
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 账号管理-新增
     *
     * @param requestUserAddEntity
     * @return
     */
    int saveUser(RequestUserAddEntity requestUserAddEntity);

    /**
     * 账号管理-修改
     *
     * @param requestUserUpdateEntity
     * @return
     */
    int updateUser(RequestUserUpdateEntity requestUserUpdateEntity);

    /**
     * 账号管理-软删除
     *
     * @param requestUserDeleteEntity
     * @return
     */
    int deleteUnRealUser(RequestUserDeleteEntity requestUserDeleteEntity);

    /**
     * 账号管理-列表-查询
     *
     * @param requestUserListEntity
     * @return
     */
    PageData<ResponseUserListEntity> userList(RequestUserListEntity requestUserListEntity);

    /**
     * 账号信息-查询
     *
     * @param userId 账号id
     * @return
     */
    ResponseUserInfoEntity userInfo(String userId);

    /**
     * 修改密码
     *
     * @param requestUserUpdatePasswordEntity
     * @return
     */
    int updatePassword(RequestUserUpdatePasswordEntity requestUserUpdatePasswordEntity);

    /**
     * 校验 账号是否重复
     *
     * @param userId  账号id
     * @param account 账号
     * @return
     */
    int checkAccountUnique(String userId, String account);

    /**
     * 账号管理-禁用
     *
     * @param requestUserDeleteEntity
     * @return
     */
    int disableUser(RequestUserDeleteEntity requestUserDeleteEntity);

    /**
     * 账号管理-启用
     *
     * @param requestUserDeleteEntity
     * @return
     */
    int restoreUser(RequestUserDeleteEntity requestUserDeleteEntity);

    /**
     * 平台登录
     *
     * @param account 账号
     * @return
     */
    SysUserEntity login(String account);
}

