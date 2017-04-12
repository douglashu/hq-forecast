package com.hq.crash.web.controller.auth;

import com.hq.crash.model.auth.UserSession;
import com.hq.crash.model.auth.req.LoginReq;
import com.hq.crash.service.auth.LoginService;
import com.hq.crash.web.controller.common.BaseController;
import com.hq.esc.inf.entity.RespEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Cwreated by zhaoyang on 13/01/2017.
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/sessions", method = RequestMethod.POST)
    public ResponseEntity<UserSession> login(@RequestBody LoginReq loginReq) {
        UserSession session = this.loginService.login(loginReq);
        return getJsonResp(session, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public ResponseEntity<UserSession> getSession(HttpServletRequest request) {
        UserSession session = getUserSession(request);
        return getJsonResp(session, HttpStatus.OK);
    }

    @RequestMapping(value = "/session", method = RequestMethod.DELETE)
    public ResponseEntity<RespEntity> logout(HttpServletRequest request) {
        UserSession session = getUserSession(request);
        this.loginService.logout(session.getAppId(), session.getUserId());
        return getMessageResp("您的账号已退出");
    }

}
