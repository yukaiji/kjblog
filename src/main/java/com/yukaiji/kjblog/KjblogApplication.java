package com.yukaiji.kjblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yukaiji.kjblog.dao.mapper")
public class KjblogApplication extends SpringApplication{

    public static void main(String[] args) {
        SpringApplication.run(KjblogApplication.class, args);
    }

}

