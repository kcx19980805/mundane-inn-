package com.common.utils.pay;

import com.common.exception.CustomException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付接口组件
 */
public class WXpay {
    /**
     * 小程序支付-统一下单 生成预支付订单
     *
     * @param openid     微信用户唯一标识
     * @param appid      微信分配的小程序ID
     * @param mchId      微信支付分配的商户号   微信用户唯一标识
     * @param outTradeNo 商家订单号
     * @param body       商品描述
     * @param attach     自定义参数
     * @param notifyUrl  回调地址
     * @param totalFee   订单总金额，单位为分 不能有小数点
     * @param key        key为商户平台设置的密钥key
     * @return
     */
    public static Map<String, String> miniProgramPay(String openid, String appid, String mchId, String outTradeNo, String totalFee, String key, String notifyUrl, String body, String attach, HttpServletRequest request) {
        // 生成微信预支付订单
        Map<String, String> map = new HashMap<>(16);
        map.put("openid", openid);
        map.put("appid", appid);
        map.put("mch_id", mchId);
        map.put("out_trade_no", outTradeNo);
        //随机字符串 String(32)
        map.put("nonce_str", WXPayXmlUtil.generateNonceStr());
        //商品描述 String(128)
        map.put("body", body);
        map.put("fee_type", "CNY");
        map.put("sign_type", WXPayConstants.SignType.MD5.toString());
        //自定义参数
        map.put("attach", attach);
        ///回调地址
        map.put("notify_url", notifyUrl);
        map.put("trade_type", "JSAPI");
        map.put("total_fee", totalFee);
        //支持IPV4和IPV6两种格式的IP地址。用户的客户端IP
        map.put("spbill_create_ip", request.getRemoteAddr());

        System.out.println("生成微信预支付订单==参数==" + map);
        RestTemplate restTemplate = new RestTemplate();
        try {
            //生成带有 sign 的 XML 格式字符串
            String requestXml = WXPayXmlUtil.generateSignedXml(map, key, WXPayConstants.SignType.MD5);
            System.out.println("生成带有 sign 的 XML 格式字符串==" + requestXml);
            //调用 统一下单 接口
            // 设置restemplate编码为utf-8
            restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            String resultXml = restTemplate.postForObject("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXml, String.class);
            System.out.println("生成微信预支付订单==结果resultXml==" + resultXml);
            //解析 xml结果转map
            Map<String, String> resultMap = WXPayXmlUtil.xmlToMap(resultXml);
            System.out.println("生成微信预支付订单==结果resultMap==" + resultMap);
            //返给前端参数
            Map<String, String> result = new HashMap<>(16);
            result.put("appId", resultMap.get("appid"));
            result.put("timeStamp", String.valueOf(WXPayXmlUtil.getCurrentTimestamp()));
            result.put("nonceStr", WXPayXmlUtil.generateNonceStr());
            result.put("package", "prepay_id=" + resultMap.get("prepay_id"));
            result.put("signType", WXPayConstants.SignType.MD5.toString());
            String sign = WXPayXmlUtil.generateSignature(result, key, WXPayConstants.SignType.MD5);
            result.put("paySign", sign);
            System.out.println("生成微信预支付订单==返给前端参数==" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }
    }
}
