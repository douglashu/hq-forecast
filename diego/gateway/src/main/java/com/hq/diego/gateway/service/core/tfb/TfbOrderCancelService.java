package com.hq.diego.gateway.service.core.tfb;

import com.hq.diego.gateway.httpclient.executor.TfbServiceExecutor;
import com.hq.diego.gateway.service.core.common.OrderCancelService;
import com.hq.diego.model.req.OrderCancelReq;
import com.hq.diego.model.req.tfb.TfbCancelOrderReq;
import com.hq.diego.model.resp.OrderCancelResp;
import com.hq.diego.model.resp.tfb.TfbCancelResp;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.constants.trade.PayChannels;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 15/03/2017.
 */
@Service("TfbOrderCancelService")
public class TfbOrderCancelService implements OrderCancelService {

    @Autowired
    private TfbServiceExecutor executor;

    @Override
    public OrderCancelResp cancelOrder(OrderCancelReq cancelReq, ChannelRoute route) {
        TfbCancelOrderReq cancelOrderReq = new TfbCancelOrderReq();
        cancelOrderReq.setSpid(route.getMchId());
        cancelOrderReq.setSp_billno(cancelReq.getOrderId());
        cancelOrderReq.setTran_time(DateTime.now().toString("YYYYMMddHHmmss"));
        cancelOrderReq.setTran_amt(cancelReq.getTotalAmount());
        if (PayChannels.WEIXIN_PAY.getCode().equals(cancelReq.getPayChannel())) {
            cancelOrderReq.setReqUrl("/api_wx_pay_cancel.cgi");
        } else if (PayChannels.ALI_PAY.getCode().equals(cancelReq.getPayChannel())) {
            cancelOrderReq.setReqUrl("/api_ali_pay_cancel.cgi");
        }
        OrderCancelResp cancelResp = new OrderCancelResp();
        TfbCancelResp tfbCancelResp = this.executor.execute(cancelOrderReq, TfbCancelResp.class, route.getKey());
        if ("00".equals(tfbCancelResp.getRetcode())) {
            cancelResp.setResult(CANCEL_RESULT_SUCCESS);
        } else {
            cancelResp.setErrCode(tfbCancelResp.getRetcode());
            cancelResp.setErrCodeDes(tfbCancelResp.getRetmsg());
            cancelResp.setResult(CANCEL_RESULT_FAIL);
        }
        return cancelResp;
    }

}
