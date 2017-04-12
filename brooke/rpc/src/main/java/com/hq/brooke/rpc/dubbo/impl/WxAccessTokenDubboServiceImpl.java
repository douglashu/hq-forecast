package com.hq.brooke.rpc.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.hq.brooke.model.WxAccessToken;
import com.hq.brooke.model.view.req.WxAccessTokenReq;
import com.hq.brooke.rpc.dubbo.WxAccessTokenDubboService;
import com.hq.brooke.service.WxAccessTokenService;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoyang on 02/01/2017.
 */
@Service(interfaceName = "com.hq.brooke.rpc.dubbo.WxAccessTokenDubboService", version = "1.0")
public class WxAccessTokenDubboServiceImpl extends DubboBaseService implements WxAccessTokenDubboService {

    @Autowired
    private WxAccessTokenService wxAccessTokenService;

    @Override
    public RespEntity getAccessToken(HqRequest request) {
        try {
            WxAccessTokenReq tokenReq = parseRequest(request, WxAccessTokenReq.class);
            if(tokenReq == null) throw new CommonException(CommonErrCode.ARGS_INVALID);
            WxAccessToken accessToken = this.wxAccessTokenService
                    .getAccessToken(tokenReq.gettAppId(), tokenReq.gettAppSecret());
            return getSuccessResp(accessToken);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

}
