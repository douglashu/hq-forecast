package com.hq.crash.web.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Zhaoyang on 16/6/29.
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        RabbitAutoConfiguration.class
})
@EnableScheduling
@ComponentScan({ "com.hq.crash", "com.hq.scrati.cache" })
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = "com.hq.crash.repository.mongo")
public class CrashWebStarter {
    public static void main(String[] args) {
        SpringApplication.run(CrashWebStarter.class, args);
    }
}
