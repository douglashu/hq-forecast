package com.hq.brooke.rpc.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.brooke.model.AliOpenId;
import com.hq.brooke.model.view.req.AliOpenIdReq;
import com.hq.brooke.rpc.dubbo.AliOpenIdDubboService;
import com.hq.brooke.service.AliOpenIdService;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoyang on 23/01/2017.
 */
@Service(interfaceName = "com.hq.brooke.rpc.dubbo.AliOpenIdDubboService", version = "1.0")
public class AliOpenIdDubboServiceImpl extends DubboBaseService implements AliOpenIdDubboService {

    @Autowired
    private AliOpenIdService aliOpenIdService;

    @Override
    public RespEntity getUserOpenId(HqRequest request) {
        try {
            AliOpenIdReq openIdReq = parseRequest(request, AliOpenIdReq.class);
            if(openIdReq == null) throw new CommonException(CommonErrCode.ARGS_INVALID);
            AliOpenId aliOpenId = this.aliOpenIdService
                    .getUserOpenIdFromTServer(openIdReq.getAuthCode());
            return getSuccessResp(aliOpenId);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

}
