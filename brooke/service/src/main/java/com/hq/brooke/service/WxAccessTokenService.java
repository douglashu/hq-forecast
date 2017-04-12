package com.hq.brooke.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hq.brooke.model.WxAccessToken;
import com.hq.brooke.model.constant.WxUrl;
import com.hq.brooke.service.common.WxCommonService;
import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.lock.RedisDistributedLock;
import com.hq.scrati.cache.lock.RedisKeyWatch;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.signature.MD5Util;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * Created by zhaoyang on 25/12/2016.
 */
@Service("WxAccessTokenService")
public class WxAccessTokenService extends WxCommonService {

    private static final Logger logger = Logger.getLogger(WxAccessTokenService.class);

    private static final Integer ADVANCE_UPDATE_TOKEN_IN_MINUTES = 5;
    private static final Integer TOKEN_REQUEST_LOCK_MAX_SECONDS = 90;
    private static final String ACCESS_TOKEN_LOCK_KEY = "WX_ACCESS_TOKEN";

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    public WxAccessToken getAccessToken(String appId, String appSecret) {
        if(StringUtils.isEmpty(appId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用ID不能为空[tAppId]");
        }
        if(StringUtils.isEmpty(appSecret)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用密钥不能为空[tAppSecret]");
        }

        String cacheKey = RedisKeyConfigure.BrookeAccessTokenCacheKey(appId);
        WxAccessToken accessToken = null;
        try {
            accessToken = this.redisCacheDao.read(cacheKey, WxAccessToken.class);
        } catch (Throwable th) {
            logger.warn("<<<<<< Get Wx Access Token From Redis Fail(appId=" + appId + ")", th);
        }

        boolean thinkExpired = true;
        if(accessToken != null) {
            DateTime now = DateTime.now();
            int seconds = Seconds.secondsBetween(now
                    , new DateTime(accessToken.getExpireTime())).getSeconds();
            // 在微信AccessToken快过期之前, 提前几分钟更新access token
            thinkExpired = (seconds <= (ADVANCE_UPDATE_TOKEN_IN_MINUTES * 60));
        }
        if(!thinkExpired) {
            validateAppSecret(accessToken, appSecret);
            accessToken.clearSecures();
            return accessToken;
        }

        RedisDistributedLock lock = new RedisDistributedLock(
                this.redisCacheDao.getRedisTemplate(), ACCESS_TOKEN_LOCK_KEY);
        if(lock.tryLock(TOKEN_REQUEST_LOCK_MAX_SECONDS)) {
            try {
                accessToken = getAccessTokenFromTServer(appId, appSecret);

                // May save to mongodb

                try {
                    this.redisCacheDao.save(cacheKey, accessToken, (accessToken.getExpireInSeconds() * 1L));
                } catch (Throwable th) {
                    logger.warn("<<<<<< Save Wx Access Token To Redis Fail(appId=" + appId + ")", th);
                }
                accessToken.clearSecures();
                return accessToken;
            } catch (CommonException ce) {
                logger.warn("<<<<<< Get Wx Access Token From Wx Server Fail(appId="
                        + appId + ", errCode=" + ce.getErrCode() + ", errMsg=" + ce.getErrMsg() + ")", ce);
                throw ce;
            } finally {
                lock.unlock();
            }
        } else {
            /*
                获取分布式锁失败, 有其他服务正在更新AccessToken
                如果以前缓存中有数据, 返回之前数据, 还可以继续使用

                目前access_token的有效期通过返回的expire_in来传达，目前是7200秒之内的值。
                中控服务器需要根据这个有效时间提前去刷新新access_token。
                在刷新过程中，中控服务器对外输出的依然是老access_token，
                此时公众平台后台会保证在刷新短时间内，新老access_token都可用，
                这保证了第三方业务的平滑过渡
             */
            if(accessToken != null) {
                validateAppSecret(accessToken, appSecret);
                accessToken.clearSecures();
                return accessToken;
            }
            // 缓存中无历史数据, 监控Redis是否有新数据更新,最多持续60s
            RedisKeyWatch keyWatch = new RedisKeyWatch(this.redisCacheDao);
            accessToken = keyWatch.watch(cacheKey, 60, WxAccessToken.class);
            if(accessToken != null) {
                validateAppSecret(accessToken, appSecret);
                accessToken.clearSecures();
                return accessToken;
            }
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "获取AccessToken失败");
        }
    }

    /**
     * access_token是公众号的全局唯一接口调用凭据，
     * 公众号调用各接口时都需使用access_token。开发者需要进行妥善保存。
     * access_token的存储至少要保留512个字符空间。access_token的有效期目前为2个小时，
     * 需定时刷新，重复获取将导致上次获取的access_token失效。
     *
     * @param appId
     * @param appSecret
     */
    private WxAccessToken getAccessTokenFromTServer(String appId, String appSecret) {
        if(StringUtils.isEmpty(appId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用ID不能为空[tAppId]");
        }
        if(StringUtils.isEmpty(appSecret)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用密钥不能为空[tAppSecret]");
        }
        final String url = WxUrl.getAccessTokenUrl(appId, appSecret);
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        try {
            httpGet = new HttpGet(url);
            response = HttpClients.createDefault().execute(httpGet);
            if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                String respJsonStr = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                logger.info("Get Wx Access Token Resp String:\r\n" + respJsonStr);
                JSONObject jsonObject = JSON.parseObject(respJsonStr);
                if(jsonObject.containsKey("access_token")) {
                    String accessToken = jsonObject.getString("access_token");
                    Integer expiresIn = jsonObject.getInteger("expires_in");
                    DateTime now = DateTime.now();
                    WxAccessToken wxAccessToken = new WxAccessToken();
                    wxAccessToken.setAppId(appId);
                    wxAccessToken.setAppSecret(MD5Util.MD5Encode(("TOKEN:" + appSecret), "UTF-8"));
                    wxAccessToken.setToken(accessToken);
                    wxAccessToken.setExpireInSeconds(expiresIn);
                    wxAccessToken.setExpireTime(now.plusSeconds(expiresIn).getMillis());
                    wxAccessToken.setCreateTime(now.getMillis());
                    return wxAccessToken;
                } else {
                    throwExceptionWithJsonObj(jsonObject);
                }
            } else {
                throwExceptionWithStatusCode(response.getStatusLine().getStatusCode());
            }
        } catch (Throwable th) {
            if(th instanceof CommonException) throw (CommonException) th;
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "获取AccessToken失败", th);
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

    private void validateAppSecret(WxAccessToken accessToken, String appSecret) {
        if(!accessToken.getAppSecret().equalsIgnoreCase(
                MD5Util.MD5Encode(("TOKEN:" + appSecret), "UTF-8"))) {
            throw new CommonException(CommonErrCode.BUSINESS, "AppSecret错误");
        }
    }
}
