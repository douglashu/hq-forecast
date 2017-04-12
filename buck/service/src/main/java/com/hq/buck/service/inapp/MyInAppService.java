package com.hq.buck.service.inapp;

import com.hq.buck.model.inapp.InApp;
import com.hq.buck.model.inapp.MyInApps;
import com.hq.buck.model.inapp.view.*;
import com.hq.buck.repository.mongo.MyInAppsRepository;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zhaoyang on 03/01/2017.
 */
@Service
public class MyInAppService {

    private static final Logger logger = Logger.getLogger(MyInAppService.class);

    private static final Integer MY_INAPPS_EXPIRE_IN_MINUTES = 5;

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    @Autowired
    private InAppService inAppService;

    @Autowired
    private InAppCatService inAppCatService;

    @Autowired
    private MyInAppsRepository myInAppsRepository;

    /**
     * 获取我的应用列表
     * @param appId
     * @param userId
     * @return
     */
    public MyInAppsView getMyInApps(String appId, String userId) {
        MyInAppsView myInAppsView = new MyInAppsView();
        InAppsView inAppsView = this.inAppService.getInApps(appId);
        if(inAppsView.isEmpty()) return myInAppsView;
        MyInApps myInApps = getMyInAppsLocal(appId, userId);
        if(myInApps == null || myInApps.isEmpty()) {
            for (InApp inApp : inAppsView.getInApps()) {
                if(inApp.getDefaults()) {
                    if(myInApps == null || !myInApps.isExcludesInApp(inApp.getId())) {
                        inApp.clearForRender();
                        myInAppsView.getInApps().add(inApp);
                    }
                }
            }
        } else {
            for (String id: myInApps.getInAppIds()) {
                InApp inApp = inAppsView.getInAppById(id);
                if(inApp != null && !myInApps.isExcludesInApp(inApp.getId())) {
                    inApp.clearForRender();
                    myInAppsView.getInApps().add(inApp);
                }
            }
        }
        return myInAppsView;
    }

    /**
     * 获取我的应用类别列表
     * @param appId
     * @param userId
     * @return
     */
    public MyInAppCatsView getMyGroupedInApps(String appId, String userId) {
        MyInAppCatsView myInAppCatsView = new MyInAppCatsView();
        myInAppCatsView.setInAppCatViews(new ArrayList<>());
        List<InAppCatView> inAppCatViews = this.inAppCatService.getInAppCats(appId);
        if(CollectionUtils.isEmpty(inAppCatViews)) return myInAppCatsView;
        MyInApps myInApps = getMyInAppsLocal(appId, userId);
        for(InAppCatView inAppCatView: inAppCatViews) {
            InAppCatView catView = new InAppCatView();
            catView.setId(inAppCatView.getId());
            catView.setTitle(inAppCatView.getTitle());
            catView.setIntroduce(inAppCatView.getIntroduce());
            if(myInApps != null && !CollectionUtils.isEmpty(myInApps.getExcludesInAppIds())) {
                catView.setInApps(new ArrayList<InApp>());
                for(InApp inApp: inAppCatView.getInApps()) {
                    if(myInApps.isExcludesInApp(inApp.getId())) continue;
                    catView.getInApps().add(inApp);
                }
            } else {
                catView.setInApps(inAppCatView.getInApps());
            }
            if(!catView.isEmpty()) {
                myInAppCatsView.getInAppCatViews().add(catView);
            }
        }
        return myInAppCatsView;
    }

    /**
     * 更新我的应用类别列表
     * @param appId
     * @param userId
     * @param myInAppsUpdateView
     */
    public void saveMyInApps(String appId, String userId, MyInAppsUpdateView myInAppsUpdateView) {
        InAppsView inAppsView = this.inAppService.getInApps(appId);
        if(inAppsView.isEmpty()) {
            if(!CollectionUtils.isEmpty(myInAppsUpdateView.getInAppIds())) {
                throw new CommonException(CommonErrCode.BUSINESS
                        , "应用不存在[inAppId=" + myInAppsUpdateView.getInAppIds().get(0) + "]");
            }
        }
        // Check In App Exists
        List<InApp> inApps = inAppsView.getInApps();
        for (String inAppId: myInAppsUpdateView.getInAppIds()) {
            boolean exists = false;
            for(InApp inApp: inApps) {
                if(inApp.getId().equals(inAppId)) {
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                throw new CommonException(CommonErrCode.BUSINESS, "应用不存在[inAppId=" + inAppId + "]");
            }
        }
        // Check Fixed Is Excluded
        Set<String> fixedIds = inAppsView.getFixedInAppIds();
        for(String fixedId: fixedIds) {
            boolean contains = false;
            for (String inAppId: myInAppsUpdateView.getInAppIds()) {
                if(inAppId.equals(fixedId)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                throw new CommonException(CommonErrCode.BUSINESS, "应用不允许被删除[appId=" + fixedId + "]");
            }
        }
        try {
            MyInApps myInApps = new MyInApps();
            myInApps.setId(appId + userId);
            myInApps.setAppId(appId);
            myInApps.setUserId(userId);
            if(CollectionUtils.isEmpty(myInAppsUpdateView.getInAppIds())) {
                myInApps.setInAppIds(new ArrayList<String>());
            } else {
                myInApps.setInAppIds(myInAppsUpdateView.getInAppIds());
            }

            MyInApps existsMyInApps = this.myInAppsRepository.findOne(myInApps.getId());
            if(existsMyInApps != null) {
                myInApps.setExcludesInAppIds(existsMyInApps.getExcludesInAppIds());
            }
            this.myInAppsRepository.save(myInApps);
        } catch (Throwable th) {
            logger.warn("<<<<<< Save My InApps To Mongodb Fail(appId=" + appId + ", userId=" + userId + ")", th);
            throw new CommonException(CommonErrCode.DB_ERROR, "");
        }
        try {
            String cacheKey = "APP:" + appId + ":USER_INAPPS:" + userId;
            this.redisCacheDao.delete(cacheKey, MyInApps.class);
        } catch (Throwable th) {
            logger.warn("<<<<<< Delete My InApps From Redis Fail(appId="
                    + appId + ", userId=" + userId + ")", th);
        }
    }

    private MyInApps getMyInAppsLocal(String appId, String userId) {
        String cacheKey = "APP:" + appId + ":USER_INAPPS:" + userId;
        MyInApps myInApps = null;
        try {
            myInApps = this.redisCacheDao.read(cacheKey, MyInApps.class);
        } catch (Throwable th) {
            logger.warn("<<<<<< Read MyInApps From Redis Fail(appId="
                    + appId + ", userId=" + userId + ")", th);
        }
        if(myInApps == null) {
            myInApps = this.myInAppsRepository.findByUserId(userId);
            if(myInApps != null && !myInApps.isEmpty()) {
                try {
                    this.redisCacheDao.save(cacheKey, myInApps, MY_INAPPS_EXPIRE_IN_MINUTES * 60L);
                } catch (Throwable th) {
                    logger.warn("<<<<<< Save My InApps To Redis Fail(appId="
                            + appId + ", userId=" + userId + ")", th);
                }
            }
        }
        return myInApps;
    }
}
