package com.hq.crash.web.cfg;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hq.crash.web.filter.SecurityFilter;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Zale on 16/6/29.
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public FilterRegistrationBean webFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        registration.setFilter(characterEncodingFilter);
        return registration;
    }

    @Bean
    public HttpMessageConverters messageConverters(){
        FastJsonHttpMessageConverter fastjson = new FastJsonHttpMessageConverter();
        FormHttpMessageConverter httpMessageConverter = new FormHttpMessageConverter();
        return new HttpMessageConverters(fastjson,httpMessageConverter);
    }

    @Bean
    public SecurityFilter securityFilter() {
        return new SecurityFilter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final String[] excludePathPatterns = new String[] {
            "/sessions", "/passcode", "/pwd", "/ping"
        };
        registry.addInterceptor(securityFilter())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);
        super.addInterceptors(registry);
    }

}
