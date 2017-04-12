package com.hq.buck.service.inapp;

import com.hq.buck.model.inapp.InApp;
import com.hq.buck.model.inapp.InAppCat;
import com.hq.buck.model.inapp.view.InAppCatView;
import com.hq.buck.model.inapp.view.InAppCatsView;
import com.hq.buck.model.inapp.view.InAppsView;
import com.hq.buck.repository.mongo.InAppCatRepository;
import com.hq.scrati.cache.redis.RedisCacheDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyang on 03/01/2017.
 */
@Service
public class InAppCatService {

    private static final Logger logger = Logger.getLogger(InAppCatService.class);

    private static final Integer GROUPED_INAPPS_EXPIRE_IN_MINUTES = 5;

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    @Autowired
    private InAppCatRepository inAppCatRepository;

    @Autowired
    private InAppService inAppService;

    /**
     * 获取所有应用类别信息
     * @param appId
     * @return
     */
    public List<InAppCatView> getInAppCats(String appId) {
        // Get All Cats From Redis
        String cacheKey = "APP:" + appId + ":INAPP_CATS";

        InAppCatsView inAppCatsView = null;
        try {
            inAppCatsView = this.redisCacheDao.read(cacheKey, InAppCatsView.class);
        } catch (Throwable th) {
            logger.warn("<<<<<< Read InApp Cats From Redis Fail(appId=" + appId + ")", th);
        }

        if(inAppCatsView != null && !inAppCatsView.isEmpty()) {
            return inAppCatsView.getInAppCatViews();
        }

        // Get All Cats From Database && Save To Redis
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "order"));
        List<InAppCat> inAppCats = this.inAppCatRepository.findByAppIdAndState(appId, 1, sort);
        List<InAppCatView> inAppCatViews = new ArrayList<>();
        InAppsView inAppsView = this.inAppService.getInApps(appId);
        if(!inAppsView.isEmpty()) {
            for (InAppCat inAppCat : inAppCats) {
                InAppCatView inAppCatView = new InAppCatView();
                inAppCatView.setId(inAppCat.getId());
                inAppCatView.setTitle(inAppCat.getTitle());
                inAppCatView.setIntroduce(inAppCat.getIntroduce());
                inAppCatView.setInApps(new ArrayList<InApp>());
                for (String inAppId : inAppCat.getInAppIds()) {
                    InApp inApp = inAppsView.getInAppById(inAppId);
                    if (inApp == null || inApp.getState() == 0) continue;
                    inApp.clearForRender();
                    inAppCatView.getInApps().add(inApp);
                }
                if(!inAppCatView.isEmpty()) {
                    inAppCatViews.add(inAppCatView);
                }
            }
            if (!CollectionUtils.isEmpty(inAppCatViews)) {
                try {
                    inAppCatsView = new InAppCatsView();
                    inAppCatsView.getInAppCatViews().addAll(inAppCatViews);
                    this.redisCacheDao.save(cacheKey, inAppCatsView, GROUPED_INAPPS_EXPIRE_IN_MINUTES * 60L);
                } catch (Throwable th) {
                    logger.warn("<<<<<< Save Grouped InApps From Redis Fail(appId=" + appId + ")", th);
                }
            }
        }
        return inAppCatViews;
    }

}
