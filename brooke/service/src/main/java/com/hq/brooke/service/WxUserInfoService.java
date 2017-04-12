package com.hq.brooke.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hq.brooke.model.WxAccessToken;
import com.hq.brooke.model.WxOpenId;
import com.hq.brooke.model.WxUserInfo;
import com.hq.brooke.model.constant.WxUrl;
import com.hq.brooke.service.common.WxCommonService;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;

/**
 * Created by zhaoyang on 20/01/2017.
 */
@Service
public class WxUserInfoService extends WxCommonService {

    @Autowired
    private WxAccessTokenService wxAccessTokenService;

    public WxUserInfo getUserInfoFromTServer(String appId, String appSecret, String openId) {
        if(StringUtils.isEmpty(appId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用ID不能为空[tAppId]");
        }
        if(StringUtils.isEmpty(appSecret)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用密钥不能为空[tAppSecret]");
        }
        if(StringUtils.isEmpty(openId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "用户openId不能为空[openId]");
        }

        WxAccessToken accessToken = this.wxAccessTokenService.getAccessToken(appId, appSecret);
        if(accessToken == null) {
            throw new CommonException(CommonErrCode.BUSINESS, "获取AccessToken失败");
        }

        final String url = WxUrl.getUserInfoUrl(accessToken.getToken(), openId);
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        try {
            httpGet = new HttpGet(url);
            response = HttpClients.createDefault().execute(httpGet);
            if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                String respJsonStr = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                return JSON.parseObject(respJsonStr, WxUserInfo.class);
            } else {
                throwExceptionWithStatusCode(response.getStatusLine().getStatusCode());
            }
        } catch (Throwable th) {
            if(th instanceof CommonException) throw (CommonException) th;
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "获取微信用户信息失败", th);
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
