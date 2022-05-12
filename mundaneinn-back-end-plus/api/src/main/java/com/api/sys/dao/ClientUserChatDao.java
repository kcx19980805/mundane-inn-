package com.api.sys.dao;

import com.api.sys.entity.ClientUserChatEntity;
import com.api.sys.requestEntity.RequestUserChatListEntity;
import com.api.sys.responseEntity.ResponseUserChatListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户聊天记录表
 * @author
 * @email
 * @date 2022-04-25 17:06:52
 */
@Mapper
public interface ClientUserChatDao extends BaseMapper<ClientUserChatEntity> {
    /***
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
     * 查询与当前用户存在聊天记录的所有用户id
      * @param currentUerId
     * @return
     */
    List<String> getOtherUserId(String currentUerId);


    /**
     * 查询2个用户之间的最后一条消息
     * @param currentUerId
     * @param otherUserId
     * @return
     */
    ResponseUserChatListEntity lastMessage(@Param("currentUerId") String currentUerId,@Param("otherUserId") String otherUserId);

    /**
     * 删除当前用户发送的聊天记录
     * @param req
     * @return
     */
    Integer deleteSendChat(RequestUserChatListEntity req);

    /**
     * 删除当前用户接收的聊天记录
     * @param req
     * @return
     */
    Integer deleteAcceptChat(RequestUserChatListEntity req);
}
