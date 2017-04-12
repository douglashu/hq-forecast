package com.hq.guan.web.controller.callback;

import com.alibaba.fastjson.JSON;
import com.hq.guan.service.callback.TfbCallbackService;
import com.hq.guan.web.controller.common.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyang on 16/03/2017.
 */
@RestController
public class TfbCallbackController extends BaseController {

    private static final Logger logger = Logger.getLogger(TfbCallbackController.class);

    @Autowired
    private TfbCallbackService callbackService;

    @RequestMapping(value = "/tfb_trade_orders", method = RequestMethod.POST)
    public ResponseEntity<String> onCallback(HttpServletRequest request) throws UnsupportedEncodingException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "text/plain;charset=utf-8");
        // Check Param
        Map<String, String[]> params = request.getParameterMap();
        if(params.size() == 0) {
            return new ResponseEntity<>("PARAM_ERROR", responseHeaders, HttpStatus.OK);
        }
        // Convert to json map
        Map<String, String> jsonMap = new HashMap<>();
        for(Map.Entry<String, String[]> entry: params.entrySet()) {
            if(entry.getValue() != null && entry.getValue().length > 0) {
                jsonMap.put(entry.getKey(), new String(entry.getValue()[0].getBytes("iso-8859-1"),"GBK")
);
            }
        }
        String respStr = this.callbackService.processTfbCallback(JSON.toJSONString(jsonMap));
        return new ResponseEntity<>(respStr, responseHeaders, HttpStatus.OK);
    }

}
