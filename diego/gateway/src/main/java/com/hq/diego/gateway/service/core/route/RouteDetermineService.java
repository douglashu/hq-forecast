package com.hq.diego.gateway.service.core.route;

import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.route.ChannelRoute;

/**
 * Created by zhaoyang on 08/03/2017.
 */
public interface RouteDetermineService {

    ChannelRoute determine(OrderTradeReq tradeReq);

}
