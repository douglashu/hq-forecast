package com.hq.buck.repository.mongo;

import com.hq.buck.model.app.App;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by zhaoyang on 18/12/2016.
 */
public interface AppRepository extends MongoRepository<App, String> {

}
