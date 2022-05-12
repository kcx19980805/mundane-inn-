package com.api.sys.service.impl;

import com.api.sys.requestEntity.RequestUserChatListEntity;
import com.api.sys.responseEntity.ResponseUserChatListEntity;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import com.api.sys.dao.ClientUserChatDao;
import com.api.sys.entity.ClientUserChatEntity;
import com.api.sys.service.ClientUserChatService;

@Service("clientUserChatService")
public class ClientUserChatServiceImpl extends ServiceImpl<ClientUserChatDao, ClientUserChatEntity> implements ClientUserChatService {
    /**
    * 查询2个用户之间的聊天记录
    * @return
    */
    @Override
    public List<ClientUserChatEntity> clientUserChatList(RequestUserChatListEntity req) {
        return baseMapper.clientUserChatList(req);
    }

    /**
     * 用户聊天记录未读总数
     * @param req
     * @return
     */
    @Override
    public Integer unReadTotal(RequestUserChatListEntity req) {
        return baseMapper.unReadTotal(req);
    }

    /**
     * 查询与当前用户存在聊天记录的所有用户和最后一条消息
     * @param req
     * @return
     */
    @Override
    public List<ResponseUserChatListEntity> userList(RequestUserChatListEntity req) {
        List<ResponseUserChatListEntity> list = new ArrayList<>();
        List<String> otherUserIds = baseMapper.getOtherUserId(req.getCurrentUserId());
        for(String otherUserId : otherUserIds){
            ResponseUserChatListEntity entity = baseMapper.lastMessage(req.getCurrentUserId(),otherUserId);
            list.add(entity);
        }
        return list;
    }

    /**
     * 用户聊天记录表-新增
     * @param entity
     * @return
     */
    @Override
    public int saveClientUserChat(ClientUserChatEntity entity) {
        return baseMapper.insert(entity);
    }

    /**
    * 用户聊天记录表-删除
    * @param req
    * @return
    */
    @Override
    public int deleteClientUserChat(RequestUserChatListEntity req) {
        baseMapper.deleteSendChat(req);
        baseMapper.deleteAcceptChat(req);
        return 1;
    }

    /**
     * 用户聊天记录表-已读
     * @param req
     * @return
     */
    @Override
    public int isReadChat(RequestUserChatListEntity  req) {
        ClientUserChatEntity entity = new ClientUserChatEntity();
        entity.setIsRead("1");
        return baseMapper.update(entity,new QueryWrapper<ClientUserChatEntity>().eq("send_user_id",req.getOtherUserId()).eq("accept_user_id",req.getCurrentUserId()));
    }

}
