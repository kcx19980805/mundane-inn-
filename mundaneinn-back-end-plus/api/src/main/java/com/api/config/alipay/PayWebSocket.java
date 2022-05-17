package com.api.config.alipay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 在支付宝订单支付成功后，通知页面支付结果
 **/
//@ServerEndpoint("/bindingRecord")
//@Component
@Slf4j
public class PayWebSocket {

    private Session session;
    private static CopyOnWriteArraySet<PayWebSocket> payWebSockets = new CopyOnWriteArraySet<>();

    /**
     * 新建webSocket配置类
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 建立连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        payWebSockets.add(this);
        log.info("支付宝WebSocket新连接，连接总数:{}", payWebSockets.size());
    }

    /**
     * 断开连接
     */
    @OnClose
    public void onClose(){
        payWebSockets.remove(this);
        log.info("支付宝WebSocket断开连接，连接总数:{}", payWebSockets.size());
    }

    /**
     * 接收到信息
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        log.info("支付宝WebSocket收到客户端的信息:{}，连接总数:{}", message, payWebSockets.size());
    }

    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(String message){
        log.info("广播发送，信息:{}，总连接数:{}", message, payWebSockets.size());
        for (PayWebSocket payWebSocket : payWebSockets) {
            try {
                payWebSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.info("【广播发送】，信息异常:{}", e.fillInStackTrace());
            }
        }
    }

}
