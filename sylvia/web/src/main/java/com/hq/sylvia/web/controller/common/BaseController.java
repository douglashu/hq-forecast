package com.hq.sylvia.web.controller.common;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.sylvia.model.WebContainer;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhaoyang on 18/12/2016.
 */
@RestController
public class BaseController {

    private static final Logger logger = Logger.getLogger(BaseController.class);

    public WebContainer getWebContainer(HttpServletRequest request) {
        WebContainer container = new WebContainer();
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT).toLowerCase();
        if (userAgent.contains("micromessenger")) {
            container.setWechat(true);
            container.setContainer("Wechat");
        } else if (userAgent.contains("alipayclient")) {
            container.setAlipay(true);
            container.setContainer("Alipay");
        }
        return container;
    }

    public <T> ResponseEntity<T> getJsonResp(T body, HttpStatus statusCode) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<>(body, responseHeaders, statusCode);
    }

    public ResponseEntity<RespEntity> getMessageResp(String message) {
        RespEntity respEntity = new RespEntity();
        respEntity.setMsg(message);
        return getJsonResp(respEntity, HttpStatus.OK);
    }

    public void redirect(String url, HttpServletResponse response) {
        try {
            response.sendRedirect(url);
        } catch (Throwable th) {
            logger.error("<<<<<< Redirect Url Fail(url=" + url + ")", th);
        }
    }

    public void forward(String url, HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        } catch (Throwable th) {
            logger.error("<<<<<< Forward Url Fail(url=" + url + ")", th);
        }
    }

}
