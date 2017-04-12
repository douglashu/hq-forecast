package com.hq.brooke.rpc.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.brooke.model.WxOpenId;
import com.hq.brooke.model.view.req.WxOpenIdReq;
import com.hq.brooke.rpc.dubbo.WxOpenIdDubboService;
import com.hq.brooke.service.WxOpenIdService;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoyang on 05/01/2017.
 */
@Service(interfaceName = "com.hq.brooke.rpc.dubbo.WxOpenIdDubboService", version = "1.0")
public class WxOpenIdDubboServiceImpl extends DubboBaseService implements WxOpenIdDubboService {

    @Autowired
    private WxOpenIdService wxOpenIdService;

    @Override
    public RespEntity getUserOpenId(HqRequest request) {
        try {
            WxOpenIdReq openIdReq = parseRequest(request, WxOpenIdReq.class);
            if(openIdReq == null) throw new CommonException(CommonErrCode.ARGS_INVALID);
            WxOpenId wxOpenId = this.wxOpenIdService.getUserOpenIdFromTServer(
                    openIdReq.gettAppId(), openIdReq.gettAppSecret(), openIdReq.getAuthCode());
            return getSuccessResp(wxOpenId);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

}
