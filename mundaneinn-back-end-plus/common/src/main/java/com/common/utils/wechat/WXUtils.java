package com.common.utils.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.constant.HttpStatus;
import com.common.exception.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信公众号 网页开发 获取用户微信信息
 * 公众平台以access_token为接口调用凭据，来调用接口，所有接口的调用需要先获取access_token，access_token在2小时内有效，过期需要重新获取，但1天内获取次数有限，开发者需自行存储
 * 1 第一步：用户同意授权，获取code (前端处理 返回给我们后端)
 * 2 第二步：通过code换取网页授权access_token
 * 3 第三步：刷新access_token（如果需要）
 * 4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
 * 5 附：检验授权凭证（access_token）是否有效
 */
@Component
public class WXUtils {


    /**
     * 微信公众号 网页开发 通过 code 获取 access_token
     * 参数	是否必须	 说明
     * appid	是	公众号的唯一标识
     * secret	是	公众号的appsecret
     * code	是	微信用户授权后的code参数
     * grant_type	是	填写为authorization_code
     * <p>
     * 返回成功：access_token	网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
     * expires_in	access_token接口调用凭证超时时间，单位（秒）
     * refresh_token	用户刷新access_token
     * openid	用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
     * scope	用户授权的作用域，使用逗号（,）分隔
     * <p>
     * 返回失败："errcode":40029,"errmsg":"invalid code"
     * https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html#2
     */
    public Map<String, Object> getOffiAccountAccessTokenByCode(String code, String appId, String secret) {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(appId) || StringUtils.isBlank(secret)) {
            throw new CustomException("参数异常", HttpStatus.ERROR);
        }
        RestTemplate restTemplate = new RestTemplate();
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
        String accessTokenResult = restTemplate.getForObject(accessTokenUrl, String.class);
        JSONObject jsonObject = JSON.parseObject(accessTokenResult, JSONObject.class);
        System.out.println("通过 code 获取 access_token====" + jsonObject);
        if (jsonObject != null) {
            if (!jsonObject.containsKey("errcode")) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("openid", jsonObject.get("openid").toString());
                map.put("accessToken", jsonObject.get("access_token").toString());
                //access_token接口调用凭证超时时间，单位（秒）默认7200秒
                map.put("expiresIn", jsonObject.get("expires_in").toString());
                map.put("refreshToken", jsonObject.get("refresh_token").toString());
                map.put("scope", jsonObject.get("scope").toString());
                return map;
            } else {
                throw new CustomException(jsonObject.get("errmsg").toString(), HttpStatus.ERROR);
            }
        } else {
            throw new CustomException("获取access_token请求异常", HttpStatus.ERROR);
        }
    }

    /**
     * 微信 公众号 网页开发
     * 验证accessToken 是否过期 true没有过期,false 已经过期
     */
    public boolean checkAccessToken(String accessToken, String openid) {
        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(openid)) {
            throw new CustomException("参数异常", HttpStatus.ERROR);
        }
        RestTemplate restTemplate = new RestTemplate();
        String accessTokenUrl = "https://api.weixin.qq.com/sns/auth?access_token=" + accessToken + "&openid=" + openid;
        String accessTokenResult = restTemplate.getForObject(accessTokenUrl, String.class);
        System.out.println("checkAccessToken====accessTokenResult1:====" + accessTokenResult);
        JSONObject jsonObject = JSON.parseObject(accessTokenResult, JSONObject.class);
        if (jsonObject != null) {
            return jsonObject.containsKey("errcode") && "0".equals(jsonObject.getString("errcode"));
        }
        throw new CustomException("请求异常", HttpStatus.ERROR);
    }

    /**
     * 微信 公众号 网页开发
     * 根据refreshToken 刷新accessToken
     */
    private String refreshAccessToken(String refreshToken, String appId) {
        if (StringUtils.isBlank(refreshToken) || StringUtils.isBlank(appId)) {
            throw new CustomException("参数异常", HttpStatus.ERROR);
        }
        RestTemplate restTemplate = new RestTemplate();
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + appId + "&grant_type=refresh_token&refresh_token=" + refreshToken;
        String accessTokenResult = restTemplate.getForObject(accessTokenUrl, String.class);
        System.out.println("refreshAccessToken===accessTokenResult2:====" + accessTokenResult);
        JSONObject jsonObject = JSON.parseObject(accessTokenResult, JSONObject.class);
        if (jsonObject != null) {
            if (jsonObject.containsKey("errcode")) {
                throw new CustomException(jsonObject.get("errmsg").toString(), HttpStatus.ERROR);
            } else {
                return jsonObject.get("access_token").toString();
            }
        } else {
            throw new CustomException("请求异常", HttpStatus.ERROR);
        }
    }

    /**
     * 通过accessToken openid 获取用户信息
     */
    public Map<String, Object> getUserInfo(String accessToken, String openid) {
        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(openid)) {
            throw new CustomException("参数异常");
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN";
        String userInfoResult = restTemplate.getForObject(userInfoUrl, String.class);
        System.out.println("微信用户信息:====" + userInfoResult);
        JSONObject jsonObject = JSON.parseObject(userInfoResult, JSONObject.class);
        if (jsonObject != null) {
            if (!jsonObject.containsKey("errcode")) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("openid", jsonObject.get("openid").toString());
                map.put("nickname", jsonObject.get("nickname").toString());
                map.put("headimgurl", jsonObject.get("headimgurl").toString());
                map.put("sex", jsonObject.get("sex").toString());
                map.put("province", jsonObject.get("province").toString());
                map.put("city", jsonObject.get("city").toString());
                map.put("country", jsonObject.get("country").toString());
                return map;
            } else {
                throw new CustomException(jsonObject.get("errmsg").toString(), HttpStatus.ERROR);
            }
        } else {
            throw new CustomException("请求失败", HttpStatus.ERROR);
        }
    }

    /**
     * 微信小程序 通过 appid appsecret 以及前端传的code 获取用户openid session_key
     * GET https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
     */
    public Map<String, Object> getMiniProgramUserOpenId(String code, String appId, String secret) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        System.out.println("微信小程序==result===" + result);
        JSONObject jsonObject = JSON.parseObject(result, JSONObject.class);
        if (jsonObject != null) {
            Map<String, Object> map = new HashMap<>(16);
            if (jsonObject.containsKey("errcode")) {
                System.out.println(jsonObject.get("errmsg").toString());
                if ("-1".equals(jsonObject.get("errcode").toString())) {
                    throw new CustomException("系统繁忙，请稍后再试");
                } else if ("40029".equals(jsonObject.get("errcode").toString())) {
                    throw new CustomException("code无效");
                } else if ("45011".equals(jsonObject.get("errcode").toString())) {
                    throw new CustomException("请求频繁,请稍后再试");
                } else if ("40163".equals(jsonObject.get("errcode").toString())) {
                    throw new CustomException("code已被使用");
                } else if ("0".equals(jsonObject.get("errcode").toString())) {
                    map.put("openid", jsonObject.get("openid"));
                    map.put("sessionKey", jsonObject.get("session_key"));
                    return map;
                } else {
                    throw new CustomException("系统繁忙，请稍后再试");
                }
            } else {
                map.put("openid", jsonObject.get("openid"));
                map.put("sessionKey", jsonObject.get("session_key"));
                return map;
            }
        } else {
            throw new CustomException("微信小程序获取用户信息异常");
        }
    }

}

