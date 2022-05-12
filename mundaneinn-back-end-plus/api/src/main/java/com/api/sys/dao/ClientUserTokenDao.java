package com.api.sys.dao;

import com.api.sys.entity.ClientUserTokenEntity;
import com.api.sys.requestEntity.RequestUserTokenUpdateEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户token
 *
 * @author
 * @email
 * @date 2021-01-25 15:33:39
 */
@Mapper
public interface ClientUserTokenDao extends BaseMapper<ClientUserTokenEntity> {

    /**
     * 删除用户token
     *
     * @param userTokenUpdateEntityList
     * @return
     */
    int deleteUserToken(List<RequestUserTokenUpdateEntity> userTokenUpdateEntityList);


    /**
     * 新增用户token
     *
     * @param userTokenUpdateEntityList
     * @return
     */
    int SaveUserToken(List<RequestUserTokenUpdateEntity> userTokenUpdateEntityList);
}
