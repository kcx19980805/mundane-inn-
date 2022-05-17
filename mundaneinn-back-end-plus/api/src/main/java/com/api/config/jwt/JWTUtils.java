package com.api.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.common.constant.Constants;
import com.common.utils.encryption.Base64Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 */
@Component
public class JWTUtils {

    @Autowired
    private JWTConfig jwtConfig;

    /**
     * 获取token
     *
     * @return token
     */
    public String getToken(String userId) {
        //头部信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("type", "JWT");
        header.put("alg", "HS512");
        long date = System.currentTimeMillis();
        Algorithm algorithm = Algorithm.HMAC512(jwtConfig.getSecret());
        return JWT.create()
                .withHeader(header)
                .withClaim(Constants.TOKEN_USERID, Base64Utils.base64Encoder(userId))
                .withClaim(Constants.TOKEN_UPDATE_TIME, date)
                //.withIssuedAt(iatDate)//设置签发时间
                //.withExpiresAt(expiresDate)//设置过期时间 过期时间大于签发时间
                .sign(algorithm);//Signature
    }

    /**
     * 校验 token 是否正确 通过tokenSecret 校验
     * true正确  false错误
     */
    public boolean verifyToken(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(jwtConfig.getSecret())).build();
            jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 校验 token 是否过期
     * true已过期 false未过期
     */
    public boolean isExpireTime(String token) {
        //更新时间+有效时间 与 当前时间 比较
        return JWT.decode(token).getClaim(Constants.TOKEN_UPDATE_TIME).asLong() + Long.parseLong(jwtConfig.getExpireTime()) < System.currentTimeMillis();
    }

    /**
     * 获取userId
     *
     * @param token
     * @return
     */
    public String getUserId(String token) {
        return JWT.decode(token).getClaim(Constants.TOKEN_USERID).asString();
    }
}

