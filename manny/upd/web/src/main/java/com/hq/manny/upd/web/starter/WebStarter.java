package com.hq.manny.upd.web.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Zale on 16/6/29.
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        RabbitAutoConfiguration.class
})
@EnableScheduling
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = "com.hq.manny.upd.dao.mongodb")
@ComponentScan("com.hq")
public class WebStarter {
    public static void main(String[] args) {

        SpringApplication.run(WebStarter.class, args);
    }

}
