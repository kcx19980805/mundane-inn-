package com.api.config.jwt;


import com.api.sys.entity.ClientUserTokenEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.api.sys.service.ClientUserTokenService;
import com.common.constant.Constants;
import com.common.constant.HttpStatus;
import com.common.exception.CustomException;
import com.common.utils.encryption.Base64Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证，前后端分离导致跨域，session不能保存用户，使用jwt进行登录
 */
@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    private ClientUserTokenService clientUserTokenService;
    @Autowired
    private JWTUtils tokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("operationUrl====" + request.getRequestURL().toString());
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        // 跨域访问设置
        response.setHeader("Access-Control-Allow-Origin", "*");
        //从header中获取token
        String token = request.getHeader(Constants.TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new CustomException(Constants.MESSAGE_LOGOUT, HttpStatus.UNAUTHORIZED);
        }

        //校验token是否正确
        if (!tokenUtils.verifyToken(token)) {
            throw new CustomException(Constants.MESSAGE_LOGOUT, HttpStatus.UNAUTHORIZED);
        }
        //校验token是否过期
        if (tokenUtils.isExpireTime(token)) {
            throw new CustomException(Constants.MESSAGE_LOGOUT, HttpStatus.UNAUTHORIZED);
        }
        //获取 用户id base64解码
        String userId = Base64Utils.base64Decoder(tokenUtils.getUserId(token));
        //获取数据库token，判断是否相等
        int i = clientUserTokenService.count(new QueryWrapper<ClientUserTokenEntity>().eq("user_id", userId).eq("token", token));
        if (i == 0) {
            throw new CustomException(Constants.MESSAGE_LOGOUT, HttpStatus.UNAUTHORIZED);
        }

        //设置userId到request里
        request.setAttribute(Constants.TOKEN_USERID, userId);
        return true;
    }
}
