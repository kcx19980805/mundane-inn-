package com.api.sys.service;


import com.api.sys.entity.ClientUserTokenEntity;
import com.api.sys.requestEntity.RequestUserTokenUpdateEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * APP用户token
 *
 * @author
 * @email
 * @date 2021-01-25 15:33:39
 */
public interface ClientUserTokenService extends IService<ClientUserTokenEntity> {

    /**
     * 更新用户token
     *
     * @param userTokenUpdateEntityList
     * @return
     */
    int updateUserToken(List<RequestUserTokenUpdateEntity> userTokenUpdateEntityList);

}

