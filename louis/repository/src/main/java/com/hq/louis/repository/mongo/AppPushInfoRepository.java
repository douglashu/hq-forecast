package com.hq.louis.repository.mongo;

import com.hq.louis.model.platform.AppPushInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by zhaoyang on 07/02/2017.
 */
public interface AppPushInfoRepository extends MongoRepository<AppPushInfo, String> {
}
