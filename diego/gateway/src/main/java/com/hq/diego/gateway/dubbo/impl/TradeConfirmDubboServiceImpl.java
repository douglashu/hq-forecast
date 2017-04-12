package com.hq.diego.gateway.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.hq.diego.gateway.dubbo.TradeConfirmDubboService;
import com.hq.diego.gateway.service.core.TradeCallbackDispatcher;
import com.hq.diego.model.req.TradeNotifyReq;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoyang on 30/01/2017.
 */
@Service(interfaceName = "com.hq.diego.gateway.dubbo.TradeConfirmDubboService", version = "1.0")
public class TradeConfirmDubboServiceImpl extends DubboBaseService implements TradeConfirmDubboService {

    private static final Logger logger = Logger.getLogger(TradeConfirmDubboServiceImpl.class);

    @Autowired
    private TradeCallbackDispatcher tradeCallbackDispatcher;

    @Override
    public RespEntity onTradeNotify(HqRequest request) {
        TradeNotifyReq tradeNotifyReq = JSON.parseObject(request.getBizContent(), TradeNotifyReq.class);
        String respStr = this.tradeCallbackDispatcher.dispatch(tradeNotifyReq);
        logger.info("<<< [" + tradeNotifyReq.getService()
                + "] Trade Notify On Process Result(" + respStr + ")");
        return getSuccessResp(respStr);
    }

}
