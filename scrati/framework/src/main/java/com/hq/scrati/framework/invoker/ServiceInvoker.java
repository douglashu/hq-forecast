package com.hq.scrati.framework.invoker;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.IdleConnectionCleanThread;
import com.hq.scrati.model.HqRequest;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhaoyang on 05/01/2017.
 */
public class ServiceInvoker {

    private static final Logger logger = Logger.getLogger(ServiceInvoker.class);

    private static final String REQUEST_PARAM_KEY = "request";

    private CloseableHttpClient client;
    private PoolingHttpClientConnectionManager connManager;

    private static ServiceInvoker invoker = null;
    private static final Object SyncObject = new Object();

    public static ServiceInvoker newInstance() {
        if (invoker != null) return invoker;
        synchronized (SyncObject) {
            if (invoker != null) return invoker;
            invoker = new ServiceInvoker();
        }
        return invoker;
    }

    private ServiceInvoker() {
        this.connManager = new PoolingHttpClientConnectionManager();
        this.connManager.setMaxTotal(1200);
        this.connManager.setDefaultMaxPerRoute(1200);
        this.client = HttpClients.custom().setConnectionManager(this.connManager).build();
        new IdleConnectionCleanThread(this.connManager);
        logger.info("<<< Service Invoker Http Client Initialized");
    }

    public <T> RespEntity<T> invoke(String escUrl
            , String service, String version, HqRequest hqRequest, Class<T> clazz) {
        if(StringUtils.isEmpty(escUrl)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "ESC地址不能为空[escUrl]");
        }
        if(StringUtils.isEmpty(service)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "服务名称不能为空[service]");
        }
        if(StringUtils.isEmpty(version)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "服务版本号不能为空[version]");
        }
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        RespEntity<T> respEntity;
        try {
            StringBuilder urlBuilder = new StringBuilder(escUrl);
            urlBuilder.append("/").append(service).append("/").append(version).append("/");
            httpPost = new HttpPost(urlBuilder.toString());
            httpPost.setConfig(RequestConfig.custom()
                    .setConnectionRequestTimeout(5000)
                    .setConnectTimeout(5000)
                    .setSocketTimeout(20000).build());
            if(hqRequest != null) {
                List<BasicNameValuePair> formParams = Arrays
                        .asList(new BasicNameValuePair(REQUEST_PARAM_KEY, JSON.toJSONString(hqRequest)));
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
                httpPost.setEntity(formEntity);
            }
            response = this.client.execute(httpPost);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(response.getEntity()));
                respEntity = new RespEntity<>();
                respEntity.setKey(jsonObject.getString("key"));
                respEntity.setMsg(jsonObject.getString("msg"));
                respEntity.setHost(jsonObject.getString("host"));
                if(jsonObject.containsKey("ext") && clazz != null) {
                    if(clazz.equals(String.class)) {
                        respEntity.setExt((T)jsonObject.getString("ext"));
                    } else if (clazz.equals(Boolean.class)) {
                        respEntity.setExt((T)jsonObject.getBoolean("ext"));
                    } else if(clazz.equals(JSONObject.class)) {
                        respEntity.setExt((T)jsonObject.getJSONObject("ext"));
                    } else if(clazz.equals(JSONArray.class)) {
                        respEntity.setExt((T)jsonObject.getJSONArray("ext"));
                    } else {
                        respEntity.setExt(JSON.toJavaObject(jsonObject.getJSONObject("ext"), clazz));
                    }
                }
            } else {
                respEntity = new RespEntity(CommonErrCode.NETWORK_ERROR.getCode()
                        , ("Http错误码(" + response.getStatusLine().getStatusCode() + ")"));
            }
        } catch (Throwable th) {
            logger.warn("<<< Invoke Service Fail(service=" + service + ", version=" + version + ")", th);
            respEntity = new RespEntity(
                    CommonErrCode.SERVICE_INVOKE_ERROR.getCode(),
                    CommonErrCode.SERVICE_INVOKE_ERROR.getDesc(), th);
        }

        try {
            if (httpPost != null) httpPost.releaseConnection();
        } catch (Throwable th) {
            logger.warn("<<< Release Connection Fail", th);
        }
        try {
            if (response != null) response.close();
        } catch (Throwable th) {
            logger.warn("<<< Close response Fail", th);
        }

        return respEntity;
    }
}
