package com.hq.diego.gateway.service.core.route.chain;

import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.repository.model.generate.TRudyChannelRoute;
import com.hq.diego.repository.model.generate.TRudyMchChannel;
import com.hq.scrati.common.constants.trade.PayChannels;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyang on 15/03/2017.
 */
public class FixedChannelRouteChain implements ChannelRouteChain {

    @Override
    public List<TRudyChannelRoute> filter(OrderTradeReq tradeReq
            , List<TRudyMchChannel> mchChannels, List<TRudyChannelRoute> channelRoutes) {
        List<TRudyChannelRoute> availableRoutes = new ArrayList<>();
        PayChannels.PayChannel payChannel = PayChannels.fromString(tradeReq.getPayChannel());
        for (TRudyChannelRoute channelRoute: channelRoutes) {
            boolean found = false;
            for (TRudyMchChannel mchChannel: mchChannels) {
                if (channelRoute.getChannel().equals(mchChannel.getChannel())) {
                    found = true;
                    break;
                }
            }
            if (!found) continue;
            if ((payChannel.getValue() & channelRoute.getPayChannel()) != 0) {
                if (tradeReq.getTradeType().equals(channelRoute.getTradeType())) {
                    availableRoutes.add(channelRoute);
                }
            }
        }
        return availableRoutes;
    }
}
