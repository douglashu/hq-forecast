package com.hq.louis.service.core;

import com.hq.louis.model.platform.AppPushInfo;
import com.hq.louis.repository.mongo.AppPushInfoRepository;
import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;

/**
 * Created by zhaoyang on 08/02/2017.
 */
@Service
public class AppPushInfoService {

    private static final Logger logger = Logger.getLogger(AppPushInfoService.class);

    private static final Integer APP_PUSH_INFO_EXPIRY_IN_DAYS = 3;

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    @Autowired
    private AppPushInfoRepository appPushInfoRepository;

    public AppPushInfo getAppPushInfo(String appId) {
        if (StringUtils.isEmpty(appId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用id不能为空[appId]");
        }
        String cacheKey = RedisKeyConfigure.LouisAppPushInfoCacheKey(appId);
        AppPushInfo pushInfo = null;
        try {
            pushInfo = this.redisCacheDao.read(cacheKey, AppPushInfo.class);
        } catch (Throwable th) {
            logger.warn("<<<<<< Read AppPushInfo From Redis Fail(cacheKey=" + cacheKey + ")", th);
        }
        if (pushInfo == null) {
            pushInfo = this.appPushInfoRepository.findOne(appId);
            if (pushInfo == null) return null;
            try {
                this.redisCacheDao.save(cacheKey, pushInfo, APP_PUSH_INFO_EXPIRY_IN_DAYS * 24 * 60 * 60L);
            } catch (Throwable th) {
                logger.warn("<<<<<< Save AppPushInfo To Redis Fail(cacheKey=" + cacheKey + ")", th);
            }
        }
        return pushInfo;
    }

}
