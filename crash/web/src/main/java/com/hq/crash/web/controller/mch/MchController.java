package com.hq.crash.web.controller.mch;

import com.hq.crash.service.merc.MchService;
import com.hq.crash.web.controller.common.BaseController;
import com.hq.sid.service.entity.response.MercResp;
import com.hq.sid.service.entity.response.QrCodeRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyang on 11/02/2017.
 */
@RestController
public class MchController extends BaseController {

    @Autowired
    private MchService mchService;

    @RequestMapping(value = "/mch", method = RequestMethod.GET)
    public ResponseEntity getMchInfo(HttpServletRequest request) {
        MercResp mercResp = this.mchService.getMchInfo(getUserSession(request));
        return getJsonResp(mercResp, HttpStatus.OK);
    }

    @RequestMapping(value = "/store_codes", method = RequestMethod.GET)
    public ResponseEntity<List<QrCodeRsp>> getStoreCodes(HttpServletRequest request) {
        List<QrCodeRsp> codes = this.mchService.getStoreCodes(getUserSession(request));
        return getJsonResp(codes, HttpStatus.OK);
    }

    @RequestMapping(value = "/prod_signs", method = RequestMethod.GET)
    public ResponseEntity getProdSigns(HttpServletRequest request) {
        List<Map<String, Object>> prods = this.mchService
                .getMchRatesInfo(getUserSession(request));
        return getJsonResp(prods, HttpStatus.OK);
    }

}
