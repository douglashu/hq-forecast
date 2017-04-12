package com.hq.guan.service.auth;

import com.alibaba.fastjson.JSON;
import com.hq.guan.service.common.GuanCommonService;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.guan.model.auth.AliAppAuth;
import com.hq.guan.repository.mongo.AliAppAuthRepository;
import com.hq.scrati.common.util.MapBuilder;
import com.hq.scrati.model.HqRequest;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Map;

/**
 * Created by zhaoyang on 16/01/2017.
 */
@Service
public class AliAppAuthService extends GuanCommonService {

    private static final Logger logger = Logger.getLogger(AliAppAuthService.class);

    @Autowired
    private AliAppAuthRepository appAuthRepository;

    public void doAppAuth(String appId, String appAuthCode, Integer coreId) {
        if(StringUtils.isEmpty(appId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "开发者应用id不能为空[appId]");
        }
        if(StringUtils.isEmpty(appAuthCode)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商户授权码不能为空[appAuthCode]");
        }
        if(coreId == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商户客户号不能为空[coreId]");
        }
        boolean success = false;
        String errorDes = "";
        try {
            HqRequest request = new HqRequest();
            request.setAppId(appId);
            Map<String, Object> reqParams = MapBuilder
                    .create("core_id", coreId)
                    .add("auth_code", appAuthCode).get();
            request.setBizContent(JSON.toJSONString(reqParams));
            invoke("sid.merc.exchangetoken", "1.0", request, null);
            success = true;
        } catch (CommonException ce) {
            if (!StringUtils.isEmpty(ce.getErrCode())) errorDes = ce.getErrCode();
            if (!StringUtils.isEmpty(ce.getErrMsg())) {
                if (!StringUtils.isEmpty(errorDes)) {
                    errorDes += "-";
                }
                errorDes += ce.getErrMsg();
            }
        } catch (Throwable th) {
            logger.warn("<<<<<< Save Ali App Auth To Service Fail", th);
        }
        try {
            AliAppAuth aliAppAuth = new AliAppAuth();
            aliAppAuth.setAppId(appId);
            aliAppAuth.setAppAuthCode(appAuthCode);
            aliAppAuth.setCoreId(coreId);
            aliAppAuth.setSuccess(success);
            aliAppAuth.setErrorDes(errorDes);
            aliAppAuth.setCreateTime(DateTime.now().getMillis());
            this.appAuthRepository.save(aliAppAuth);
        } catch (Throwable th) {
            logger.warn("<<<<<< Save Ali App Auth To Mongodb Fail(appId="
                    + appId + ", appAuthCode=" + appAuthCode + ")");
        }
    }
}
