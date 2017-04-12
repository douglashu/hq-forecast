package com.hq.crash.web.controller.common;

import com.hq.crash.model.auth.UserSession;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.CommonErrCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by young on 6/11/15.
 */
@RestController
public class BaseController {

    public static final String USER_SESSION_KEY = "USER:SESSION";

    public <T> ResponseEntity<T> getJsonResp(T body, HttpStatus statusCode) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<>(body, responseHeaders, statusCode);
    }

    public ResponseEntity<RespEntity> getMessageResp(String message) {
        RespEntity respEntity = new RespEntity();
        respEntity.setKey(CommonErrCode.NONE.getCode());
        respEntity.setMsg(message);
        return getJsonResp(respEntity, HttpStatus.OK);
    }

    public UserSession getUserSession(HttpServletRequest request) {
        Object obj = request.getAttribute(USER_SESSION_KEY);
        if(obj != null && obj instanceof UserSession) {
            return (UserSession) obj;
        }
        return null;
    }

}
