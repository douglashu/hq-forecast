package com.hq.diego.gateway.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.hq.diego.gateway.dubbo.TradeChannelDubboService;
import com.hq.diego.gateway.service.core.route.ChannelRouteService;
import com.hq.diego.model.req.channel.TradeChannelReq;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.util.MapBuilder;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoyang on 22/03/2017.
 */
@Service(interfaceName = "com.hq.diego.gateway.dubbo.TradeChannelDubboService", version = "1.0")
public class TradeChannelDubboServiceImpl extends DubboBaseService implements TradeChannelDubboService {

    @Autowired
    private ChannelRouteService channelRouteService;

    @Override
    public RespEntity getTradeChannels() {
        try {
            return getSuccessResp(this.channelRouteService.getChannelRoutesFromDB());
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

    @Override
    public RespEntity updateCache() {
        try {
            return getSuccessResp("刷新成功", null);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

    @Override
    public RespEntity createTradeChannel(HqRequest request) {
        try {
            TradeChannelReq tradeChannelReq = JSON.parseObject(
                    request.getBizContent(), TradeChannelReq.class);
            String id = this.channelRouteService.saveChannelRoute(tradeChannelReq, true);
            return getSuccessResp(MapBuilder.create("id", id).get());
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

    @Override
    public RespEntity updateTradeChannel(HqRequest request) {
        try {
            TradeChannelReq tradeChannelReq = JSON.parseObject(
                    request.getBizContent(), TradeChannelReq.class);
            String id = this.channelRouteService.saveChannelRoute(tradeChannelReq, false);
            return getSuccessResp(MapBuilder.create("id", id).get());
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

}
