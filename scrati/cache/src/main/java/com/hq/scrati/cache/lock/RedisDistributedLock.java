package com.hq.scrati.cache.lock;

import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by zhaoyang on 02/01/2017.
 */
public class RedisDistributedLock {

    private static final String REDIS_LOCK_KEY_PREFIX = "REDIS_LOCK";

    private static final Long MIN_EXPIRY_IN_SECONDS = 60L;
    private static final Long MAX_EXPIRY_IN_SECONDS = 15 * 60L;

    private static final Logger logger = Logger.getLogger(RedisDistributedLock.class);

    private RedisTemplate<Serializable, Serializable> redisTemplate;

    private byte[] key;
    private byte[] value;
    private boolean locked;

    private RedisDistributedLock() { }

    public RedisDistributedLock(RedisTemplate redisTemplate, String key) {
        Assert.notNull(redisTemplate, "redisTemplate");
        Assert.hasText(key, "key");
        this.redisTemplate = redisTemplate;
        this.key = (REDIS_LOCK_KEY_PREFIX + ":" + key).getBytes();
    }

    public boolean tryLock(Integer expiryInSeconds) {
        if(expiryInSeconds == null || expiryInSeconds < MIN_EXPIRY_IN_SECONDS) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "过期时间为空或设置过小[MIN_EXPIRY_IN_SECONDS=" + MIN_EXPIRY_IN_SECONDS + "]");
        }
        if(expiryInSeconds > MAX_EXPIRY_IN_SECONDS) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "过期时间为设置过大[MAX_EXPIRY_IN_SECONDS=" + MAX_EXPIRY_IN_SECONDS + "]");
        }
        if(this.isLocked()) return false;
        // Try Lock
        try {
            RedisConnection connection = this.redisTemplate.getConnectionFactory().getConnection();
            // Generate Lock Value
            StringBuilder builder = new StringBuilder(
                    UUID.randomUUID().toString().replaceAll("-", ""));
            builder.append(":").append(System.currentTimeMillis());
            this.value = builder.toString().getBytes();
            connection.setEx(this.key, expiryInSeconds, this.value);
            byte[] actualValue = connection.get(this.key);
            if(actualValue != null &&
                    Arrays.equals(actualValue, this.value)) {
                this.locked = true;
            }
        } catch (Throwable th) {
            logger.warn("<<<<<< Set Ex To Redis Fail(key=" + getKey() + ")", th);
            unlock();
        }
        return isLocked();
    }

    public void unlock() {
        try {
            RedisConnection connection = this.redisTemplate.getConnectionFactory().getConnection();
            if(connection.exists(this.key)) {
                byte[] lockValue = connection.get(this.key);
                if(Arrays.equals(lockValue, this.value)) {
                    connection.del(this.key);
                }
            }
            this.locked = false;
        } catch (Throwable th) {
            logger.error("<<<<<< Remove Redis Lock Key Fail(key=" + getKey() + ")", th);
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public String getKey() {
        return new String(this.getKey());
    }
}
