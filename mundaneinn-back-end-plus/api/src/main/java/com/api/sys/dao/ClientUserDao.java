package com.api.sys.dao;

import com.api.sys.responseEntity.ResponseClientUserInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.api.sys.entity.ClientUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * 用户管理-单个信息
     * @param userId
     * @return
     */
    ResponseClientUserInfoEntity userInfo(String userId);

}
