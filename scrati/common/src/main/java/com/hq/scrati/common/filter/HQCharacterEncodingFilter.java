package com.hq.scrati.common.filter;

import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zale on 2017/3/21.
 */
public class HQCharacterEncodingFilter extends CharacterEncodingFilter{
    private Map<String,String> urlPattern;

    public HQCharacterEncodingFilter() {
        this.urlPattern = new HashMap<>();
    }
    public void addUrlPattern(String urlPattern,String encoding){
        this.urlPattern.put(urlPattern, encoding);
    }
    public Map<String, String> getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(Map<String, String> urlPattern) {
        this.urlPattern = urlPattern;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(urlPattern.size()==0||!urlPattern.containsKey(request.getServletPath())) {
            super.doFilterInternal(request,response,filterChain);
        }else{
            String encoding = urlPattern.get(request.getServletPath());
            if (encoding != null) {
                if (isForceRequestEncoding() || request.getCharacterEncoding() == null) {
                    request.setCharacterEncoding(encoding);
                }
                if (isForceResponseEncoding()) {
                    response.setCharacterEncoding(encoding);
                }
            }
            filterChain.doFilter(request, response);
        }

    }
}
