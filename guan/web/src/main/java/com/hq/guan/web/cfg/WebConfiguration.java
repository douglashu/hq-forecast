package com.hq.guan.web.cfg;

import com.hq.scrati.common.filter.HQCharacterEncodingFilter;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

/**
 * Created by zhaoyang on 12/01/2017.
 */
@Configuration
public class WebConfiguration {

    @Bean
    public FilterRegistrationBean webFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        HQCharacterEncodingFilter characterEncodingFilter = new HQCharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.addUrlPattern("/tfb_trade_orders", "GBK");
        registration.setFilter(characterEncodingFilter);
        return registration;
    }

    @Bean
    public HttpMessageConverters messageConverters() {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        // FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FormHttpMessageConverter httpMessageConverter = new FormHttpMessageConverter();
        HttpMessageConverters converters = new HttpMessageConverters(stringHttpMessageConverter, httpMessageConverter);
        return converters;
    }

}
