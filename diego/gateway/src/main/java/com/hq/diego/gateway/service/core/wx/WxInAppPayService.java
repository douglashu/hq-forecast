package com.hq.diego.gateway.service.core.wx;

import com.hq.diego.gateway.service.core.common.PrepayOrderGenService;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.resp.OrderPrepayResp;
import com.hq.diego.model.route.ChannelRoute;

/**
 * Created by zhaoyang on 10/5/16.
 */
public class WxInAppPayService extends WxCommonService implements PrepayOrderGenService {
    @Override
    public OrderPrepayResp genPrepayOrder(OrderTradeReq tradeReq, ChannelRoute route) {
        return null;
    }
}
