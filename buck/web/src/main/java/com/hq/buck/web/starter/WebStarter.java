package com.hq.buck.web.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Zale on 16/6/29.
 */
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@ComponentScan("com.gd")
@MapperScan("com.hq.peaches.dao")
public class WebStarter {
    public static void main(String[] args) {
        SpringApplication.run(WebStarter.class, args);
    }

}
