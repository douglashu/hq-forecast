package com.hq.diego.gateway.httpclient.executor;

import com.alibaba.fastjson.JSON;
import com.hq.diego.gateway.common.XStreamUtil;
import com.hq.diego.gateway.constant.config.GatewayConfig;
import com.hq.scrati.common.exception.CommonException;
import com.hq.diego.model.req.wx.WxCommonReq;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.util.UrlParamUtil;
import com.hq.scrati.common.util.signature.MD5Util;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhaoyang on 08/10/2016.
 */
@Service("WxServiceExecutor")
public class WxServiceExecutor {

    private static final Logger logger = Logger.getLogger(WxServiceExecutor.class);

    private CloseableHttpClient httpClient;
    private PoolingHttpClientConnectionManager connManager;

    @Value("${wx.api.client.cert.path}")
    private String wxApiClientCertPath;

    @PostConstruct
    public void init() {
        try {
            File file = new File(this.wxApiClientCertPath);
            KeyStore trustStore = KeyStore.getInstance("PKCS12");
            trustStore.load(new FileInputStream(file), GatewayConfig.WX_API_CLIENT_CERT_PASSWORD.toCharArray());
            SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(
                    trustStore, GatewayConfig.WX_API_CLIENT_CERT_PASSWORD.toCharArray()).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> socketFactoryRegistry =
                    RegistryBuilder.<ConnectionSocketFactory>create().register("https", sslsf).build();
            this.connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            this.connManager.setMaxTotal(500);
            this.connManager.setDefaultMaxPerRoute(500);
            this.httpClient = HttpClients.custom()
                    .setConnectionManager(this.connManager).build();
        } catch (Throwable th) {
            logger.error("<<<<<< Init Wx Http Executor On Error", th);
        }
    }

    public <T> T execute(String wxURL, WxCommonReq wxReq, Class<T> clazz) {
        if(StringUtils.isEmpty(wxURL)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "请求URL不能为空");
        }
        if(wxReq == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        if(clazz == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "clazz不能为空");
        }
        wxReq.setAppid(GatewayConfig.WX_APP_ID);
        wxReq.setMch_id(GatewayConfig.WX_MERCHANT_NO);
        wxReq.setNonce_str(UUID.randomUUID().toString().replaceAll("-", ""));
        Map<String, Object> params = (Map<String, Object>) JSON.toJSON(wxReq);
        wxReq.setSign(genSignStr(params, GatewayConfig.WX_API_SECRET_KEY));
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        try {
            httpPost = new HttpPost(wxURL);
            httpPost.setConfig(RequestConfig.custom()
                    .setConnectionRequestTimeout(5000)
                    .setConnectTimeout(5000)
                    .setSocketTimeout(20000).build()
            );
            String xmlStr = XStreamUtil.toXml(wxReq);
            logger.warn("<[WX REQUEST XML STRING]:\r\n" + xmlStr);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/xml");
            httpPost.setEntity(new StringEntity(xmlStr, Charset.forName("UTF-8")));
            response = this.httpClient.execute(httpPost);
            if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                String respXmlStr = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                logger.warn("<[WX RESPONSE XML STRING]:\r\n" + respXmlStr);
                return XStreamUtil.fromXml(respXmlStr, clazz);
            } else {
                throw new CommonException(CommonErrCode.NETWORK_ERROR
                        , "调用微信服务出错[StatusCode=" + response.getStatusLine().getStatusCode() + "]");
            }
        } catch (CommonException ce) {
            throw ce;
        } catch (Throwable th) {
            logger.warn("<<<<<< Call Wechat Service(" + wxURL + ") Fail", th);
            throw new CommonException(CommonErrCode.NETWORK_ERROR, "调用微信服务出错", th);
        } finally {
            if(httpPost != null) {
                try {
                    httpPost.releaseConnection();
                } catch (Throwable th) {
                }
            }
            if(response != null) {
                try {
                    response.close();
                } catch (Throwable th) {
                }
            }
        }
    }

    public static String genSignStr(Map<String, Object> params, String key) {
        List<String> emptyKeys = new ArrayList<>();
        for(String k: params.keySet()) {
            if("".equals(params.get(k))) emptyKeys.add(k);
        }
        for(String k: emptyKeys) params.remove(k);
        String signStr = UrlParamUtil.createLinkString(params);
        signStr += ("&key=" + key);
        return MD5Util.MD5Encode(signStr, "UTF-8").toUpperCase();
    }
}
