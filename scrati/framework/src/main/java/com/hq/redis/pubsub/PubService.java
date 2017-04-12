package com.hq.redis.pubsub;

import com.hq.scrati.common.constants.Message;
import java.util.List;

/**
 * @包名称：com.hq.scrati.cache.redis.pubsub
 * @创建人：yyx
 * @创建时间：17/1/10 下午8:56
 */
public interface PubService {

    /**
     * 消息发布
     * @param message
     */

    void publish(Message message, String channel, List<String> queueKeys);
}
