package com.hq.guan.web.controller.callback;

import com.hq.guan.service.callback.CibCallbackService;
import com.hq.guan.web.controller.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaoyang on 16/03/2017.
 */
@RestController
public class CibCallbackController extends BaseController {

    @Autowired
    private CibCallbackService callbackService;

    @RequestMapping(value = "/cib_trade_orders", method = RequestMethod.POST)
    public ResponseEntity<String> onCallback(@RequestBody String xmlStr) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "text/plain;charset=utf-8");
        String respStr = this.callbackService.processCibCallback(xmlStr);
        return new ResponseEntity<>(respStr, responseHeaders, HttpStatus.OK);
    }

}
