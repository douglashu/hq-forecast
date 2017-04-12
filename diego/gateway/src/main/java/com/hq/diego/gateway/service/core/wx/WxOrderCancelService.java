package com.hq.diego.gateway.service.core.wx;

import com.hq.diego.gateway.constant.url.WxPayURL;
import com.hq.diego.gateway.httpclient.executor.WxServiceExecutor;
import com.hq.diego.gateway.service.core.common.OrderCancelService;
import com.hq.diego.model.req.OrderCancelReq;
import com.hq.diego.model.req.wx.WxCancelOrderReq;
import com.hq.diego.model.resp.OrderCancelResp;
import com.hq.diego.model.resp.wx.WxCancelOrderResp;
import com.hq.diego.model.route.ChannelRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 06/12/2016.
 */
@Service("WxOrderCancelService")
public class WxOrderCancelService implements OrderCancelService {

    @Autowired
    private WxServiceExecutor executor = new WxServiceExecutor();

    @Override
    public OrderCancelResp cancelOrder(OrderCancelReq cancelReq, ChannelRoute route) {
        WxCancelOrderReq cancelOrderReq = new WxCancelOrderReq();
        // cancelOrderReq.setTransaction_id(queryReq.gettOrderId());
        cancelOrderReq.setOut_trade_no(cancelReq.getOrderId());
        cancelOrderReq.setSub_appid(route.getAppId());
        cancelOrderReq.setSub_mch_id(route.getMchId());

        OrderCancelResp cancelResp = new OrderCancelResp();
        WxCancelOrderResp response = this.executor.execute(
                WxPayURL.getCancelOrderUrl(), cancelOrderReq, WxCancelOrderResp.class);
        if("SUCCESS".equals(response.getReturn_code())) {
            // 是否需要重试
            if("Y".equals(response.getRecall())) {
                cancelResp.setErrCode(response.getErr_code());
                cancelResp.setErrCodeDes(response.getErr_code_des());
                cancelResp.setResult(CANCEL_RESULT_CONTINUE);
            } else {
                if ("SUCCESS".equals(response.getResult_code())) {
                    cancelResp.setResult(CANCEL_RESULT_SUCCESS);
                } else {
                    cancelResp.setErrCode(response.getErr_code());
                    cancelResp.setErrCodeDes(response.getErr_code_des());
                    if ("SYSTEMERROR".equals(response.getResult_code())) {
                        cancelResp.setResult(CANCEL_RESULT_UNKNOW);
                    } else {
                        cancelResp.setResult(CANCEL_RESULT_FAIL);
                    }
                }
            }
        } else {
            cancelResp.setErrCode(response.getReturn_code());
            cancelResp.setErrCodeDes(response.getReturn_msg());
            cancelResp.setResult(CANCEL_RESULT_FAIL);
        }
        return cancelResp;
    }

}
