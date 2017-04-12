package com.hq.diego.gateway.service.core.cib;

import com.hq.diego.gateway.httpclient.executor.CibServiceExecutor;
import com.hq.diego.gateway.service.core.common.OrderCancelService;
import com.hq.diego.model.req.OrderCancelReq;
import com.hq.diego.model.req.cib.CibCancelOrderReq;
import com.hq.diego.model.resp.OrderCancelResp;
import com.hq.diego.model.resp.cib.CibCancelResp;
import com.hq.diego.model.route.ChannelRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 04/03/2017.
 */
@Service("CibOrderCancelService")
public class CibOrderCancelService implements OrderCancelService {

    @Autowired
    private CibServiceExecutor executor;

    @Override
    public OrderCancelResp cancelOrder(OrderCancelReq cancelReq, ChannelRoute route) {
        CibCancelOrderReq cancelOrderReq = new CibCancelOrderReq();
        cancelOrderReq.setService("unified.micropay.reverse");
        cancelOrderReq.setVersion("2.0");
        cancelOrderReq.setMch_id(route.getMchId());
        cancelOrderReq.setOut_trade_no(cancelReq.getOrderId());
        OrderCancelResp cancelResp = new OrderCancelResp();
        CibCancelResp cibCancelResp = this.executor.execute(
                cancelOrderReq, CibCancelResp.class, route.getKey());
        if ("0".equals(cibCancelResp.getStatus())) {
            if ("0".equals(cibCancelResp.getResult_code())) {
                cancelResp.setResult(CANCEL_RESULT_SUCCESS);
            } else {
                cancelResp.setErrCode(cibCancelResp.getErr_code());
                cancelResp.setErrCodeDes(cibCancelResp.getErr_msg());
                cancelResp.setResult(CANCEL_RESULT_FAIL);
            }
        } else {
            cancelResp.setErrCodeDes(cibCancelResp.getMessage());
            cancelResp.setResult(CANCEL_RESULT_UNKNOW);
        }
        return cancelResp;
    }

}
