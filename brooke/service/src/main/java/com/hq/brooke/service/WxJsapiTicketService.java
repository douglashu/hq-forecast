package com.hq.brooke.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hq.brooke.model.WxJsapiConfig;
import com.hq.brooke.model.WxAccessToken;
import com.hq.brooke.model.WxJsapiTicket;
import com.hq.brooke.model.constant.WxUrl;
import com.hq.brooke.service.common.WxCommonService;
import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.lock.RedisDistributedLock;
import com.hq.scrati.cache.lock.RedisKeyWatch;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.signature.MD5Util;
import com.hq.scrati.common.util.signature.SHAUtil;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by zhaoyang on 30/12/2016.
 */
@Service("WxJsapiTicketService")
public class WxJsapiTicketService extends WxCommonService {

    private static final Logger logger = Logger.getLogger(WxJsapiTicketService.class);

    private static final Integer ADVANCE_UPDATE_JSAPI_TICKET_IN_MINUTES = 5;
    private static final Integer TICKET_REQUEST_LOCK_MAX_SECONDS = 90;

    private static final String JSAPI_TICKET_LOCK_KEY = "WX_JSAPI_TICKET";

    @Autowired
    private WxAccessTokenService wxAccessTokenService;

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    /**
     *
     * @param url
     *    签名用的url必须是调用JS接口页面的完整URL
     *    Ex:(http://mp.weixin.qq.com?params=value)
     * @param appId
     * @param appSecret
     * @return
     */
    public WxJsapiConfig getJsapiConfig(String url, String appId, String appSecret) {
        if(StringUtils.isEmpty(url)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "当前网页URL不能为空");
        }
        WxJsapiTicket jsapiTicket = getJsapiTicket(appId, appSecret);
        if(jsapiTicket == null) {
            throw new CommonException(CommonErrCode.BUSINESS, "获取JsapiTicket失败");
        }
        String noncestr = UUID.randomUUID().toString().replaceAll("-", "");
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        StringBuilder builder = new StringBuilder();
        builder.append("jsapi_ticket=").append(jsapiTicket.getTicket())
               .append("&noncestr=").append(noncestr)
               .append("&timestamp=").append(timestamp)
               .append("&url=").append(url);
        String signature = SHAUtil.sha1(builder.toString());
        if(StringUtils.isEmpty(signature)) {
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "内部签名失败");
        }
        WxJsapiConfig jsapiConfig = new WxJsapiConfig();
        jsapiConfig.setAppId(appId);
        jsapiConfig.setNoncestr(noncestr);
        jsapiConfig.setTimestamp(timestamp);
        jsapiConfig.setSignature(signature);
        return jsapiConfig;
    }

    public WxJsapiTicket getJsapiTicket(String appId, String appSecret) {
        if(StringUtils.isEmpty(appId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用ID不能为空[tAppId]");
        }
        if(StringUtils.isEmpty(appSecret)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用密钥不能为空[tAppSecret]");
        }

        String cacheKey = RedisKeyConfigure.BrookeJsapiTicketCacheKey(appId);
        WxJsapiTicket jsapiTicket = null;
        try {
            jsapiTicket = this.redisCacheDao.read(cacheKey, WxJsapiTicket.class);
        } catch (Throwable th) {
            logger.warn("<<<<<< Get Wx Jsapi Ticket From Redis Fail(appId=" + appId + ")", th);
        }
        boolean thinkExpired = true;
        if(jsapiTicket != null) {
            DateTime now = DateTime.now();
            int seconds = Seconds.secondsBetween(now
                    , new DateTime(jsapiTicket.getExpireTime())).getSeconds();
            thinkExpired = (seconds <= (ADVANCE_UPDATE_JSAPI_TICKET_IN_MINUTES * 60));
        }
        if(!thinkExpired) {
            validateAppSecret(jsapiTicket, appSecret);
            jsapiTicket.clearSecures();
            return jsapiTicket;
        }

        RedisDistributedLock lock = new RedisDistributedLock(
                this.redisCacheDao.getRedisTemplate(), JSAPI_TICKET_LOCK_KEY);
        if(lock.tryLock(TICKET_REQUEST_LOCK_MAX_SECONDS)) {
            try {
                jsapiTicket = getJsapiTicketFromTServer(appId, appSecret);

                // May save to mongodb

                try {
                    this.redisCacheDao.save(cacheKey, jsapiTicket, (jsapiTicket.getExpireInSeconds() * 1L));
                } catch (Throwable th) {
                    logger.warn("<<<<<< Save Wx Jsapi Ticket To Redis Fail(appId=" + appId + ")", th);
                }
                jsapiTicket.clearSecures();
                return jsapiTicket;
            } catch (CommonException ce) {
                logger.warn("<<<<<< Get Wx Jsapi Ticket From Wx Server Fail(appId="
                        + appId + ", errCode=" + ce.getErrCode() + ", errMsg=" + ce.getErrMsg() + ")", ce);
                throw ce;
            } finally {
                lock.unlock();
            }
        } else {
            // 有其他服务正在更新JSAPI_TICKET, 获取分布式更新锁失败
            // 如果以前缓存中有数据, 返回之前数据
            if(jsapiTicket != null) {
                validateAppSecret(jsapiTicket, appSecret);
                jsapiTicket.clearSecures();
                return jsapiTicket;
            }
            // 缓存中无历史数据,监控Redis是否有新数据更新,最多持续60s
            RedisKeyWatch keyWatch = new RedisKeyWatch(this.redisCacheDao);
            jsapiTicket = keyWatch.watch(cacheKey, 60, WxJsapiTicket.class);
            if(jsapiTicket != null) {
                validateAppSecret(jsapiTicket, appSecret);
                jsapiTicket.clearSecures();
                return jsapiTicket;
            }
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "获取JsApiTicket失败");
        }
    }

    public WxJsapiTicket getJsapiTicketFromTServer(String appId, String appSecret) {
        if(StringUtils.isEmpty(appId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用ID不能为空[tAppId]");
        }
        if(StringUtils.isEmpty(appSecret)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用密钥不能为空[tAppSecret]");
        }
        WxAccessToken accessToken = this.wxAccessTokenService.getAccessToken(appId, appSecret);
        if(accessToken == null) {
            throw new CommonException(CommonErrCode.BUSINESS, "获取AccessToken失败");
        }
        final String url = WxUrl.getJsapiTicketUrl(accessToken.getToken());
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        try {
            httpGet = new HttpGet(url);
            response = HttpClients.createDefault().execute(httpGet);
            if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                String respJsonStr = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                JSONObject jsonObject = JSON.parseObject(respJsonStr);
                if(jsonObject.containsKey("ticket")) {
                    String ticket = jsonObject.getString("ticket");
                    Integer expiresIn = jsonObject.getInteger("expires_in");
                    DateTime now = DateTime.now();
                    WxJsapiTicket jsapiTicket = new WxJsapiTicket();
                    jsapiTicket.setAppId(appId);
                    jsapiTicket.setAppSecret(MD5Util.MD5Encode(("TICKET:" + appSecret), "UTF-8"));
                    jsapiTicket.setTicket(ticket);
                    jsapiTicket.setExpireInSeconds(expiresIn);
                    jsapiTicket.setExpireTime(now.plusSeconds(expiresIn).getMillis());
                    jsapiTicket.setCreateTime(now.getMillis());
                    return jsapiTicket;
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

    private void validateAppSecret(WxJsapiTicket jsapiTicket, String appSecret) {
        if(!jsapiTicket.getAppSecret().equalsIgnoreCase(
                MD5Util.MD5Encode(("TICKET:" + appSecret), "UTF-8"))) {
            throw new CommonException(CommonErrCode.BUSINESS, "AppSecret错误");
        }
    }

}
