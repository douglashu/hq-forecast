package com.hq.buck.repository.mongo;

import com.hq.buck.model.inapp.InApp;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Created by zhaoyang on 28/12/2016.
 */
public interface InAppRepository extends MongoRepository<InApp, String> {

    List<InApp> findByAppId(String appId, Sort sort);

}
