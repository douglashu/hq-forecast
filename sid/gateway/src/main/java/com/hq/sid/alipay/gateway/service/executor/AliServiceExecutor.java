package com.hq.sid.alipay.gateway.service.executor;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.hq.sid.alipay.gateway.service.constant.config.GatewayConfig;
import com.hq.sid.alipay.gateway.service.constant.url.AliUrl;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 05/12/2016.
 */
@Service("Sid-AliServiceExecutor")
public class AliServiceExecutor {

    private AlipayClient client;

    public AliServiceExecutor() {
        this.client = new DefaultAlipayClient(
                AliUrl.getGatewayUrl(), GatewayConfig.ALIPAY_APP_ID,
                GatewayConfig.ALIPAY_APP_PKCS8_PRIVATE_KEY, "JSON", "utf-8",
                GatewayConfig.ALIPAY_PKCS8_PUBLIC_KEY, "RSA2");
    }

    public AlipayClient getClient() {
        return client;
    }

    public void setClient(AlipayClient client) {
        this.client = client;
    }
}
