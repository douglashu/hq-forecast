package com.hq.diego.gateway.httpclient.executor;

import com.hq.scrati.common.util.UrlParamUtil;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by zhaoyang on 13/03/2017.
 */
@Service
public class HttpClientProvider {

    private static final Logger logger = Logger.getLogger(HttpClientProvider.class);

    private CloseableHttpClient httpClient;
    private PoolingHttpClientConnectionManager connManager;

    public HttpClientProvider() {
        try {
            this.connManager = new PoolingHttpClientConnectionManager();
            this.connManager.setMaxTotal(500);
            this.connManager.setDefaultMaxPerRoute(500);
            this.httpClient = HttpClients.custom()
                    .setConnectionManager(this.connManager).build();
        } catch (Throwable th) {
            logger.error("<<<<<< Init Http Executor On Error", th);
        }
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public HttpGet getHttpGet(String url, Map<String, Object> params) {
        String queryStr = UrlParamUtil.createLinkString(params);
        if (!StringUtils.isEmpty(queryStr)) {
            queryStr = "?" + queryStr;
        }
        HttpGet httpGet = new HttpGet(url + queryStr);
        httpGet.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(5000)
                .setSocketTimeout(20000).build()
        );
        return httpGet;
    }

    public HttpPost getHttpPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(5000)
                .setSocketTimeout(20000).build()
        );
        return httpPost;
    }
}
