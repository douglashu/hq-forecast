package com.hq.scrati.rpcex.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hq.scrati.rpcex.DispatcherServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;


/**
 * Created by Zale on 16/6/29.
 */
@Configuration
@ImportResource("classpath:spring-mvc.xml")
public class WebConfiguration {
    @Autowired
    private ApplicationContext context;
    @Bean
    public FilterRegistrationBean webFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        registration.setFilter(characterEncodingFilter);
        return registration;
    }
    @Bean
    public ServletRegistrationBean webServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new DispatcherServlet(context),"/svr/*");
        return servletRegistrationBean;
    }
    @Bean
    public HttpMessageConverters messageConverters(){
        FastJsonHttpMessageConverter fastjson = new FastJsonHttpMessageConverter();
        FormHttpMessageConverter httpMessageConverter = new FormHttpMessageConverter();

        return new HttpMessageConverters(fastjson,httpMessageConverter);
    }



}
