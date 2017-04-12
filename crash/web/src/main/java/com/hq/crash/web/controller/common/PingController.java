package com.hq.crash.web.controller.common;

import com.hq.esc.inf.entity.RespEntity;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyang on 14/01/2017.
 */
@RestController
public class PingController extends BaseController {

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> ping() {
        Map<String, Object> results = new HashMap<>();
        results.put("Name", "Crash");
        results.put("DateTime", DateTime.now().toString("YYYY-MM-dd HH:mm:ss"));
        return getJsonResp(results, HttpStatus.OK);
    }

}
