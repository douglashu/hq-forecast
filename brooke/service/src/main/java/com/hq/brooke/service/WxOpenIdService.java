package com.hq.brooke.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hq.brooke.model.WxOpenId;
import com.hq.brooke.model.constant.WxUrl;
import com.hq.brooke.service.common.WxCommonService;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.signature.MD5Util;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.nio.charset.Charset;

/**
 * Created by zhaoyang on 05/01/2017.
 */
@Service
public class WxOpenIdService extends WxCommonService {

    public WxOpenId getUserOpenIdFromTServer(String appId, String appSecret, String authCode) {
        if(StringUtils.isEmpty(appId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用ID不能为空[tAppId]");
        }
        if(StringUtils.isEmpty(appSecret)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用密钥不能为空[tAppSecret]");
        }
        if(StringUtils.isEmpty(authCode)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "用户授权码不能为空[authCode]");
        }
        final String url = WxUrl.getUserOpenIdUrl(appId, appSecret, authCode);
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        try {
            httpGet = new HttpGet(url);
            response = HttpClients.createDefault().execute(httpGet);
            if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                String respJsonStr = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                JSONObject jsonObject = JSON.parseObject(respJsonStr);
                if(jsonObject.containsKey("openid")) {
                    DateTime now = DateTime.now();
                    WxOpenId wxOpenId = new WxOpenId();
                    Integer expiresIn = jsonObject.getInteger("expires_in");
                    wxOpenId.setAppId(appId);
                    wxOpenId.setOpenId(jsonObject.getString("openid"));
                    wxOpenId.setAccessToken(jsonObject.getString("access_token"));
                    wxOpenId.setRefreshToken(jsonObject.getString("refresh_token"));
                    wxOpenId.setExpireInSeconds(expiresIn);
                    wxOpenId.setScope(jsonObject.getString("scope"));
                    wxOpenId.setExpireTime(now.plusSeconds(expiresIn).getMillis());
                    wxOpenId.setCreateTime(now.getMillis());
                    return wxOpenId;
                } else {
                    throwExceptionWithJsonObj(jsonObject);
                }
            } else {
                throwExceptionWithStatusCode(response.getStatusLine().getStatusCode());
            }
        } catch (Throwable th) {
            if(th instanceof CommonException) throw (CommonException) th;
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "获取JsApiTicket失败", th);
        } finally {
            if(httpGet != null) {
                try { httpGet.releaseConnection(); } catch (Throwable th) { }
            }
            if(response != null) {
                try { response.close(); } catch (Throwable th) { }
            }
        }
        return null;
    }

}
