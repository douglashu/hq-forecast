package com.hq.diego.gateway.service.core.tfb;

import com.hq.diego.gateway.httpclient.executor.TfbServiceExecutor;
import com.hq.diego.gateway.service.core.common.OrderQueryService;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.req.tfb.TfbQueryOrderReq;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.diego.model.resp.tfb.TfbQueryResp;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 15/03/2017.
 */
@Service("TfbOrderQueryService")
public class TfbOrderQueryService implements OrderQueryService {

    @Autowired
    private TfbServiceExecutor executor;

    @Override
    public OrderTradeResp queryOrder(String tradeId, OrderTradeReq tradeReq, ChannelRoute route) {
        TfbQueryOrderReq queryOrderReq = new TfbQueryOrderReq();
        queryOrderReq.setSpid(route.getMchId());
        queryOrderReq.setSp_billno(tradeReq.getOrderId());
        if (PayChannels.WEIXIN_PAY.getCode().equals(tradeReq.getPayChannel())) {
            queryOrderReq.setReqUrl("/api_wx_pay_single_qry.cgi");
        } else if (PayChannels.ALI_PAY.getCode().equals(tradeReq.getPayChannel())) {
            queryOrderReq.setReqUrl("/api_ali_pay_qry.cgi");
        }
        OrderTradeResp tradeResp = new OrderTradeResp();
        try {
            TfbQueryResp response = this.executor.execute(queryOrderReq, TfbQueryResp.class, route.getKey());
            tradeResp.setOrderId(response.getData().getRecord().getSp_billno());
            tradeResp.settOrderId(response.getData().getRecord().getListid());
            tradeResp.setTtOrderId(response.getData().getRecord().getMerch_listid());
            tradeResp.setTradeState(
                    TradeState.fromTfbTradeState(response.getData().getRecord().getState()));
            tradeResp.setTotalAmount(response.getData().getRecord().getTran_amt());
            tradeResp.setReceiptAmount(tradeResp.getTotalAmount());
            tradeResp.setBuyerPayAmount(tradeResp.getTotalAmount());
            tradeResp.setSubOpenId(response.getData().getRecord().getSub_openid());
            /*
            tradeResp.setOpenId(response.getOpenid());
            tradeResp.setIsSubscribe(response.getIs_subscribe());
            tradeResp.setSubIsSubscribe(response.getSub_is_subscribe());
            */
            if(!StringUtils.isEmpty(response.getData().getRecord().getPay_time())) {
                String[] tm = response.getData().getRecord().getPay_time().split(" ");
                tradeResp.setEndDate(tm[0].replaceAll("-", ""));
                tradeResp.setEndTime(tm[1].replaceAll(":", ""));
            }
            if(TradeState.FAIL.equals(tradeResp.getTradeState())) {
                tradeResp.setErrCode(response.getRetcode());
                tradeResp.setErrCodeDes(response.getRetmsg());
            }
        } catch (Throwable th) {
            tradeResp.setTradeState(TradeState.UNKNOW);
            if(th instanceof CommonException) {
                CommonException ce = (CommonException) th;
                tradeResp.setErrCode(ce.getErrCode());
                tradeResp.setErrCodeDes(ce.getErrMsg());
            } else {
                tradeResp.setErrCode(CommonErrCode.UNKNOW_ERROR.getCode());
                tradeResp.setErrCodeDes(CommonErrCode.UNKNOW_ERROR.getDesc());
            }
        }
        return tradeResp;
    }

}
