package com.hq.redis.pubsub;

import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.lock.RedisDistributedLock;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.framework.FrameworkInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @包名称：com.hq.scrati.cache.redis.pubsub
 * @创建人：yyx
 * @创建时间：17/1/10 下午1:04
 */
public abstract class AbstractTopicMessageListener implements MessageListener {

    private Logger logger = Logger.getLogger();

    private RedisDistributedLock lock;
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name = "redisCache")
    private RedisCacheDao redisCacheDao;

    @Resource(name = "frameworkInvoker")
    private FrameworkInvoker frameworkInvoker;


    public abstract String getQueueKey();

    //    @Value("${tony.trade.expiryInMinutes}")
//    private String expiryInMinutes;
//
//    @Value("${tony.trade.maxRetryTimes}")
//    private String maxRetryTimes;
//

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        lock = new RedisDistributedLock(redisTemplate, getQueueKey());
        if (lock.tryLock(1000)) {
            // TODO
            // 1、从redis队列中获取未处理的订单数据
            List<String> userQueue = redisCacheDao.readList(getQueueKey(), String.class);

            userQueue.stream().filter(c -> c != null).forEach(queue -> {
                try {
                    doProcess(queue);
                    //删除redis处理过的数据
                    redisCacheDao.removeListItem(getQueueKey(), queue);
                }catch (Exception e){
                    logger.error(e);
                }
            });

            lock.unlock();

            // TODO 以下为数据的基本处理方式
            /* byte[] body = message.getBody();//请使用valueSerializer
            byte[] channel = message.getChannel();
            //请参考配置文件，本例中key，value的序列化方式均为string。
            //其中key必须为stringSerializer。和redisTemplate.convertAndSend对应
            String itemValue = (String) redisTemplate.getValueSerializer().deserialize(body);
            logger.info("Message:" + itemValue);
            String topic = (String) redisTemplate.getStringSerializer().deserialize(channel);
            logger.info("Topic:" + topic);
            */
        }
    }

    protected abstract void doProcess(String queue);
}
