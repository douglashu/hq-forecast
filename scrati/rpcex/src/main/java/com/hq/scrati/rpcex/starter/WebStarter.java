package com.hq.scrati.rpcex.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Zale on 16/6/29.
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan("com.hq")
public class WebStarter {
    public static void main(String[] args) {

        SpringApplication.run(WebStarter.class, args);
    }

}
