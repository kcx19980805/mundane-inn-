

package com.admin.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.common.constant.Constants;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 */
@Configuration
public class ShiroConfig {

    /**
     * 将自己的验证方式加入容器
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * 单机环境，session交给shiro管理，ConditionalOnProperty满足条件才注入
     */
    @Bean
    @ConditionalOnProperty(prefix = "sys", name = "cluster", havingValue = "false")
    public DefaultWebSessionManager sessionManager(@Value("${sys.globalSessionTimeout}") long globalSessionTimeout) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionValidationInterval(globalSessionTimeout * 1000);
        sessionManager.setGlobalSessionTimeout(globalSessionTimeout * 1000);
        //shiro 的session是否放在cookie中  true是 false否
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie(globalSessionTimeout));
        return sessionManager;
    }

    /**
     * 设置cookie
     * 不同项目(服务)name不要重复 防止因cookieName相同导致session被覆盖
     */
    private SimpleCookie sessionIdCookie(long globalSessionTimeout) {
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("adminUSERSESSIONID");
        cookie.setHttpOnly(true);
        //有效时间 秒
        cookie.setMaxAge(Integer.parseInt(globalSessionTimeout + "") * 1000);
        return cookie;
    }

    /**
     * 集群环境，session交给spring-session管理，ConditionalOnProperty满足条件才注入
     */
    @Bean
    @ConditionalOnProperty(prefix = "sys", name = "cluster", havingValue = "true")
    public ServletContainerSessionManager servletContainerSessionManager() {
        return new ServletContainerSessionManager();
    }

    /**
     * shiro管理核心
     * @param sessionManager
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    /**
     * shiro过滤器，配置拦截路径
     * @param securityManager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login.html");
        shiroFilter.setSuccessUrl("/index.html");
        shiroFilter.setUnauthorizedUrl("/");
        /*
        anon:无需认证可以访问
        authc:必须认证才能访问
        user:必须拥有记住我功能才能访问
        perms:拥有对某个资源的权限才能访问
        role:拥有某个角色权限才能访问
        */
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/images/**", "anon");
        filterMap.put("/img/**", "anon");
        filterMap.put("/adminFiles/**", "anon");
        filterMap.put("/static/**", "anon");
        filterMap.put("/doc.html", "anon");
        filterMap.put("/*.html", "anon");
        filterMap.put("/*.js", "anon");
        filterMap.put("/*.css", "anon");

        // 文件路径
        filterMap.put(Constants.RESOURCE_UPLOAD + "/**", "anon");
        filterMap.put(Constants.RESOURCE_DOWNLOAD + "/**", "anon");
        filterMap.put("/login.html", "anon");
        filterMap.put("/login/**", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/captcha.jpg", "anon");
        /*代码map.put("/**", "authc")使得所有请求都需要认证才能请求成功，否则就跳转至setLoginUrl中指定的页面，
        同时为了使登录页面可以在未登陆时请求成功，需要用代码map.put("/login","anon")进行设置*/
        filterMap.put("/**", "authc");
        Map<String, Filter> filters = shiroFilter.getFilters();
        //将自定义的权限验证失败的过滤器ShiroFilterFactoryBean注入shiroFilter
        filters.put("authc", new ShiroValidationFailedFilter());
        shiroFilter.setFilters(filters);
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    /**
     * 在实现可初始化或可销毁接口的Shiro对象上自动调用init（）和/或destroy（）方法。
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 开启Shiro注解的必要配置(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,后台接口的权限验证
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启Shiro注解的必要配置(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,后台接口的权限验证
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 整合shiro thymeleaf实现页面的权限验证
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
