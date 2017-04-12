package com.hq.guan.web.controller.callback;

import com.hq.guan.service.callback.WxPayCallbackService;
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
 * Created by zhaoyang on 26/01/2017.
 */
@RestController
public class WxPayCallbackController extends BaseController {

    @Autowired
    private WxPayCallbackService callbackService;

    @RequestMapping(value = "/wx_trade_orders", method = RequestMethod.POST)
    public ResponseEntity<String> onCallback(@RequestBody String xmlStr) {
        String wxRespXml = this.callbackService.processWxCallback(xmlStr);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "text/xml;charset=utf-8");
        return new ResponseEntity<>(wxRespXml, responseHeaders, HttpStatus.OK);
    }

}
