package com.api.sys.service;

import com.api.sys.requestEntity.*;
import com.api.sys.responseEntity.ResponseClientUserInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.api.sys.entity.ClientUserEntity;
import com.api.sys.responseEntity.ResponseLoginEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户管理
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:12
 */
public interface ClientUserService extends IService<ClientUserEntity> {

    /**
     * 发送短信
     * @param phone
     * @return
     */
    int sendSms(String phone);

    /**
     * 登录
     * @param req
     * @return
     */
    ResponseLoginEntity  login(RequestLoginEntity req);

    /**
     * 用户基本信息
     * @return
     */
    ResponseClientUserInfoEntity getUserInfo(HttpServletRequest request);

    /**
     * 查询指定用户信息
     * @param req
     * @return
     */
    ResponseClientUserInfoEntity getOtherUserInfo(RequestClientUserInfoEntity req);

    /**
     * 修改用户信息
     *
     * @param req
     * @return
     */
    int updateUserInfo(HttpServletRequest request,RequestClientUserUpdateEntity req);

    /**
     * 修改密码
     *
     * @param req
     * @return
     */
    int updatePassword(HttpServletRequest request,RequestClientUserUpdatePassword1Entity req);

    /**
     * 退出登录
     * @return
     */
    int logOut(HttpServletRequest request);

    /**
     * 发送邮箱验证码
     * @param email
     * @return
     */
    int sendEmailCode(String email);

    /**
     * 邮箱验证码是否正确
     * @param req
     * @return
     */
    int emailCodeIsCorrect(RequestSendEmailCodeCorrectEntity req);

    /**
     * 找回密码-邮箱验证通过-修改密码
     * @param req
     * @return
     */
    int updatePassword2(RequestClientUserUpdatePassword2Entity req);
}

