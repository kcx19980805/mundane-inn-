package com.common.utils.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 阿里云短信服务
 * @author kcx
 */
public class AliSms {
    public static String sendSms(String phoneNumber,String code){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GJ71dyqDAvPpVJG7FY6",
                "IMRqUyUsokGMKud3KUVl7M2LdQ8fqI");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "毕业设计网站");
        request.putQueryParameter("TemplateCode", "SMS_206547320");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject jsonObj = JSONObject.parseObject(response.getData());
            return jsonObj.get("Message").toString();
        } catch (ServerException e) {
            e.printStackTrace();
            return "error";
        } catch (ClientException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
