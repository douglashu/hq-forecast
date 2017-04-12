package com.hq.brooke.rpc.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.brooke.model.WxJsapiConfig;
import com.hq.brooke.model.view.req.WxJsapiConfigReq;
import com.hq.brooke.rpc.dubbo.WxJsapiConfigDubboService;
import com.hq.brooke.service.WxJsapiTicketService;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoyang on 02/01/2017.
 */
@Service(interfaceName = "com.hq.brooke.rpc.dubbo.WxJsapiConfigDubboService", version = "1.0")
public class WxJsapiConfigDubboServiceImpl extends DubboBaseService implements WxJsapiConfigDubboService {

    @Autowired
    private WxJsapiTicketService jsapiTicketService;

    @Override
    public RespEntity getJsapiConfig(HqRequest request) {
        try {
            // "http://m.hqast.com/sylvia"
            WxJsapiConfigReq jsapiConfigReq = parseRequest(request, WxJsapiConfigReq.class);
            if(jsapiConfigReq == null) throw new CommonException(CommonErrCode.ARGS_INVALID);
            WxJsapiConfig jsapiConfig = this.jsapiTicketService.getJsapiConfig(
                    jsapiConfigReq.getUrl(), jsapiConfigReq.gettAppId(), jsapiConfigReq.gettAppSecret());
            return getSuccessResp(jsapiConfig);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

}
