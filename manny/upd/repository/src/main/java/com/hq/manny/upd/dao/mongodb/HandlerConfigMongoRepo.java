package com.hq.manny.upd.dao.mongodb;

import com.hq.manny.upd.entity.mongodb.HandlerConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Zale on 2016/11/23.
 */
public interface HandlerConfigMongoRepo extends MongoRepository<HandlerConfig,String>{
}
