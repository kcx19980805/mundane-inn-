package com.admin.config.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目 微信 公众号 相关配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx")
public class WxConfig {
    /**
     * 公众号app_id
     */
    private String appid;

    /**
     * 公众号secret
     */
    private String secret;

    /**
     * 商户号mch_id
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
    private String branchOfficeRedirectUri;

    /**
     * 扫码进入订单详情页面
     */
    private String orderDetailsUrl;

    /**
     * 申请退款 调用小程序端退款接口
     */
    private String refundUrl;

    /**
     * 设备二维码 内容前缀
     */
    private String equipmentQdCodeUrl;

    /**
     * 处理待分润订单 调用小程序端分润接口
     */
    private String profitUrl;

}
