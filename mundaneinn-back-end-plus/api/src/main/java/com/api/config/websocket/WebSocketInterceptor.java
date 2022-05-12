package com.api.config.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * websocket握手的拦截
 */
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {
    private Logger logger = LoggerFactory.getLogger(WebSocketInterceptor.class);

    /**
     * 在处理握手之前调用。
     * @param request 当前请求
     * @param response 当前响应
     * @param handler 目标 WebSocket 处理程序
     * @param attributes 来自 HTTP 握手的属性，用于与 WebSocket 会话相关联,对应WebsocketSession里的属性
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpSession session = httpServletRequest.getSession(true);
            //获取用户id
            String userId = httpServletRequest.getParameter("userId");
            if (null != session) {
                attributes.put("userId", userId);
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * 握手完成后调用。 响应状态和标头指示握手的结果，即是否成功。
     * @param request 当前请求
     * @param response 当前响应
     * @param wsHandler 标 WebSocket 处理程序
     * @param e 握手期间引发的异常，如果没有异常，则返回 null
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception e) {

    }
}
