package com.api.sys.service;

import com.api.sys.requestEntity.RequestUserChatListEntity;
import com.api.sys.responseEntity.ResponseUserChatListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.api.sys.entity.ClientUserChatEntity;
import java.util.List;


/**
 * 用户聊天记录表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
public interface ClientUserChatService extends IService<ClientUserChatEntity> {
    /**
    * 查询2个用户之间的聊天记录
    * @return
    */
    List<ClientUserChatEntity> clientUserChatList(RequestUserChatListEntity req);


    /**
     * 用户聊天记录未读总数
     * @param req
     * @return
     */
    Integer unReadTotal(RequestUserChatListEntity req);

    /**
     * 查询与当前用户存在聊天记录的所有用户和最后一条消息
     * @param req
     * @return
     */
    List<ResponseUserChatListEntity> userList(RequestUserChatListEntity req);

    /**
    * 用户聊天记录表-新增
    * @param entity
    * @return
    */
    int saveClientUserChat(ClientUserChatEntity entity);

    /**
    * 用户聊天记录表-删除
    * @param req
    * @return
    */
    int deleteClientUserChat(RequestUserChatListEntity req);

    /**
    * 用户聊天记录表-已读
    * @param req
    * @return
    */
    int isReadChat(RequestUserChatListEntity req);
}

