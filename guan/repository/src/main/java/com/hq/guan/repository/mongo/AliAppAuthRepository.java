package com.hq.guan.repository.mongo;

import com.hq.guan.model.auth.AliAppAuth;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by zhaoyang on 16/01/2017.
 */
public interface AliAppAuthRepository extends MongoRepository<AliAppAuth, String> {
}
