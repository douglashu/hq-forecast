package com.hq.crash.repository.mongo;

import com.hq.crash.model.auth.UserSession;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by zhaoyang on 18/12/2016.
 */
public interface UserSessionRepository extends MongoRepository<UserSession, String> {


}
