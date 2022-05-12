package com.admin.config.shiro;

import com.admin.sys.entity.SysUserEntity;
import com.common.exception.CustomException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 */
public class ShiroUtils {


    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }


    /**
     * 返回当前 操作者 对象
     *
     * @return
     */
    public static SysUserEntity getObject() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 返回的是 当前操作者 账号id
     *
     * @return
     */
    public static Long getOperatorId() {
        return getObject().getId();
    }

    /**
     * 返回的是 当前操作者 平台账号id
     *
     * @return
     */
    public static Long getUserId() {
        return getObject().getId();
    }

    /**
     * 返回的是 当前操作者 账号
     *
     * @return
     */
    public static String getOperatorAccount() {
        return getObject().getAccount();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static String getKaptcha(String key) {
        Object kaptcha = getSessionAttribute(key);
        if (kaptcha == null) {
            throw new CustomException("验证码已失效");
        }
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }
}
