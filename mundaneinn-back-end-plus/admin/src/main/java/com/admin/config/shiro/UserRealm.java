package com.admin.config.shiro;


import com.admin.sys.entity.SysUserEntity;
import com.admin.sys.service.SysMenuService;
import com.admin.sys.service.SysUserService;
import com.common.utils.encryption.Shiro2Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 认证
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserEntity sysUserEntity = (SysUserEntity) principals.getPrimaryPrincipal();
        List<String> permsList;
        //根据用户类型查询所有的操作权限(增删改查) type = 0超级管理员拥有最高权限
        permsList = sysMenuService.getPermsList(sysUserEntity.getType().toString());
        Set<String> permsSet = new HashSet<>();
        if (permsList != null && permsList.size() > 0) {
            for (String perms : permsList) {
                if (StringUtils.isBlank(perms)) {
                    continue;
                }
                permsSet.addAll(Arrays.asList(perms.trim().split(",")));
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }


    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        SysUserEntity user = sysUserService.login(token.getUsername());
        //账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号错误");
        }
        //账号锁定 (0正常  1禁用)
        if ("1".equals(user.getStatus())) {
            throw new LockedAccountException("账号已被禁用,请联系管理员");
        }
        info = new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()), getName());
        return info;
    }

    /**
     * 密码校验
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        //密码加密 类型
        shaCredentialsMatcher.setHashAlgorithmName(Shiro2Utils.HASH_ALGORITHM_NAME_SHA256);
        shaCredentialsMatcher.setHashIterations(Shiro2Utils.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }
}
