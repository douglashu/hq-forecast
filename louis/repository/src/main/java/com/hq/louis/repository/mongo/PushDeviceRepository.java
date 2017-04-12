package com.hq.louis.repository.mongo;

import com.hq.louis.repository.model.PushDevice;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public interface PushDeviceRepository extends MongoRepository<PushDevice, String> {

    List<PushDevice> findByStateAndKeyIdInAndExpiryTimeAfter(Boolean state, List<String> keyIds, Date now);

    List<PushDevice> findByStateAndGrpIdAndExpiryTimeAfter(Boolean state, String grpId, Date now);

}
