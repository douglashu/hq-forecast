package com.hq.diego.gateway.service.core.common;

import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.diego.model.route.ChannelRoute;

/**
 * Created by zhaoyang on 05/12/2016.
 */
public interface OrderQueryService {

    OrderTradeResp queryOrder(String tradeId, OrderTradeReq tradeReq, ChannelRoute route);

}
