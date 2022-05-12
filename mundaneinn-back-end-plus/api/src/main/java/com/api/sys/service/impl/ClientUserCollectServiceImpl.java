package com.api.sys.service.impl;

import com.api.sys.responseEntity.ResponseCollectOtherListEntity;
import com.api.sys.responseEntity.ResponseHouseListEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;

import com.api.sys.dao.ClientUserCollectDao;
import com.api.sys.entity.ClientUserCollectEntity;
import com.api.sys.service.ClientUserCollectService;

@Service("clientUserCollectService")
public class ClientUserCollectServiceImpl extends ServiceImpl<ClientUserCollectDao, ClientUserCollectEntity> implements ClientUserCollectService {
    /**
    * 用户收藏列表
    * @return
    */
    @Override
    public List<ResponseHouseListEntity> clientUserCollectList(String userId) {
        return baseMapper.clientUserCollectList(userId);
    }

    /**
     * 查询其他用户的收藏房源
     * @param userId
     * @return
     */
    @Override
    public List<ResponseCollectOtherListEntity> getOtherUserCollectList(String userId) {
        return baseMapper.getOtherUserCollectList(userId);
    }

    /**
     * 用户收藏表-新增
     * @param userId
     * @param houseId
     * @return
     */
    @Override
    public int saveClientUserCollect(String userId,String houseId) {
        ClientUserCollectEntity entity = new ClientUserCollectEntity();
        entity.setUserId(Long.valueOf(userId));
        entity.setHouseId(Long.valueOf(houseId));
        if(baseMapper.selectCount(new QueryWrapper<ClientUserCollectEntity>()
                .eq("user_id",userId)
                .eq("house_id",houseId))>0){
            return 0;
        }
        return baseMapper.insert(entity);
    }


    /**
     * 用户收藏表-删除
     * @param userId
     * @param houseId
     * @return
     */
    @Override
    public int deleteClientUserCollectById(String userId,String houseId) {
        ClientUserCollectEntity entity = new ClientUserCollectEntity();
        return baseMapper.delete(new QueryWrapper<ClientUserCollectEntity>()
                .eq("user_id",userId)
                .eq("house_id",houseId));
    }

}
