package com.hq.buck.repository.mongo;

import com.hq.buck.model.inapp.MyInApps;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by zhaoyang on 29/12/2016.
 */
public interface MyInAppsRepository extends MongoRepository<MyInApps, String> {

    MyInApps findByUserId(String userId);

}

