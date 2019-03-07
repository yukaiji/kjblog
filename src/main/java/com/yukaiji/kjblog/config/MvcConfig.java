package com.yukaiji.kjblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 自定义配置文件
 * addViewControllers 页面的配置
 * addResourceHandlers 静态资源配置，否则读取不到静态资源
 * addInterceptors 自定义拦截器配置
 * @author kaijiyu
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    /**
     * 默认首页的跳转
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:index");
    }

    /**
     * 设置静态资源，防止被拦截
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/editor/**").addResourceLocations("classpath:/static/editor/");
        registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/static/img/upload/");
    }

    /**
     * 添加拦截器
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns 用于添加拦截规则
        //excludePathPatterns 用于删除拦截规则
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/uploadfile").addPathPatterns("/write").addPathPatterns("writeArticle");
        super.addInterceptors(registry);
    }
}
