package com.hq.crash.web.controller.app;

import com.alibaba.fastjson.JSONObject;
import com.hq.crash.service.app.AppService;
import com.hq.crash.web.controller.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhaoyang on 10/02/2017.
 */
@RestController
public class AppController extends BaseController {

    @Autowired
    private AppService appService;

    @RequestMapping(value = "/app_check_version", method = RequestMethod.GET)
    public ResponseEntity appCheckVersion(HttpServletRequest request
            , @RequestParam String appId, @RequestParam String version) {
        JSONObject resp = this.appService.checkVersion(
                appId, version, getUserSession(request));
        return getJsonResp(resp, HttpStatus.OK);
    }

}
