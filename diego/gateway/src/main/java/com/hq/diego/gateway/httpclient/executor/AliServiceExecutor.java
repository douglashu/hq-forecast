package com.hq.diego.gateway.httpclient.executor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alipay.api.*;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.hq.diego.gateway.constant.config.GatewayConfig;
import com.hq.diego.gateway.constant.url.AliPayUrl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 05/12/2016.
 */
@Service("AliServiceExecutor")
public class AliServiceExecutor {

    public static void main(String[] args) throws Exception {
        AliServiceExecutor e = new AliServiceExecutor();
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode("b87d8394e1ba4b899ac77494476eUB02");
System.out.println(JSON.toJSONString(request));
        AlipaySystemOauthTokenResponse response = e.execute(request);
System.out.println();
        System.out.println();
        System.out.println();

        System.out.println(response.getBody());
        /*
        AlipayOpenAuthTokenAppQueryRequest request = new AlipayOpenAuthTokenAppQueryRequest();
        request.setBizContent("{" +
                "    \"app_auth_token\":\"201701BB7b5f171915f2494da55c10bd4cac2A33\"" +
                "  }");
        try {
            AlipayOpenAuthTokenAppQueryResponse response = e.getClient().execute(request);
            System.out.println(response.getBody());
        } catch (Throwable th) {
            th.printStackTrace();
        }


        AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
        request.setBizContent("{" +
                "    \"grant_type\":\"refresh_token\"," +
                "    \"refresh_token\":\"201701BB7b5f171915f2494da55c10bd4cac2A33\"," +
                "  }");
        try {
            AlipayOpenAuthTokenAppResponse response = e.getClient().execute(request);
            System.out.println(response.getBody());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        */
    }

    private static final Logger logger = Logger.getLogger(AliServiceExecutor.class);

    private AlipayClient client;

    public AliServiceExecutor() {
        this.client = new DefaultAlipayClient(
                AliPayUrl.getGatewayUrl(), GatewayConfig.ALIPAY_APP_ID,
                GatewayConfig.ALIPAY_APP_PKCS8_PRIVATE_KEY, "JSON", "utf-8",
                GatewayConfig.ALIPAY_PKCS8_PUBLIC_KEY, "RSA2");
    }

    public <T extends AlipayResponse> T execute(AlipayRequest<T> request) throws AlipayApiException {
        return execute(request, null, null);
    }

    public <T extends AlipayResponse> T execute(AlipayRequest<T> request
            , String accessToken, String appAuthToken) throws AlipayApiException {
        logger.warn("<[ALIPAY REQUEST MODEL]:\r\n" + JSON.toJSONString(request.getBizModel()));
        AlipayResponse response = this.client
                .execute(request, accessToken, appAuthToken);
        logger.warn("<[ALIPAY RESPONSE BODY]:\r\n" + response.getBody());
        return (T) response;
    }

}
