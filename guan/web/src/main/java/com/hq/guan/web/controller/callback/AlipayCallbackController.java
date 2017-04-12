package com.hq.guan.web.controller.callback;

import com.alibaba.fastjson.JSON;
import com.hq.guan.service.callback.AlipayCallbackService;
import com.hq.guan.web.controller.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyang on 26/01/2017.
 */
@RestController
public class AlipayCallbackController extends BaseController {

    @Autowired
    private AlipayCallbackService alipayCallbackService;

    @RequestMapping(value = "/ali_trade_orders", method = RequestMethod.POST)
    public ResponseEntity<String> onCallback(HttpServletRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "text/plain;charset=utf-8");
        // Check Param
        Map<String, String[]> params = request.getParameterMap();
        if(params.size() == 0) {
            return new ResponseEntity<>("param_error", responseHeaders, HttpStatus.OK);
        }
        // Convert to json map
        Map<String, String> jsonMap = new HashMap<>();
        for(Map.Entry<String, String[]> entry: params.entrySet()) {
            if(entry.getValue() != null && entry.getValue().length > 0) {
                jsonMap.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        String respStr = this.alipayCallbackService
                .processAliCallback(JSON.toJSONString(jsonMap));
        return new ResponseEntity<>(respStr, responseHeaders, HttpStatus.OK);
    }

}
