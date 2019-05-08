package com.yukaiji.kjblog.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * 设置Spring临时文件的存储路径
 * 否则长时间运行后，上传文件报错the temporary uplaod location *** is not valid
 * 也可以重启服务解决
 * @author yukaiji
 * @date 2019/5/8
 */
@Configuration
public class MultipartConfig{
    /**
     *文件临时上传路径
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location  = System.getProperty("user.dir") +"/data/tmp";
        File tmpFile = new File (location);
        if(!tmpFile.exists()){
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }
}