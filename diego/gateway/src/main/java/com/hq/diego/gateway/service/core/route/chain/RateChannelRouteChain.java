package com.hq.diego.gateway.service.core.route.chain;

import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.repository.model.generate.TRudyChannelRoute;
import com.hq.diego.repository.model.generate.TRudyMchChannel;

import java.util.List;

/**
 * Created by zhaoyang on 14/03/2017.
 */
public class RateChannelRouteChain implements ChannelRouteChain {

    @Override
    public List<TRudyChannelRoute> filter(OrderTradeReq tradeReq
            , List<TRudyMchChannel> mchChannels, List<TRudyChannelRoute> channelRoutes) {
        return null;
    }

}
