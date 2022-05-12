package com.api.sys.service.impl;

import com.api.sys.dao.ClientUserTokenDao;
import com.api.sys.entity.ClientUserTokenEntity;
import com.api.sys.requestEntity.RequestUserTokenUpdateEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.api.sys.service.ClientUserTokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clientUserTokenService")
public class ClientUserTokenServiceImpl extends ServiceImpl<ClientUserTokenDao, ClientUserTokenEntity> implements ClientUserTokenService {

    /**
     * 更新用户token
     *
     * @param userTokenUpdateEntityList
     * @return
     */
    @Override
    public int updateUserToken(List<RequestUserTokenUpdateEntity> userTokenUpdateEntityList) {
        if (userTokenUpdateEntityList != null && userTokenUpdateEntityList.size() > 0) {
            //删除 用户token
            baseMapper.deleteUserToken(userTokenUpdateEntityList);
            //新增 用户token
            return baseMapper.SaveUserToken(userTokenUpdateEntityList);
        } else {
            return 0;
        }
    }
}
