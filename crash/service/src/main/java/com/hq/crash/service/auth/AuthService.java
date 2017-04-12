package com.hq.crash.service.auth;

import com.hq.crash.model.auth.UserSession;
import com.hq.crash.repository.mongo.UserSessionRepository;
import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;

/**
 * Created by zhaoyang on 17/12/2016.
 */
@Service
public class AuthService {

    private static final Logger logger = Logger.getLogger(AuthService.class);

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    @Autowired
    private UserSessionRepository sessionRepository;

    public UserSession doAuth(String appId, String userId, String authToken) {
        if(StringUtils.isEmpty(appId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用ID不能为空[appId]");
        }
        if(StringUtils.isEmpty(userId)) {
            throw new CommonException(CommonErrCode.AUTH_TOKEN_INVALID, "用户ID不能为空[userId]");
        }
        if(StringUtils.isEmpty(authToken)) {
            throw new CommonException(CommonErrCode.AUTH_TOKEN_INVALID, "授权令牌不能为空[authToken]");
        }
        String cacheKey = RedisKeyConfigure.CrashUserSessionCacheKey(appId, userId);
        UserSession session = null;
        try {
            session = this.redisCacheDao.read(cacheKey, UserSession.class);
        } catch (Throwable th) {
            logger.warn("<<<<<< Read User Session From Redis Fail(appId=" + appId + ", userId=" + appId + ")", th);
        }
        if(session == null) {
            try {
                session = this.sessionRepository.findOne(cacheKey);
                if(session != null) {
                    if(DateTime.now().getMillis() >= session.getExpiryTime()) {
                        session = null;
                    }
                }
            } catch (Throwable th) {
                logger.error("<<<<<< Read User Session From Mongodb Fail(appId=" + appId + ", userId=" + appId + ")", th);
                throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, th);
            }
        }
        if (session == null) {
            throw new CommonException(CommonErrCode.AUTH_TOKEN_INVALID, "登录授权已过期,请重新登录");
        }
        if(!authToken.equals(session.getAuthToken())) {
            String dateStr = (new DateTime(session.getCreateTime())).toString("YYYY-MM-dd HH:mm:ss");
            StringBuffer buffer = new StringBuffer();
            buffer.append("您的账号于").append(dateStr).append("在另一台名为")
                    .append("(").append(session.getDeviceName()).append(")的")
                    .append(session.getOsPlatform().toString()).append("设备登录,如非本人操作,请尽快修改密码");
            throw new CommonException(CommonErrCode.AUTH_TOKEN_INVALID, buffer.toString());
        }
        session.setId(null);
        session.setAuthToken(null);
        session.setBelongCoreId(null);
        return session;
    }

}
