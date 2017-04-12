package com.hq.scrati.cache.redis.pubsub;

import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.redis.RedisCacheDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test-esbsdk.xml")
public class TradePubServiceImplTest {

    @Autowired
//    private PubService pubService;

    @Resource(name = "redisCache")
    private RedisCacheDao redisCacheDao;


    @Test
    public void testPublish() throws Exception {

//        redisCacheDao.pushLisItem(RedisKeyConfigure.USER_CENTER_KEY ,"123456");
//        redisCacheDao.pushLisItem(RedisKeyConfigure.USER_CENTER_KEY ,"654321");
//        redisCacheDao.pushLisItem(RedisKeyConfigure.USER_CENTER_KEY ,"654321098");
//        redisCacheDao.pushLisItem(RedisKeyConfigure.USER_CENTER_KEY ,"654321097");

        List<String> redisList = redisCacheDao.readList(RedisKeyConfigure.USER_CENTER_KEY, String.class);
        for (String s : redisList) {
            redisCacheDao.removeListItem(RedisKeyConfigure.USER_CENTER_KEY,s);
//            System.out.println(s);
        }

//        pubService.publish("redis pub test");
    }
}