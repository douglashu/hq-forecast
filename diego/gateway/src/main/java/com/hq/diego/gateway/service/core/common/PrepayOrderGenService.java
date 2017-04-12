package com.hq.diego.gateway.service.core.common;

import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.resp.OrderPrepayResp;
import com.hq.diego.model.route.ChannelRoute;

/**
 * Created by zhaoyang on 26/11/2016.
 */
public interface PrepayOrderGenService {
    OrderPrepayResp genPrepayOrder(OrderTradeReq tradeReq, ChannelRoute route);
}
