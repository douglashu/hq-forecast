package com.hq.buck.service.inapp;

import com.hq.buck.model.inapp.InApp;
import com.hq.buck.model.inapp.view.*;
import com.hq.buck.repository.mongo.InAppRepository;
import com.hq.scrati.cache.redis.RedisCacheDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhaoyang on 03/01/2017.
 */
@Service
public class InAppService {
    private static final Logger logger = Logger.getLogger(InAppService.class);

    private static final Integer INAPPS_EXPIRE_IN_MINUTES = 5;

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    @Autowired
    private InAppRepository inAppRepository;

    public InAppsView getInApps(String appId) {
        String cacheKey = "APP:" + appId + ":INAPPS";
        InAppsView inAppsView = null;
        try {
            inAppsView = this.redisCacheDao.read(cacheKey, InAppsView.class);
        } catch (Throwable th) {
            logger.warn("<<<<<< Read All InApps From Redis Fail(appId=" + appId + ")", th);
        }
        if(inAppsView != null) return inAppsView;

        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "defaultOrder"));
        List<InApp> allInApps = this.inAppRepository.findByAppId(appId, sort);
        inAppsView = new InAppsView(allInApps);
        if(!inAppsView.isEmpty()) {
            try {
                this.redisCacheDao.save(cacheKey, inAppsView, INAPPS_EXPIRE_IN_MINUTES * 60L);
            } catch (Throwable th) {
                logger.warn("<<<<<< Save All InApps To Redis Fail(appId=" + appId + ")", th);
            }
        }
        return inAppsView;
    }

}
