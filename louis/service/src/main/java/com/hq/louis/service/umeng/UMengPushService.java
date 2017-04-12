package com.hq.louis.service.umeng;

import com.alibaba.fastjson.JSON;
import com.hq.louis.model.req.Message;
import com.hq.louis.model.PushResult;
import com.hq.louis.model.platform.AppPushInfo;
import com.hq.louis.model.umeng.UMengMsg;
import com.hq.louis.model.umeng.UMengResp;
import com.hq.louis.service.core.PushService;
import com.hq.scrati.common.constants.OSPlatform;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.util.signature.MD5Util;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.nio.charset.Charset;

/**
 * Created by zhaoyang on 21/12/2016.
 */
@Service("UMengPushService")
public class UMengPushService implements PushService {

    private static final Logger logger = Logger.getLogger(UMengPushService.class);

    public static final String MSG_PUSH_URL = "http://msg.umeng.com/api/send";

    private UMengMsgFilter filter = new UMengMsgFilter();

    @Override
    public PushResult push(Message message, AppPushInfo appPushInfo) {
        UMengMsg msg = this.filter.filter(message, appPushInfo);
        PushResult result = new PushResult();
        try {
            OSPlatform osPlatform = OSPlatform.fromString(message.getOsPlatform());
            String secret = OSPlatform.Android.equals(osPlatform)?
                    appPushInfo.getAndroidSecret(): appPushInfo.getIosSecret();
            String jsonstr = JSON.toJSONString(msg);
            String signstr = sign("POST", MSG_PUSH_URL, jsonstr, secret);
            HttpPost httpPost = new HttpPost(MSG_PUSH_URL + "?sign=" + signstr);
            httpPost.setEntity(new StringEntity(jsonstr, Charset.forName("UTF-8")));
            httpPost.setHeader("Content-Type", "application/json");
            CloseableHttpResponse resp = HttpClients.createDefault().execute(httpPost);
            if(200 == resp.getStatusLine().getStatusCode() ||
                    400 == resp.getStatusLine().getStatusCode()) {
                String respJsonstr = EntityUtils.toString(resp.getEntity(), Charset.forName("UTF-8"));
                UMengResp uMengResp = JSON.parseObject(respJsonstr, UMengResp.class);
                if("SUCCESS".equalsIgnoreCase(uMengResp.getRet())) {
                    result.setSuccess(Boolean.TRUE);
                    result.setMsgId(uMengResp.getData().getMsg_id());
                    result.setTaskId(uMengResp.getData().getTask_id());
                } else {
                    result.setSuccess(Boolean.FALSE);
                    if(uMengResp.getData() != null) {
                        String errCode = uMengResp.getData().getError_code();
                        result.setErrCode(CommonErrCode.SERVICE_INVOKE_ERROR.getCode());
                        result.setErrMsg("友盟返回错误码:" + errCode);
                        logger.warn("<<<<<< Push UMeng Msg Fail, Ret Code: " + errCode + ", Push Msg:");
                    } else {
                        result.setErrCode(CommonErrCode.SERVICE_INVOKE_ERROR.getCode());
                        result.setErrMsg("友盟返回数据解析失败");
                        logger.warn("<<<<<< Push UMeng Msg Fail, Resp JSON:");
                        logger.warn(respJsonstr);
                    }
                }
            } else {
                result.setSuccess(Boolean.FALSE);
                result.setErrCode(CommonErrCode.NETWORK_ERROR.getCode());
                result.setErrMsg("友盟返回HTTP状态码: " + resp.getStatusLine().getStatusCode());
                logger.warn("<<<<<< Push UMeng Msg Fail, Http Status Code: "
                        + resp.getStatusLine().getStatusCode() + ", Push Msg:");
                logger.warn(message.toString());
            }
        } catch (Throwable th) {
            result.setSuccess(Boolean.FALSE);
            result.setErrCode(CommonErrCode.INTERNAL_SERVER_ERROR.getCode());
            result.setErrMsg("请求友盟服务发生异常");
            logger.warn("<<<<<< Push UMeng Msg Fail, Push Msg:", th);
            logger.warn(message.toString());
        }
        return result;
    }

    private String sign(String method, String url, String postBody, String secret) {
        String signstr = method + url + postBody + secret;
        return MD5Util.MD5Encode(signstr, "UTF-8").toLowerCase();
    }

}
