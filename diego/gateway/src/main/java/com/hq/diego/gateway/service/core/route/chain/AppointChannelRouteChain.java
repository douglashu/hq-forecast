package com.hq.diego.gateway.service.core.route.chain;

import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.repository.model.generate.TRudyChannelRoute;
import com.hq.diego.repository.model.generate.TRudyMchChannel;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyang on 14/03/2017.
 */
public class AppointChannelRouteChain implements ChannelRouteChain {

    @Override
    public List<TRudyChannelRoute> filter(OrderTradeReq tradeReq
            , List<TRudyMchChannel> mchChannels, List<TRudyChannelRoute> channelRoutes) {
        if (StringUtils.isEmpty(tradeReq.getTradeChannel())) return channelRoutes;
        List<TRudyChannelRoute> availableRoutes = new ArrayList<>();
        for(TRudyChannelRoute channelRoute: channelRoutes) {
            if (channelRoute.getChannel().equals(tradeReq.getTradeChannel())) {
                availableRoutes.add(channelRoute);
                return availableRoutes;
            }
        }
        return availableRoutes;
    }

}
