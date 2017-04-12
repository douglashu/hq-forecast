package com.hq.crash.web.filter;

import com.hq.crash.model.auth.UserSession;
import com.hq.crash.service.auth.AuthService;
import com.hq.crash.web.controller.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhaoyang on 14/01/2017.
 */
public class SecurityFilter extends HandlerInterceptorAdapter {

    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String appId = request.getHeader("AID");
        final String userId = request.getHeader("UID");
        final String token = request.getHeader("TOKEN");
        UserSession session = this.authService.doAuth(appId, userId, token);
        request.setAttribute(BaseController.USER_SESSION_KEY, session);
        return super.preHandle(request, response, handler);
    }

}
