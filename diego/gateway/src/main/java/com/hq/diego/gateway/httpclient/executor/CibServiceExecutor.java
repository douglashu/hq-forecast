package com.hq.diego.gateway.httpclient.executor;

import com.alibaba.fastjson.JSON;
import com.hq.diego.gateway.common.XStreamUtil;
import com.hq.diego.gateway.constant.config.GatewayConfig;
import com.hq.diego.model.req.cib.CibCommonReq;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.UrlParamUtil;
import com.hq.scrati.common.util.signature.MD5Util;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhaoyang on 02/03/2017.
 */
@Service
public class CibServiceExecutor {

    private static final Logger logger = Logger.getLogger(CibServiceExecutor.class);

    private static final String GATEWAY_URL = "https://pay.swiftpass.cn/pay/gateway";

    @Autowired
    private HttpClientProvider provider;

    public <T> T execute(CibCommonReq cibCommonReq, Class<T> clazz, String key) {
        if(cibCommonReq == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        if(clazz == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "clazz不能为空");
        }
        if (!StringUtils.isEmpty(GatewayConfig.CIB_GROUP_NO)
                && !StringUtils.isEmpty(GatewayConfig.CIB_API_SECRET_KEY)) {
            cibCommonReq.setGroupno(GatewayConfig.CIB_GROUP_NO);
            key = GatewayConfig.CIB_API_SECRET_KEY;
        }
        cibCommonReq.setCharset("UTF-8");
        cibCommonReq.setSign_type("MD5");
        cibCommonReq.setNonce_str(UUID.randomUUID().toString().replaceAll("-", ""));
        Map<String, Object> params = (Map<String, Object>) JSON.toJSON(cibCommonReq);
        cibCommonReq.setSign(genSignStr(params, key));
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        try {
            httpPost = this.provider.getHttpPost(GATEWAY_URL);
            String xmlStr = XStreamUtil.toXml(cibCommonReq);
            logger.warn("<[CIB REQUEST XML STRING]:\r\n" + xmlStr);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/xml");
            httpPost.setEntity(new StringEntity(xmlStr, Charset.forName("UTF-8")));
            response = this.provider.getHttpClient().execute(httpPost);
            if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                String respXmlStr = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                logger.warn("<[CIB RESPONSE XML STRING]:\r\n" + respXmlStr);
                return XStreamUtil.fromXml(respXmlStr, clazz);
            } else {
                throw new CommonException(CommonErrCode.NETWORK_ERROR
                        , "调用兴业支付服务出错[StatusCode=" + response.getStatusLine().getStatusCode() + "]");
            }
        } catch (CommonException ce) {
            throw ce;
        } catch (Throwable th) {
            logger.warn("<<<<<< Call Cib Service(" + GATEWAY_URL + ") Fail", th);
            throw new CommonException(CommonErrCode.NETWORK_ERROR, "调用兴业支付服务出错", th);
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
