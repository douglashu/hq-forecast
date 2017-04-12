package com.hq.diego.gateway.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.diego.gateway.dubbo.TradePayDubboService;
import com.hq.scrati.framework.IDGenerator;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.gateway.service.core.TradeOrderDispatcher;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoyang on 26/11/2016.
 */
@Service(interfaceName = "com.hq.diego.gateway.dubbo.TradePayDubboService", version = "1.0")
public class TradePayDubboServiceImpl extends DubboBaseService implements TradePayDubboService {

    @Autowired
    private TradeOrderDispatcher orderDispatcher;

    @Autowired
    private IDGenerator idGenerator;

    @Override
    public RespEntity pay(HqRequest request) {
        try {
            OrderTradeReq tradeReq = parseRequest(request, OrderTradeReq.class);
            tradeReq.setOrderId(idGenerator.generateOrderId(tradeReq.getMchId()));
            return getSuccessResp(this.orderDispatcher.dispatch(tradeReq));
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }
}
