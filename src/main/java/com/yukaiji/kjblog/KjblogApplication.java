package com.yukaiji.kjblog;

import com.yukaiji.kjblog.service.CacheTestService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@MapperScan("com.yukaiji.kjblog.dao.mapper")
@ServletComponentScan
@EnableCaching
public class KjblogApplication extends SpringApplication{


    public static void main(String[] args) throws InterruptedException {
        ApplicationContext applicationContext = SpringApplication.run(KjblogApplication.class, args);

        CacheTestService cacheTestService = (CacheTestService)applicationContext.getBean("cacheTestService");

        int i = 1;
        while (true){
            long start = System.currentTimeMillis();
            cacheTestService.testCaffeineCache("A");
            long end = System.currentTimeMillis();

            long start2 = System.currentTimeMillis();
            cacheTestService.testCaffeineCache2("A");
            long end2 = System.currentTimeMillis();

            long s = (end - start) / 1000;
            long s2 = (end2 - start2) / 1000;
            System.out.println("第" + i + "次 耗时: One -> "  + s + "，Two ->" + s2);
            i++;
            Thread.sleep(1000);
        }

//        for (int i = 0; i < 5000; i++) {
//            int finalI = i;
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        long start = System.currentTimeMillis();
//                        cacheTestService.testLocalCache(String.valueOf(finalI));
//                        long end = System.currentTimeMillis();
//                        long s = (end - start) / 1000;
//
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            thread.start();
//        }

    }

}

