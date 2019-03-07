package com.yukaiji.kjblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.yukaiji.kjblog.dao.mapper")
@ServletComponentScan
public class KjblogApplication extends SpringApplication{

    public static void main(String[] args) {
        SpringApplication.run(KjblogApplication.class, args);
    }

}

