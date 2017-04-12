package com.hq.redis.pubsub.impl;

import com.alibaba.fastjson.JSONObject;
import com.hq.redis.pubsub.PubService;
import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.constants.Message;
import com.hq.scrati.common.constants.RedisPubType;
import com.hq.scrati.common.log.Logger;
import java.util.List;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;

/**
 * @包名称：com.hq.scrati.cache.redis.pubsub
 * @创建人：yyx
 * @创建时间：17/1/10 下午8:58
 */
@Service
public class PubServiceImpl implements PubService {

    private static Logger logger = Logger.getLogger();

    @Resource
    private RedisTemplate redisTemplate;

    @Resource(name = "redisCache")
    private RedisCacheDao redisCacheDao;

    private void publish(String message,String channel, List<String> queueKeys) {
        // 补偿机制
        if(queueKeys!=null) {
            queueKeys.forEach(q->this.redisCacheDao.pushLisItem(q, message));
        }
        // 发布消息
        this.redisTemplate.convertAndSend(channel,message);
    }

    @Override
    public void publish(Message message, String channel, List<String> queueKeys) {
        if (message == null) return;
        if(com.hq.scrati.common.util.StringUtils.isBlank(channel)){
            logger.warn("<<<<<< Redis Publish Message Fail, Channel Is Null");
            return;
        }
        if (StringUtils.isEmpty(message.getOrderId())) {
            logger.warn("<<<<<< Redis Publish Message Fail, Order Id Is Null");
            return;
        }
        String jsonString = JSONObject.toJSONString(message);
        try {
            publish(jsonString,channel,queueKeys);
        } catch (Throwable th){
            logger.warn("<<<<<< Redis publish message fail", th);
            logger.warn("<<<<<< " + jsonString);
        }
    }
}
