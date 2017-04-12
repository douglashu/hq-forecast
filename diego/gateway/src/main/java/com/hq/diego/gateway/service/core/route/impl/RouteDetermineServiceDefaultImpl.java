package com.hq.diego.gateway.service.core.route.impl;

import com.hq.diego.gateway.service.core.route.ChannelRouteService;
import com.hq.diego.gateway.service.core.route.MchChannelService;
import com.hq.diego.gateway.service.core.route.RouteDetermineService;
import com.hq.diego.gateway.service.core.route.chain.AppointChannelRouteChain;
import com.hq.diego.gateway.service.core.route.chain.ChannelRouteChain;
import com.hq.diego.gateway.service.core.route.chain.FixedChannelRouteChain;
import com.hq.diego.gateway.service.core.route.chain.PriorityChannelRouteChain;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.diego.repository.model.generate.TRudyChannelRoute;
import com.hq.diego.repository.model.generate.TRudyMchChannel;
import com.hq.scrati.common.constants.trade.TradeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhaoyang on 08/03/2017.
 */
@Service("RouteDetermineServiceDefaultImpl")
public class RouteDetermineServiceDefaultImpl implements RouteDetermineService {

    @Autowired
    private ChannelRouteService channelRouteService;

    @Autowired
    private MchChannelService mchChannelService;

    private List<ChannelRouteChain> routeChains;

    private ChannelRouteChain fixedChannelRouteChain = new FixedChannelRouteChain();

    @PostConstruct
    public void initRouteChains() {
        this.routeChains = Arrays.asList(
                new AppointChannelRouteChain(),
                new PriorityChannelRouteChain()
        );
    }

    @Override
    public ChannelRoute determine(OrderTradeReq tradeReq) {

        // 找出系统支持的所有通道
        List<TRudyChannelRoute> channelRoutes = this.channelRouteService.getChannelRoutes();
        if (channelRoutes == null || channelRoutes.size() == 0) return null;

        // 找出商户开通的通道列表
        List<TRudyMchChannel> mchChannels = this.mchChannelService.getMchChannels(tradeReq.getMchId());
        if (mchChannels == null || mchChannels.size() == 0) return null;

        // 找出支持此交易类型的所有通道
        List<TRudyChannelRoute> availableRoutes = channelRoutes;

        // 初步筛选
        availableRoutes = this.fixedChannelRouteChain.filter(tradeReq, mchChannels, availableRoutes);

        // 进入过滤链找出最合适的通道
        for (ChannelRouteChain routeChain: this.routeChains) {
            availableRoutes = routeChain.filter(tradeReq, mchChannels, availableRoutes);
            if (availableRoutes.size() == 0) return null;
            if (availableRoutes.size() == 1) {
                return getAndConvertFirst(tradeReq, availableRoutes, mchChannels);
            }
        }

        return getAndConvertFirst(tradeReq, availableRoutes, mchChannels);
    }

    private ChannelRoute getAndConvertFirst(OrderTradeReq tradeReq,
            List<TRudyChannelRoute> availableRoutes, List<TRudyMchChannel> mchChannels) {
        TRudyChannelRoute channelRoute = availableRoutes.get(0);
        TRudyMchChannel mchChannel = null;
        for (TRudyMchChannel item: mchChannels) {
            if (item.getChannel().equals(channelRoute.getChannel())) {
                mchChannel = item;
                break;
            }
        }
        TradeTypes.TradeType tradeType = TradeTypes.fromString(tradeReq.getTradeType());
        ChannelRoute route = new ChannelRoute();
        route.setId(channelRoute.getChannel());
        route.setAppId(mchChannel.getAppId());
        route.setMchId(mchChannel.getMchId());
        route.setKey(mchChannel.getApiKey());
        if (TradeTypes.SWIPE_CARD.equals(tradeType)
                || TradeTypes.ORDER_CODE.equals(tradeType)) {
            route.setRate(mchChannel.getCodePayRate());
        } else if (TradeTypes.H5_JSAPI.equals(tradeType)) {
            route.setRate(mchChannel.getH5PayRate());
        }
        route.setTradeService(channelRoute.getTradeService());
        route.setQueryService(channelRoute.getQueryService());
        route.setCancelService(channelRoute.getCancelService());
        return route;
    }

}
