package com.hq.buck.repository.mongo;

import com.hq.buck.model.inapp.InAppCat;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Created by zhaoyang on 29/12/2016.
 */
public interface InAppCatRepository extends MongoRepository<InAppCat, String> {

    List<InAppCat> findByAppIdAndState(String appId, Integer state, Sort sort);

}
