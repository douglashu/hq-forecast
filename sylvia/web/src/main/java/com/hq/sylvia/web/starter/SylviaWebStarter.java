package com.hq.sylvia.web.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Zhaoyang on 16/6/29.
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        RabbitAutoConfiguration.class
})
@EnableScheduling
@ComponentScan({"com.hq.sylvia", "com.hq.scrati.cache"})
//@EnableMongoRepositories(basePackages = "com.hq.sylvia.repository.mongo")
public class SylviaWebStarter {
    public static void main(String[] args) {
        SpringApplication.run(SylviaWebStarter.class, args);
    }
}

