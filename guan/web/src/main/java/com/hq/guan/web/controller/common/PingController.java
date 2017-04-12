package com.hq.guan.web.controller.common;

import org.joda.time.DateTime;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zhaoyang on 17/01/12.
 */
@RestController
@RequestMapping("/ping")
public class PingController extends BaseController {

    @RequestMapping(path = "test", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> ping(HttpServletRequest request) {
        Map<String, Object> results = new HashMap<>();
        results.put("Name", "Guan");
        results.put("UserAgent", request.getHeader(HttpHeaders.USER_AGENT));
        results.put("ContentType", request.getHeader(HttpHeaders.CONTENT_TYPE));
        results.put("DateTime", DateTime.now().toString("YYYY-MM-dd HH:mm:ss"));
        return getJsonResp(results, HttpStatus.OK);
    }

}
