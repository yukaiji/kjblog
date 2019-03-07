package com.yukaiji.kjblog.config;

import com.yukaiji.kjblog.common.HttpUtils;
import com.yukaiji.kjblog.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义过滤器
 * @author kaijiyu
 */
@WebFilter(filterName = "myFilter", urlPatterns = "/*")
public class MyFilter extends BaseController implements Filter{

    private static final Logger logger = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean filterFlag = true;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestIp = HttpUtils.getIpAddr(request);
        String requestUrl = request.getRequestURI();
        logger.info("请求的Ip地址为:" + requestIp + ",请求URL为:" + requestUrl);
        if (isXss(requestUrl)) {
            filterFlag = false;
            printString(response, requestIp + "您好,请不要恶意进行访问!");
        }
        if (filterFlag) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 判断是否为要拦截的请求
     */
    private boolean isXss(String url) {
        if (url.contains("php") || url.contains("scrpit") || url.contains("javascrpit") || url.contains("'")
                || url.contains("admin") || url.contains("jsp") || url.contains("mysql")){
            return true;
        }
        return false;
    }
}
