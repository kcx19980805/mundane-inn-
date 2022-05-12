package com.admin.sys.controller;

import com.admin.config.shiro.ShiroUtils;
import com.admin.sys.entity.SysUserEntity;
import com.admin.sys.requestEntity.RequestLoginEntity;
import com.admin.sys.responseEntity.ResponseMenuListEntity;
import com.admin.sys.service.SysMenuService;
import com.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 登录相关
 */
@Api(tags = "登录")
@Controller
public class SysLoginController {

    @Autowired
    private SysMenuService sysMenuService;

    private static final Logger logger = LoggerFactory.getLogger(SysLoginController.class);

    /**
     * 平台登录
     */
    @ResponseBody
    @PostMapping(value = "/login")
    @ApiOperation(value = "平台登录", response = Result.class)
    public Result adminLogin(@Validated @RequestBody RequestLoginEntity requestLoginEntity) {
        try {
            //账号 base64解密
            String account = new String(Base64.getDecoder().decode(requestLoginEntity.getAccount()));
            //密码
            String password = requestLoginEntity.getPassword();
            System.out.println(account + "====" + password);
            logger.info("输出日志");
            //password = new String(Base64.getDecoder().decode(password));
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(account, password);
            subject.login(token);
        } catch (UnknownAccountException | LockedAccountException e) {

            return Result.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return Result.error("密码错误");
        } catch (AuthenticationException e) {
            return Result.error("账户验证失败");
        }
        return Result.ok();
    }

    /**
     * 退出
     */
    @ApiOperation(value = "平台退出")
    @GetMapping(value = "/logout")
    public String logout() {
        ShiroUtils.logout();
        return "redirect:login.html";
    }

    /**
     * 登录成功后进入index.html页面
     */
    @ApiOperation(value = "登录成功后进入index.html页面")
    @GetMapping({"/index"})
    String index(Model model) {
        SysUserEntity sysUserEntity = ShiroUtils.getObject();
        List<ResponseMenuListEntity> menuListEntityList = new ArrayList<>(16);
        menuListEntityList = sysMenuService.getAdminMenuList(sysUserEntity.getType().toString());
        model.addAttribute("account", sysUserEntity.getAccount());
        model.addAttribute("menus", menuListEntityList);
        System.out.println(model);
        return "index";
    }


}
