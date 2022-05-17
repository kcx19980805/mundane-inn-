package com.api.config.websocket;


import com.alibaba.fastjson.JSONObject;
import com.api.sys.entity.ClientUserChatEntity;
import com.api.sys.entity.ClientUserEntity;
import com.api.sys.service.ClientUserChatService;
import com.api.sys.service.ClientUserService;
import com.common.exception.CustomException;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocket 服务端 在支付宝订单支付成功后，通知页面支付结果
 */
@Service
public class WebSocketHandlerPay extends TextWebSocketHandler{

    private Logger logger = LoggerFactory.getLogger(WebSocketInterceptor.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    //用户列表
    public static final ConcurrentHashMap<String, WebSocketSession> users = new ConcurrentHashMap<>();
    //用户标识
    private static final String userId = "userId";

    /**
     * 得到当前在线人数
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 添加在线人数
     */
    public static synchronized void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    /**
     * 减少在线人数
     */
    public static synchronized void subOnlineCount() {
        if(onlineCount.get()>0){
            onlineCount.getAndDecrement();
        }
    }

    /**
     * 获取用户标识
     * @Title: getMchNo
     * @param session
     * @return
     */
    private String getUserId(WebSocketSession session) {
        try {
            String id= session.getAttributes().get(userId).toString();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("支付WebSocket用户异常");
        }
    }
    /**
     * 在 WebSocket 协商成功并且 WebSocket 连接打开并准备好使用后调用。
     * @param session
     * @throws Exception 这个方法可以处理或传播异常
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String id= getUserId(session);
        if (Strings.isNotEmpty(id)) {
            if(!users.containsKey(id)){
                addOnlineCount();
                logger.info("用户"+id+"加入支付WebSocket！当前在线人数:" + getOnlineCount());
            }
            users.put(id,session);
        }else{
            throw new CustomException("您还未登录，请先登录");
        }
    }

    /**
     * 处理文本消息
     * @param session
     * @param message
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        logger.info("支付WebSocket收到客户端消息："+ message.getPayload());
        JSONObject chat = JSONObject.parseObject(message.getPayload());
        String acceptUserId = chat.get("acceptUserId").toString();
        //发送给指定用户或所有人
        sendMessageToUser(acceptUserId,message);
    }

    /**
     * 处理来自底层 WebSocket 消息传输的错误。
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        logger.error("支付WebSocket连接出错");
        subOnlineCount();
        users.remove(getUserId(session));
    }

    /**
     * 在任一方关闭 WebSocket 连接后或发生传输错误后调用。
     * 尽管从技术上讲会话可能仍处于打开状态，但取决于底层实现，不鼓励此时发送消息，并且很可能不会成功。
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String id= getUserId(session);
        logger.info("用户："+id+"支付WebSocket连接已关闭，状态：" + status);
        subOnlineCount();
        users.remove(getUserId(session));
    }

    /**
     * WebSocketHandler 是否处理部分消息。 如果此标志设置为 true 并且底层 WebSocket 服务器支持部分消息，
     * 则可能会拆分较大的 WebSocket 消息或未知大小的消息，并且可能会通过对 handleMessage(WebSocketSession,
     * WebSocketMessage) 的多次调用来接收。 标志 WebSocketMessage.isLast() 指示消息是否是部分的以及它是否是最后一部分。
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 发送信息给指定用户
     * @param userId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String userId, TextMessage message) {
        WebSocketSession webSocketSession = users.get(userId);
        if(null == webSocketSession){
            logger.error("支付WebSocket发送消息时没有找到WebSocketSession，在："+ LocalDateTime.now());
            return false;
        }
        if(!webSocketSession.isOpen()){
            logger.error("支付WebSocket发送消息时WebSocketSession关闭了，在："+ LocalDateTime.now());
            return false;
        }
        try {
            webSocketSession.sendMessage(message);
        } catch (IOException e) {
            logger.error("支付WebSocket发送消息失败："+e);
            return false;
        }
        return true;
    }
}
