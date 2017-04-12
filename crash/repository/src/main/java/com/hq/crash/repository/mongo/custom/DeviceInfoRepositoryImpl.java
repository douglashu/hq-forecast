package com.hq.crash.repository.mongo.custom;

import com.hq.crash.model.push.DeviceInfo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * Created by zhaoyang on 19/12/2016.
 */
public class DeviceInfoRepositoryImpl implements DeviceInfoRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateDeviceToken(String appId, String userId, String osPlatform, String deviceToken) {
        String id = (appId + userId);
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("appId", appId);
        update.set("userId", userId);
        update.set("osPlatform", osPlatform);
        update.set("deviceToken", deviceToken);
        update.set("state", 1);
        update.set("updateTime", DateTime.now().getMillis());
        this.mongoTemplate.updateFirst(query, update, DeviceInfo.class);
    }

    @Override
    public void markDeviceTokenInvalid(String appId, String userId) {
        String id = (appId + userId);
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("state", 0);
        update.set("updateTime", DateTime.now().getMillis());
        this.mongoTemplate.updateFirst(query, update, DeviceInfo.class);
    }

    @Override
    public boolean exists(String appId, String userId) {
        String id = (appId + userId);
        Query query = Query.query(Criteria.where("id").is(id));
        return this.mongoTemplate.exists(query, DeviceInfo.class);
    }

}
