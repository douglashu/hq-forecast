package com.hq.brooke.service;

import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.hq.brooke.model.AliOpenId;
import com.hq.brooke.service.common.AliServiceExecutor;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 23/01/2017.
 */
@Service
public class AliOpenIdService {

    private static final Logger logger = Logger.getLogger(AliOpenIdService.class);

    @Autowired
    private AliServiceExecutor aliServiceExecutor;

    public AliOpenId getUserOpenIdFromTServer(String authCode) {
        if(StringUtils.isEmpty(authCode)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "用户授权码不能为空[authCode]");
        }
        try {
            AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
            request.setGrantType("authorization_code");
            request.setCode(authCode);
            AlipaySystemOauthTokenResponse response = this
                    .aliServiceExecutor.getClient().execute(request);
            if(response.isSuccess()) {
                AliOpenId aliOpenId = new AliOpenId();
                aliOpenId.setAccessToken(response.getAccessToken());
                aliOpenId.setRefreshToken(response.getRefreshToken());
                aliOpenId.setExpiresIn(Integer.valueOf(response.getExpiresIn()));
                aliOpenId.setReExpiresIn(Integer.valueOf(response.getReExpiresIn()));
                aliOpenId.setUserId(response.getUserId());
                return aliOpenId;
            }
            StringBuilder errMsg = new StringBuilder();
            if(!StringUtils.isEmpty(response.getSubCode())) {
                errMsg.append(response.getSubCode());
            }
            if(!StringUtils.isEmpty(response.getSubMsg())) {
                errMsg.append(response.getSubMsg());
            }
            throw new CommonException(CommonErrCode.BUSINESS, errMsg.toString());
        } catch (Throwable th) {
            logger.warn("<<<<<< Read User Id From Alipay Server Fail(authCode=" + authCode + ")");
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "获取支付宝用户ID失败", th);
        }
    }


}
