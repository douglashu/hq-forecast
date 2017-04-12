package com.hq.diego.gateway.service.core.common;

import com.hq.diego.model.req.OrderCancelReq;
import com.hq.diego.model.resp.OrderCancelResp;
import com.hq.diego.model.route.ChannelRoute;

/**
 * Created by zhaoyang on 06/12/2016.
 */
public interface OrderCancelService {

    String CANCEL_RESULT_SUCCESS = "SUCCESS";
    String CANCEL_RESULT_FAIL = "FAIL";
    String CANCEL_RESULT_CONTINUE = "CANCEL_RESULT_CONTINUE";
    String CANCEL_RESULT_UNKNOW = "UNKNOW";

    OrderCancelResp cancelOrder(OrderCancelReq cancelReq, ChannelRoute route);

}
