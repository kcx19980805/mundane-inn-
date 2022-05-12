package com.admin.config.shiro;

import com.alibaba.fastjson.JSON;
import com.common.utils.Result;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义的shiro权限验证失败的过滤器
 */
public class ShiroValidationFailedFilter extends FormAuthenticationFilter {

    /**
     * 处理验证失败
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            return super.onAccessDenied(request, response);
        } else {
            if (isAjax((HttpServletRequest) request)) {
                //如果是ajax返回指定格式数据
                HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json");
                //返回禁止访问json字符串
                httpServletResponse.getWriter().write(JSON.toJSONString(Result.error("请重新登录")));
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }

    /**
     * 是否ajax请求
     */
    private boolean isAjax(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWithHeader);
    }
}
