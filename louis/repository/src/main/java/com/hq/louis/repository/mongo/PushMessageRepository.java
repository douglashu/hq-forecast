package com.hq.louis.repository.mongo;

import com.hq.louis.repository.model.PushMessage;
import com.hq.louis.repository.mongo.custom.PushMessageRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public interface PushMessageRepository extends MongoRepository<PushMessage, String>, PushMessageRepositoryCustom {
}
