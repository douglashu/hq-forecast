package com.hq.diego.gateway.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.diego.gateway.dubbo.TradePollingDubboService;
import com.hq.diego.model.req.OrderPollingReq;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.diego.gateway.service.core.TradeOrderMonitor;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoyang on 09/12/2016.
 */
@Service(interfaceName = "com.hq.diego.gateway.dubbo.TradePollingDubboService", version = "1.0")
public class TradePollingDubboServiceImpl extends DubboBaseService implements TradePollingDubboService {

    @Autowired
    private TradeOrderMonitor tradeOrderMonitor;

    @Override
    public RespEntity polling(HqRequest request) {
        try {
            OrderPollingReq pollingReq = parseRequest(request, OrderPollingReq.class);
            OrderTradeResp tradeResp = this.tradeOrderMonitor
                    .pollingTradeResp(pollingReq.getMchId(), pollingReq.getTradeId());
            return getSuccessResp(tradeResp);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

}
