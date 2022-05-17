package com.api.sys.service.impl;

import com.api.sys.entity.ClientUserTokenEntity;
import com.api.sys.requestEntity.*;
import com.api.sys.responseEntity.ResponseClientUserInfoEntity;
import com.api.sys.service.ClientUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.api.sys.dao.ClientUserDao;
import com.api.sys.entity.ClientUserEntity;
import com.api.sys.responseEntity.ResponseLoginEntity;
import com.api.sys.service.ClientUserTokenService;
import com.api.config.jwt.JWTUtils;
import com.common.constant.Constants;
import com.common.exception.CustomException;
import com.common.utils.date.DateUtils;
import com.common.utils.encryption.Shiro2Utils;
import com.common.utils.sms.AliSms;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * 用户管理
 *
 * @author
 * @email
 * @date 2020-12-03 14:40:12
 */
@SuppressWarnings("all")
@Service("clientUserService")
public class ClientUserServiceImpl extends ServiceImpl<ClientUserDao, ClientUserEntity> implements ClientUserService {
    @Autowired
    private JWTUtils tokenUtils;
    @Autowired
    private ClientUserTokenService clientUserTokenService;
    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 发送短信
     * @param phone
     * @return
     */
    @Override
    public int sendSms(String phone) {
        String code = RandomStringUtils.randomAlphabetic(6);
        if(!AliSms.sendSms(phone,code).equals("OK")){
            throw new CustomException("发送短信失败");
        }
        ClientUserEntity clientUserEntity=baseMapper.selectOne(new QueryWrapper<ClientUserEntity>().eq("account",phone).eq("is_del", 0));
        //如果不存在，创建账号，如果存在，修改用户验证码
        ClientUserEntity updateEntity = new ClientUserEntity();
        if(null==clientUserEntity){
            updateEntity.setAccount(phone);
            updateEntity.setPhone(phone);
            updateEntity.setPhoneCode(code);
            updateEntity.setPhoneCodeTime(LocalDateTime.now());
            return baseMapper.insert(updateEntity);
        }else{
            updateEntity.setId(clientUserEntity.getId());
            updateEntity.setPhoneCode(code);
            updateEntity.setPhoneCodeTime(LocalDateTime.now());
            return baseMapper.updateById(updateEntity);
        }
    }

    /**
     * 登录
     * @param req
     * @return
     */
    @Override
    public ResponseLoginEntity login(RequestLoginEntity req) {
        String account = new String(Base64.getDecoder().decode(req.getAccount()));
        String password = req.getPassword();
        String code = req.getCode();
        ClientUserEntity clientUser= baseMapper.selectOne(new QueryWrapper<ClientUserEntity>().eq("account", account).eq("is_del", 0));
        if (clientUser == null) {
            throw new CustomException("当前账号异常");
        }
        if ("1".equals(clientUser.getStatus())) {
            throw new CustomException("当前账号已被禁用");
        }
        boolean isCorrect=false;
        //如果是密码登录,否则验证码登录
        if(StringUtils.isNotBlank(password)){
            if(!Shiro2Utils.checkPasswordSha256(clientUser.getPassword(), password, clientUser.getSalt())){
                throw new CustomException("密码错误");
            }
        } else if(StringUtils.isNotBlank(code)){
            if(!code.equalsIgnoreCase(clientUser.getPhoneCode())){
                throw new CustomException("验证码错误");
            }
            if ((long) DateUtils.minusToLocalDateTime(clientUser.getPhoneCodeTime(), LocalDateTime.now()).get("minutes") > 5L) {
                throw new CustomException("验证码已过期");
            }
        } else{
            throw new CustomException("验证码或密码为空");
        }
        ClientUserTokenEntity clientUserTokenEntity = new ClientUserTokenEntity();
        //用户id
        String userId = clientUser.getId().toString();
        //生成token
        String token = tokenUtils.getToken(userId);
        clientUserTokenEntity.setToken(token);
        //如果存在token，更新用户 token，如果不存在，新增token
        if (clientUserTokenService.getOne(new QueryWrapper<ClientUserTokenEntity>().eq("user_id", userId)) != null) {
            clientUserTokenService.update(clientUserTokenEntity,new QueryWrapper<ClientUserTokenEntity>().eq("user_id", userId));
        } else {
            clientUserTokenEntity.setUserId(clientUser.getId());
            clientUserTokenService.save(clientUserTokenEntity);
        }
        ResponseLoginEntity responseLoginEntity = new ResponseLoginEntity();
        responseLoginEntity.setUserId(clientUser.getId().toString());
        responseLoginEntity.setToken(token);
        responseLoginEntity.setNickName(clientUser.getNickName());
        responseLoginEntity.setHeadImg(clientUser.getHeadImg());
        return responseLoginEntity;
    }

    /**
     * 用户基本信息
     * @return
     */
    @Override
    public ResponseClientUserInfoEntity getUserInfo(HttpServletRequest request) {
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        return baseMapper.userInfo(userId);
    }

    /**
     * 查询指定用户信息
     * @param req
     * @return
     */
    @Override
    public ResponseClientUserInfoEntity getOtherUserInfo(RequestClientUserInfoEntity req) {
        return baseMapper.userInfo(req.getUserId());
    }

