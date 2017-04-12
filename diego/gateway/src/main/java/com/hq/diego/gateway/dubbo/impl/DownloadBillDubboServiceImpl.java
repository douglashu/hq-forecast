package com.hq.diego.gateway.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.diego.gateway.dubbo.DownloadBillDubboService;
import com.hq.diego.gateway.service.core.DownloadBillDispatcher;
import com.hq.diego.model.req.DownloadBillReq;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoyang on 04/02/2017.
 */
@Service(interfaceName = "com.hq.diego.gateway.dubbo.DownloadBillDubboService", version = "1.0")
public class DownloadBillDubboServiceImpl extends DubboBaseService implements DownloadBillDubboService {

    @Autowired
    private DownloadBillDispatcher dispatcher;

    @Override
    public RespEntity downloadBill(HqRequest request) {
        try {
            DownloadBillReq downloadBillReq = parseRequest(request, DownloadBillReq.class);
            return getSuccessResp(this.dispatcher.downloadBill(downloadBillReq, null));
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

}
