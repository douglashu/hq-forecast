package com.hq.buck.web.controller;

import com.hq.esc.inf.entity.RespEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaoyang on 18/12/2016.
 */
@RestController
public class BaseController {
    public <T> ResponseEntity<T> getJsonResp(T body, HttpStatus statusCode) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<T>(body, responseHeaders, statusCode);
    }

    public ResponseEntity<String> getSucceedStringResp() {
        return getStringResp("success", HttpStatus.OK);
    }

    public ResponseEntity<String> getStringResp(String body, HttpStatus statusCode) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "text/plain;charset=utf-8");
        return new ResponseEntity<String>(body, responseHeaders, statusCode);
    }

    public ResponseEntity<RespEntity> getSucceedJsonResp(String msg) {
        return getJsonResp(new RespEntity("success", msg), HttpStatus.OK);
    }
}
