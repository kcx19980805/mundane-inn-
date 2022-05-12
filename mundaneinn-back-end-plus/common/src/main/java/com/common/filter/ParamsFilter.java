package com.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 处理请求参数中的特殊字符
 * @author: song
 * @data: 2019/12/13 10:10
 */
public class ParamsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        ParamsRequestWrapper requestWrapper = new ParamsRequestWrapper(httpRequest);
        filterChain.doFilter(requestWrapper, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
