package com.admin.sys.dao;

import com.admin.sys.entity.ClientUserEntity;
import com.admin.sys.requestEntity.RequestClientUserListEntity;
import com.admin.sys.responseEntity.ResponseClientUserInfoEntity;
import com.admin.sys.responseEntity.ResponseClientUserListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户管理
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:12
 */
@Mapper
public interface ClientUserDao extends BaseMapper<ClientUserEntity> {

    /**
     * 用户管理-列表
     *
     * @param requestClientUserListEntity
     * @return
     */
    List<ResponseClientUserListEntity> userList(RequestClientUserListEntity requestClientUserListEntity);

    /**
     * 用户管理-总数
     *
     * @param requestClientUserListEntity
     * @return
     */
    Integer userListTotal(RequestClientUserListEntity requestClientUserListEntity);

    /**
     * 用户管理-单个信息
     * @param userId
     * @return
     */
    ResponseClientUserInfoEntity  userInfo(String userId);

}
