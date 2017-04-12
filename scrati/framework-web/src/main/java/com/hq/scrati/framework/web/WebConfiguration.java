package com.hq.scrati.framework.web;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hq.scrati.model.cfg.SDKConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by Zale on 16/6/29.
 */
@Configuration
@ImportResource("classpath:spring-esbsdk-web.xml")
public class WebConfiguration {
    @Bean
    public FilterRegistrationBean webFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        registration.setFilter(characterEncodingFilter);
        return registration;
    }
    @Value("${sdkconfig.zookeeperAddress}")
    private String address;
    @Value("${sdkconfig.application}")
    private String application;
    @Bean(name="sdkconfig")
    public SDKConfig sdkConfig() throws IOException {
        SDKConfig sdkConfig = new SDKConfig();
        sdkConfig.setApplication(application);
        sdkConfig.setZookeeperAddress(address);
        return sdkConfig;
    }
    @Bean
    public ServletRegistrationBean webServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        return servletRegistrationBean;
    }
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix="datasource.mysql")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean
    public HttpMessageConverters messageConverters(){
        FastJsonHttpMessageConverter fastjson = new FastJsonHttpMessageConverter();
        FormHttpMessageConverter httpMessageConverter = new FormHttpMessageConverter();

        return new HttpMessageConverters(fastjson,httpMessageConverter);
    }



}
