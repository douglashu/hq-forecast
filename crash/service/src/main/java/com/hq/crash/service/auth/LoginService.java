package com.hq.crash.service.auth;

import com.alibaba.fastjson.JSON;
import com.hq.crash.model.auth.UserSession;
import com.hq.crash.model.auth.req.LoginReq;
import com.hq.crash.model.auth.resp.LoginResp;
import com.hq.crash.repository.mongo.UserSessionRepository;
import com.hq.crash.service.common.CrashCommonService;
import com.hq.crash.service.device.DeviceBindService;
import com.hq.louis.model.req.DeviceunBindReq;
import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.constants.OSPlatform;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.MapBuilder;
import com.hq.scrati.model.HqRequest;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by zhaoyang on 13/01/2017.
 */
@Service
public class LoginService extends CrashCommonService {

    private static final Logger logger = Logger.getLogger(LoginService.class);

    private static final Integer USER_SESSION_EXPIRE_IN_DAYS = 180;

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    @Autowired
    private UserSessionRepository sessionRepository;

    @Autowired
    private DeviceBindService deviceBindService;

    public UserSession login(LoginReq loginReq) {
        if(loginReq == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        if(StringUtils.isEmpty(loginReq.getAppId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用ID不能为空[appId]");
        }
        if(StringUtils.isEmpty(loginReq.getAccount())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "登录账号不能为空[account]");
        }
        if(StringUtils.isEmpty(loginReq.getPassword())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "登录密码不能为空[password]");
        }
        if(!OSPlatform.isValid(loginReq.getOsPlatform())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "设备操作系统有误[osPlatform]");
        }
        if(StringUtils.isEmpty(loginReq.getDeviceName())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "设备名称不能为空[deviceName]");
        }

        // 目前只有一个应用APP, 暂时进行写死处理, 缩短请求链路
        if(!"001".equals(loginReq.getAppId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "应用不存在[appId=" + loginReq.getAppId() + "]");
        }

        // 调用登录服务
        HqRequest request = new HqRequest();
        request.setAppId(loginReq.getAppId());
        String loginReqJson = JSON.toJSONString(MapBuilder
                .create("username", loginReq.getAccount())
                .add("password", loginReq.getPassword()).get());
        request.setBizContent(loginReqJson);
        LoginResp loginResp = invoke("sid.oper.merclogin", "1.0", request, LoginResp.class);

        // Session Persistent
        String cacheKey = RedisKeyConfigure
                .CrashUserSessionCacheKey(loginReq.getAppId(), loginResp.getUserId());
        DateTime now = DateTime.now();
        UserSession userSession = new UserSession();
        userSession.setId(cacheKey);
        userSession.setAppId(loginReq.getAppId());
        userSession.setUserId(loginResp.getUserId());
        userSession.setUserName(loginResp.getUserName());
        userSession.setMobile(loginResp.getMobile());
        userSession.setBelongCoreId(loginResp.getBelongCoreId());
        if (!CollectionUtils.isEmpty(loginResp.getRole())) {
            userSession.setRole(loginResp.getRole().get(0));
        }
        if(loginResp.getMchs() == null) {
            userSession.setMchs(new ArrayList<>());
        } else {
            if(loginResp.getMchs().size() > 0) {
                userSession.setActiveMch(loginResp.getMchs().get(0).getMchId());
            }
            userSession.setMchs(loginResp.getMchs());
        }
        userSession.setOsPlatform(loginReq.getOsPlatform());
        userSession.setDeviceName(loginReq.getDeviceName());
        userSession.setAuthToken(UUID.randomUUID().toString().replaceAll("-", ""));
        userSession.setCreateTime(now.getMillis());
        userSession.setExpiryTime(now.plusDays(USER_SESSION_EXPIRE_IN_DAYS).getMillis());
        try {
            this.sessionRepository.save(userSession);
        } catch (Throwable th) {
            logger.warn("<<<<<< Save User Session To MongoDB Fail(appId="
                    + userSession.getAppId() + ", userId=" + userSession.getUserId() + ")", th);
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, th);
        }
        try {
            this.redisCacheDao.save(cacheKey, userSession
                    , USER_SESSION_EXPIRE_IN_DAYS * 24 * 60 * 60L);
        } catch (Throwable th) {
            logger.warn("<<<<<< Save User Session To Redis Fail(appId="
                    + userSession.getAppId() + ", userId=" + userSession.getUserId() + ")", th);
        }
        userSession.setId(null);
        userSession.setPwdNeedReset(loginResp.getNeedChangePwd());
        return userSession;
    }

    public void logout(String appId, String userId) {
        String cacheKey = RedisKeyConfigure
                .CrashUserSessionCacheKey(appId, userId);
        try {
            this.redisCacheDao.delete(cacheKey, UserSession.class);
        } catch (Throwable th) {
            logger.warn("<<< Delete User Session From Redis Fail(appId="
                    + appId + ", userId=" + userId + ")", th);
        }
        try {
            this.sessionRepository.delete(cacheKey);
        } catch (Throwable th) {
            logger.warn("<<< Delete User Session From MongoDB Fail(appId="
                    + appId + ", userId=" + userId + ")", th);
        }
        try {
            DeviceunBindReq req = new DeviceunBindReq();
            req.setKey(appId + ":" + userId);
            this.deviceBindService.unbind(req, appId, userId);
        } catch (Throwable th) {
            logger.warn("<<< UnBind Device Token Fail(appId="
                    + appId + ", userId=" + userId + ")");
        }
    }

}
