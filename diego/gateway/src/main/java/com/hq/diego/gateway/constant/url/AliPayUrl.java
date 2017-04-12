package com.hq.diego.gateway.constant.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by zhaoyang on 26/11/2016.
 */
public class AliPayUrl {

    private static final Boolean SANDBOX_MODE = Boolean.FALSE;

    // 网关
    private static final String GATEWAY = "https://openapi.alipay.com/gateway.do";
    private static final String SANDBOX_GATEWAY = "https://openapi.alipaydev.com/gateway.do";
    public static String getGatewayUrl() {
        return SANDBOX_MODE? SANDBOX_GATEWAY: GATEWAY;
    }

    // 第三方授权URL
    private static final String AUTH_URL = "https://openauth.alipay.com/oauth2/appToAppAuth.htm";
    private static final String SANDBOX_AUTH_URL = "https://openauth.alipaydev.com/oauth2/appToAppAuth.htm";
    public static String getAuthUrl(String appId, String redirectUrl) {
        String encodedUrl;
        try {
            encodedUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException ue) {
            encodedUrl = redirectUrl;
        }
        return (SANDBOX_MODE? SANDBOX_AUTH_URL: AUTH_URL)
                + "?app_id=" + appId + "&redirect_uri=" + encodedUrl;
    }

}

