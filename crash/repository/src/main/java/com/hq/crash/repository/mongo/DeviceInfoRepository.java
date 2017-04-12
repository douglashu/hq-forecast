package com.hq.crash.repository.mongo;

import com.hq.crash.model.push.DeviceInfo;
import com.hq.crash.repository.mongo.custom.DeviceInfoRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by zhaoyang on 19/12/2016.
 */
public interface DeviceInfoRepository extends MongoRepository<DeviceInfo, String>, DeviceInfoRepositoryCustom {

}
