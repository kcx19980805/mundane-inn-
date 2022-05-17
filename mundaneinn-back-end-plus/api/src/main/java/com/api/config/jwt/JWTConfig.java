package com.api.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * token 相关配置
 *
 */
@Data
@Component
@ConfigurationProperties(prefix = "token")
public class JWTConfig {

    /**
     * 令牌自定义标识
     */
    private String header;

    /**
     * 令牌密钥
     */
    private String secret;

    /**
     * 令牌有效期（分钟 默认30天）
     */
    private String expireTime;

}
