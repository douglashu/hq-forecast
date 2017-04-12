package com.hq.diego.gateway.httpclient.executor;

import com.alibaba.fastjson.JSON;
import com.hq.diego.gateway.common.XStreamUtil;
import com.hq.diego.model.req.tfb.TfbCommonReq;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.UrlParamUtil;
import com.hq.scrati.common.util.signature.MD5Util;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyang on 13/03/2017.
 */
@Service
public class TfbServiceExecutor {

    private static final Logger logger = Logger.getLogger(TfbServiceExecutor.class);

    private static final String GATEWAY_URL = "http://upay.tfb8.com/cgi-bin/v2.0";

    private static final String GATEWAY_URL_TEST = "http://apitest.tfb8.com/cgi-bin/v2.0";

    @Autowired
    private HttpClientProvider provider = new HttpClientProvider();

    public <T> T execute(TfbCommonReq tfbCommonReq, Class<T> clazz, String key) {
        if(tfbCommonReq == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        if(StringUtils.isEmpty(tfbCommonReq.getReqUrl())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "reqUrl不能为空");
        }
        if(clazz == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "clazz不能为空");
        }
        Map<String, Object> params = (Map<String, Object>) JSON.toJSON(tfbCommonReq);
        params.remove("reqUrl");
        tfbCommonReq.setSign_type("MD5");
        tfbCommonReq.setVer("1");
        tfbCommonReq.setInput_charset("UTF-8");
        tfbCommonReq.setSign_key_index(1);
        tfbCommonReq.setSign(genSignStr(params, key));
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        try {
            params = (Map<String, Object>) JSON.toJSON(tfbCommonReq);
            params.remove("reqUrl");
            httpGet = this.provider.getHttpGet((GATEWAY_URL_TEST + tfbCommonReq.getReqUrl()), params);
            logger.warn("<[TFB REQUEST URL]:\r\n" + httpGet.getURI());
            response = this.provider.getHttpClient().execute(httpGet);
            if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                String respXmlStr = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                logger.warn("<[TFB RESPONSE XML STRING]:\r\n" + respXmlStr);
                return XStreamUtil.fromXml(respXmlStr, clazz);
            } else {
                throw new CommonException(CommonErrCode.NETWORK_ERROR
                        , "调用天付宝支付服务出错[StatusCode=" + response.getStatusLine().getStatusCode() + "]");
            }
        } catch (CommonException ce) {
            throw ce;
        } catch (Throwable th) {
            logger.warn("<<<<<< Call Tfb Service(" + GATEWAY_URL_TEST + ") Fail", th);
            throw new CommonException(CommonErrCode.NETWORK_ERROR, "调用天付宝支支付服务出错", th);
        } finally {
            if(httpGet != null) {
                try {
                    httpGet.releaseConnection();
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
