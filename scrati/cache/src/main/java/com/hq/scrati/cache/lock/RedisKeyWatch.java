package com.hq.scrati.cache.lock;

import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.util.Assert;

/**
 * Created by zhaoyang on 03/01/2017.
 */
public class RedisKeyWatch {

    private static final Logger logger = Logger.getLogger(RedisKeyWatch.class);

    private static final Integer MAX_TIMEOUT_SECONDS = 15 * 60;

    private RedisCacheDao redisCacheDao;

    public RedisKeyWatch(RedisCacheDao redisCacheDao) {
        this.redisCacheDao = redisCacheDao;
    }

    public <T> T watch(String key, Integer timeoutSeconds, Class<T> clazz) {
        Assert.hasText(key, "key");
        Assert.notNull(timeoutSeconds, "timeoutSeconds");
        Assert.notNull(clazz, "clazz");
        if(timeoutSeconds > MAX_TIMEOUT_SECONDS) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "超时时间设置过大(MAX_TIMEOUT_SECONDS=" + timeoutSeconds + ")");
        }
        if(timeoutSeconds <= 1) {
            return this.redisCacheDao.read(key, clazz);
        }
        DateTime startTime = DateTime.now();
        while (true) {
            T obj = this.redisCacheDao.read(key, clazz);
            if(obj != null) return obj;
            int diffSeconds = Seconds.secondsBetween(
                    startTime, DateTime.now()).getSeconds();
            if(diffSeconds >= timeoutSeconds) break;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.warn(e);
            }
        }
        return null;
    }

}
