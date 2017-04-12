package com.hq.guan.web.controller.auth;

import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.MapBuilder;
import com.hq.guan.service.auth.AliAppAuthService;
import com.hq.guan.service.constant.GuanThirdConfig;
import com.hq.guan.web.controller.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by zhaoyang on 16/01/2017.
 */
@RestController
public class AliAppAuthController extends BaseController {

    private static final String APP_AUTH_URL = "https://openauth.alipay.com/oauth2/appToAppAuth.htm";
    private static final String APP_AUTH_CALLBACK_URL = "http://m.hqast.com/guan/ali_auth_callback";
    private static final String APP_AUTH_SUCCESS_URL = "http://m.hqast.com/guan/dist/auth/index.html";
    private static final String APP_AUTH_FAIL_URL = "http://m.hqast.com/guan/dist/auth_fail/index.html";

    @Autowired
    private AliAppAuthService appAuthService;

    @RequestMapping(value = "/ali_auth_url", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getAppAuthUrl(@RequestParam Integer coreId) {
        try {
            StringBuilder builder = new StringBuilder(APP_AUTH_URL);
            builder.append("?app_id=").append(GuanThirdConfig.ALI_APP_ID);
            builder.append("&redirect_uri=").append(
                    URLEncoder.encode(APP_AUTH_CALLBACK_URL + "?coreId=" + coreId, "UTF-8"));
            return getJsonResp(MapBuilder.create("appAuthUrl", builder.toString()).get(), HttpStatus.OK);
        } catch (Throwable th) {
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "获取支付宝应用授权URL失败", th);
        }
    }

    @RequestMapping(value = "/ali_auth_callback", method = RequestMethod.GET)
    public void onAliAppAuthCallback(@RequestParam String app_id
            , @RequestParam String app_auth_code, @RequestParam Integer coreId,  HttpServletResponse response) {
        try {
            this.appAuthService.doAppAuth(app_id, app_auth_code, coreId);
        } catch (Throwable th) {
            redirect(APP_AUTH_FAIL_URL, response);
            return;
        }
        redirect(APP_AUTH_SUCCESS_URL, response);
    }

}
