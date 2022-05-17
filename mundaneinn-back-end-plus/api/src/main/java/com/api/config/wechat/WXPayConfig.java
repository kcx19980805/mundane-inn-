package com.api.config.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目 微信 小程序 相关配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx")
public class WXPayConfig {
    /**
     * 小程序-app_id
     */
    private String appid;

    /**
     * 小程序-secret
     */
    private String secret;

    /**
     * 商户号-mch_id
     */
    private String mchId;

    /**
     * key为商户平台设置的密钥key
     */
    private String key;

    /**
     * 支付成功回调地址notify_url
     */
    private String notifyUrl;

    /**
     * 授权后重定向的回调链接地址， 请使用 urlEncode 对链接进行处理
     * redirect_uri
     */
    private String redirectUri;

    /**
     * 用户扫码进入小程序授权
     */
    private String userRedirectUrl;
}
