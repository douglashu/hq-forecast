package com.hq.brooke.rpc.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.brooke.model.WxUserInfo;
import com.hq.brooke.model.view.req.WxOpenIdReq;
import com.hq.brooke.model.view.req.WxUserInfoReq;
import com.hq.brooke.rpc.dubbo.WxUserInfoDubboService;
import com.hq.brooke.service.WxUserInfoService;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoyang on 23/01/2017.
 */
@Service(interfaceName = "com.hq.brooke.rpc.dubbo.WxUserInfoDubboService", version = "1.0")
public class WxUserInfoDubboServiceImpl extends DubboBaseService implements WxUserInfoDubboService {

    @Autowired
    private WxUserInfoService wxUserInfoService;

    @Override
    public RespEntity getUserInfo(HqRequest request) {
        try {
            WxUserInfoReq userInfoReq = parseRequest(request, WxUserInfoReq.class);
            if (userInfoReq == null) throw new CommonException(CommonErrCode.ARGS_INVALID);
            WxUserInfo userInfo = this.wxUserInfoService.getUserInfoFromTServer(
                    userInfoReq.gettAppId(), userInfoReq.gettAppSecret(), userInfoReq.getOpenId());
            return getSuccessResp(userInfo);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

}
