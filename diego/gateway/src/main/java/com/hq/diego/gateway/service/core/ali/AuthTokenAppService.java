package com.hq.diego.gateway.service.core.ali;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.hq.diego.gateway.constant.config.GatewayConfig;
import com.hq.diego.gateway.constant.url.AliPayUrl;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyang on 02/12/2016.
 */
public class AuthTokenAppService {

    public static final String GRANT_TYPE_AUTH_CODE = "authorization_code";
    public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    public static void main(String[] args) {
        String url = AliPayUrl.getAuthUrl(
                GatewayConfig.ALIPAY_APP_ID, "http://www.scrati.com.cn");
        System.out.println(url);
    }

    /*
        value: 授权码或者RefreshToken
     */
    public void getAppOpenAuthToken(String grantType, String value) {
        if(StringUtils.isEmpty(grantType)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "授权类型[grantType]不能为空");
        }
        if(!GRANT_TYPE_AUTH_CODE.equals(grantType)
                && !GRANT_TYPE_REFRESH_TOKEN.equals(grantType)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "授权类型[grantType=" + grantType + "]错误");
        }
        if(StringUtils.isEmpty(value)) {
            String str = GRANT_TYPE_AUTH_CODE.equals(grantType)?"授权码": "刷新令牌";
            throw new CommonException(CommonErrCode.ARGS_INVALID, (str + "不能为空"));
        }
        try {
            DateTime now = DateTime.now();
            Map<String, String> reqParams = new HashMap<>();
            reqParams.put("grant_type", grantType);
            if(GRANT_TYPE_AUTH_CODE.equals(grantType)) {
                reqParams.put("code", value);
            } else {
                reqParams.put("refresh_token", value);
            }
            AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
            request.setBizContent(JSON.toJSONString(reqParams));
            AlipayClient alipayClient = new DefaultAlipayClient(AliPayUrl.getGatewayUrl(), GatewayConfig.ALIPAY_APP_ID
                    , GatewayConfig.ALIPAY_APP_PKCS8_PRIVATE_KEY, "JSON", "utf-8", GatewayConfig.ALIPAY_PKCS8_PUBLIC_KEY);
            AlipayOpenAuthTokenAppResponse response = alipayClient.execute(request);
            if("10000".equals(response.getCode())) {
                // 商户授权令牌
                System.out.println(response.getAppAuthToken());
                // 授权商户的ID
                System.out.println(response.getUserId());
                // 授权商户的AppId
                System.out.println(response.getAuthAppId());
                // 令牌的有效期，单位秒
                System.out.println(response.getExpiresIn());
                // 刷新令牌有效期，单位秒
                System.out.println(response.getReExpiresIn());
                // 刷新令牌时使用
                System.out.println(response.getAppRefreshToken());




                DateTime tokenExpiresIn = now.plusSeconds(Integer.valueOf(response.getExpiresIn()));
                DateTime refreshTokenExpiresIn = now.plusSeconds(Integer.valueOf(response.getReExpiresIn()));
                System.out.println(tokenExpiresIn.toString("YYYYMMddHHmmss"));
                System.out.println(refreshTokenExpiresIn.toString("YYYYMMddHHmmss"));



            } else {
                System.out.println(response.getCode());
                System.out.println(response.getSubMsg());
            }
        } catch (AlipayApiException ex) {





            ex.getErrCode();
            ex.getErrMsg();
        }
    }
}
