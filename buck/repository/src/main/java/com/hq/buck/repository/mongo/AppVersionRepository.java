package com.hq.buck.repository.mongo;

import com.hq.buck.model.app.AppVersion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Created by zhaoyang on 09/02/2017.
 */
public interface AppVersionRepository extends MongoRepository<AppVersion, String> {

    List<AppVersion> findByAppIdAndPlatform(
            String appId, String platform, Pageable pageable);

    List<AppVersion> findByAppIdAndPlatformAndVersion(
            String appId, String platform, String version);


}
