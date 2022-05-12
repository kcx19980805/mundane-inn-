package com.admin.sys.dao;

import com.admin.sys.entity.SysUserEntity;
import com.admin.sys.requestEntity.RequestUserListEntity;
import com.admin.sys.responseEntity.ResponseUserInfoEntity;
import com.admin.sys.responseEntity.ResponseUserListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 账号管理(平台)
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:12
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    /**
     * 账号管理-列表-查询
     *
     * @param requestUserListEntity
     * @return
     */
    List<ResponseUserListEntity> userList(RequestUserListEntity requestUserListEntity);

    /**
     * 账号管理-列表-查询 总数
     *
     * @param requestUserListEntity
     * @return
     */
    Integer userListTotal(RequestUserListEntity requestUserListEntity);

    /**
     * 平台端 查询账号信息
     *
     * @param userId 账号id
     * @return
     */
    ResponseUserInfoEntity userInfo(String userId);

    /**
     * 校验 账号是否重复
     *
     * @param userId  账号id
     * @param account 账号
     * @return
     */
    int checkAccountUnique(@Param("userId") String userId, @Param("account") String account);


    /**
     * 平台登录
     *
     * @param account 账号
     * @return
     */
    SysUserEntity login(String account);
}

