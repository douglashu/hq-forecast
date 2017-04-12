package com.hq.diego.gateway.service.core.route.chain;

import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.repository.model.generate.TRudyChannelRoute;
import com.hq.diego.repository.model.generate.TRudyMchChannel;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zhaoyang on 14/03/2017.
 */
public class PriorityChannelRouteChain implements ChannelRouteChain {

    @Override
    public List<TRudyChannelRoute> filter(OrderTradeReq tradeReq
            , List<TRudyMchChannel> mchChannels, List<TRudyChannelRoute> channelRoutes) {
        channelRoutes.sort(new Comparator<TRudyChannelRoute>() {
            @Override
            public int compare(TRudyChannelRoute o1, TRudyChannelRoute o2) {
                return o2.getPriority().compareTo(o1.getPriority());
            }
        });
        return Arrays.asList(channelRoutes.get(0));
    }

}