    /**
     * 修改用户信息
     *
     * @param req
     * @return
     */
    @Override
    public int updateUserInfo(HttpServletRequest request,RequestClientUserUpdateEntity req) {
        String userId = request.getAttribute(Constants.TOKEN_USERID).toString();
        if(null==userId){
            throw new CustomException("用户信息不存在，请重新登录");
        }
        if(baseMapper.selectCount(new QueryWrapper<ClientUserEntity>().eq("phone",req.getPhone()).eq("is_del",0).ne("id",userId))>0){
            throw new CustomException("当前手机号已注册");
        }
        req.setUserId(userId);
        ClientUserEntity clientUserEntity = new ClientUserEntity();
        //复制属性，必须类型和名称完全相同
        BeanUtils.copyProperties(req, clientUserEntity);
        clientUserEntity.setId(Long.valueOf(userId));
        if(StringUtils.isNotBlank(clientUserEntity.getIdCard())){
            clientUserEntity.setIsCertified("1");
        }
        return baseMapper.updateById(clientUserEntity);
    }

    /**
     * 修改密码
     *
     * @param requestClientUserUpdatePassword1Entity
     * @return
     */
    @Override
    public int updatePassword(HttpServletRequest request,RequestClientUserUpdatePassword1Entity req) {
        String oldPassword = req.getOldPassword();
        String newPassword = req.getNewPassword();
        String confirmPassword = req.getConfirmPassword();
        if (!newPassword.equals(confirmPassword)) {
            throw new CustomException("两次输入新密码不匹配");
        }
        Long userId = Long.valueOf(request.getAttribute(Constants.TOKEN_USERID).toString());
        ClientUserEntity clientUserEntity1 = baseMapper.selectOne(new QueryWrapper<ClientUserEntity>().eq("id", userId).eq("is_del", 0));
        if (clientUserEntity1 == null) {
            throw new CustomException("当前账号不存在");
        }
        if (StringUtils.isNotBlank(clientUserEntity1.getPassword()) && !Shiro2Utils.checkPasswordSha256(clientUserEntity1.getPassword(), oldPassword, clientUserEntity1.getSalt())) {
            throw new CustomException("旧密码错误");
        }
        ClientUserEntity clientUserEntity2 = new ClientUserEntity();
        //新密码 加密
        String salt = Shiro2Utils.getGenerateSalt();
        clientUserEntity2.setSalt(salt);
        clientUserEntity2.setPassword(Shiro2Utils.sha256(newPassword, salt));
        clientUserEntity2.setId(userId);
        return baseMapper.updateById(clientUserEntity2);
    }


    /**
     * 退出登录
     * @return
     */
    @Override
    public int logOut(HttpServletRequest request) {
        String userId1 = request.getAttribute(Constants.TOKEN_USERID).toString();
        ClientUserEntity clientUser = new ClientUserEntity();
        clientUser.setId(Long.valueOf(userId1));
        clientUser.setIsOnline("0");
        return baseMapper.updateById(clientUser);
    }

    /**
     * 发送邮箱验证码
     *
     * @param email
     * @return
     */
    @Override
    public int sendEmailCode(String email) {
        ClientUserEntity clientUserEntity = baseMapper.selectOne(new QueryWrapper<ClientUserEntity>().eq("email", email).eq("is_del", 0));
        if (clientUserEntity == null) {
            throw new CustomException("邮箱错误或用户不存在");
        }
        String code = RandomStringUtils.randomAlphabetic(6);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("红尘客栈");
        mailMessage.setText("尊敬的" + clientUserEntity.getAccount() + "，你的验证码是:" + code + "用于找回密码，5分钟内有效，请勿告知他人");
        mailMessage.setTo(email);
        mailMessage.setFrom("1255753142@qq.com");
        mailSender.send(mailMessage);
        ClientUserEntity updateEntity = new ClientUserEntity();
        updateEntity.setId(clientUserEntity.getId());
        updateEntity.setEmailCode(code);
        updateEntity.setEmailCodeTime(LocalDateTime.now());
        return baseMapper.updateById(clientUserEntity);
    }

    /**
     * 邮箱验证码是否正确
     *
     * @param requestVerificationCodeIsCorrectEntity
     * @return
     */
    @Override
    public int emailCodeIsCorrect(RequestSendEmailCodeCorrectEntity req) {
        ClientUserEntity clientUserEntity = baseMapper.selectOne(new QueryWrapper<ClientUserEntity>().
                eq("email", req.getEmail())
                .eq("is_del", 0));
        String mysqlEmailCode = clientUserEntity.getEmailCode();
        if (mysqlEmailCode == null || "".equals(mysqlEmailCode)) {
            throw new CustomException("请重新发送验证码");
        }
        if ((long) DateUtils.minusToLocalDateTime(clientUserEntity.getEmailCodeTime(), LocalDateTime.now()).get("minutes") > 5L) {
            throw new CustomException("验证码已过期");
        }
        return mysqlEmailCode.equals(req.getEmailCode()) ? 1 : 0;
    }

    /**
     * 找回密码-邮箱验证通过-修改密码
     *
     * @param requestClientUserUpdatePassword2Entity
     * @return
     */
    @Override
    public int updatePassword2(RequestClientUserUpdatePassword2Entity req) {
        String newPassword = req.getNewPassword();
        String confirmPassword = req.getConfirmPassword();
        if (!newPassword.equals(confirmPassword)) {
            throw new CustomException("两次输入新密码不匹配");
        }
        ClientUserEntity clientUserEntity = baseMapper.selectOne(new QueryWrapper<ClientUserEntity>().eq("email", req.getEmail()).eq("is_del", 0));
        if (clientUserEntity == null) {
            throw new CustomException("当前账号异常");
        }
        ClientUserEntity updateEntity = new ClientUserEntity();
        //新密码 加密
        String salt = Shiro2Utils.getGenerateSalt();
        updateEntity.setId(clientUserEntity.getId());
        updateEntity.setSalt(salt);
        updateEntity.setPassword(Shiro2Utils.sha256(newPassword, salt));
        updateEntity.setEmailCode("");
        return baseMapper.updateById(updateEntity);
    }


}

