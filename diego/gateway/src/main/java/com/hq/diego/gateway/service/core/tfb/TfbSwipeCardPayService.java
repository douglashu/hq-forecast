package com.hq.diego.gateway.service.core.tfb;

import com.hq.diego.gateway.constant.config.GatewayConfig;
import com.hq.diego.gateway.httpclient.executor.TfbServiceExecutor;
import com.hq.diego.gateway.service.core.common.CommonPayService;
import com.hq.diego.gateway.service.core.common.SwipeCardPayService;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.req.tfb.TfbTradeReq;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.diego.model.resp.tfb.TfbPayResp;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 14/03/2017.
 */
@Service("TfbSwipeCardPayService")
public class TfbSwipeCardPayService extends CommonPayService implements SwipeCardPayService {

    private static final Logger logger = Logger.getLogger(TfbSwipeCardPayService.class);

    @Autowired
    private TfbServiceExecutor executor;

    @Override
    public OrderTradeResp onSwipePay(OrderTradeReq tradeReq, ChannelRoute route) {
        final TfbTradeReq tfbReq = new TfbTradeReq();
        tfbReq.setSp_billno(tradeReq.getOrderId());
        tfbReq.setSpbill_create_ip(tradeReq.getIpAddress());
        tfbReq.setPay_type("800208");
        tfbReq.setCur_type(tradeReq.getFeeType());
        tfbReq.setAuth_code(tradeReq.getAuthCode());
        tfbReq.setItem_name(tradeReq.getTitle());
        tfbReq.setItem_attach(tradeReq.getBody());
        tfbReq.setTran_time(DateTime.now().toString("YYYYMMddHHmmss"));
        tfbReq.setTran_amt(tradeReq.getTotalAmount());
        tfbReq.setNotify_url(GatewayConfig.TFB_NOTIFY_URL);
        tfbReq.setSpid(route.getMchId());
        if (PayChannels.WEIXIN_PAY.getCode().equals(tradeReq.getPayChannel())) {



            tfbReq.setBank_mch_id("7656765");
            tfbReq.setBank_mch_name("rrrr");



            tfbReq.setReqUrl("/api_wx_pay_apply.cgi");
        } else if (PayChannels.ALI_PAY.getCode().equals(tradeReq.getPayChannel())) {
            tfbReq.setReqUrl("/api_ali_pay_apply.cgi");
        }
        final OrderTradeResp tradeResp = new OrderTradeResp();
        tradeResp.from(tradeReq);
        try {
            TfbPayResp tfbPayResp = this.executor.execute(tfbReq, TfbPayResp.class, route.getKey());
            if ("00".equals(tfbPayResp.getRetcode())) {
                // 支付成功, 更新交易流水,返回成功消息
                tradeResp.settOrderId(tfbPayResp.getListid());
                tradeResp.setTtOrderId(tfbPayResp.getMerch_listid());
                tradeResp.setTradeState(TradeState.SUCCESS);
                tradeResp.setReceiptAmount(tfbPayResp.getTran_amt());
                tradeResp.setBuyerPayAmount(tfbPayResp.getTran_amt());
                if(!StringUtils.isEmpty(tfbPayResp.getSysd_time())) {
                    tradeResp.setEndDate(tfbPayResp.getSysd_time().substring(0, 8));
                    tradeResp.setEndTime(tfbPayResp.getSysd_time().substring(8));
                }
            } else {
                if ("205235".equals(tfbPayResp.getRetcode())) {
                    tradeResp.setTradeState(TradeState.WAIT_PAY);
                } else {
                    // 支付失败
                    logger.warn("<<< Tfb Swipe Pay Fail(ErrCode=" + tfbPayResp.getRetcode()
                            + ", ErrCodeDes=" + tfbPayResp.getRetmsg() + ")");
                    tradeResp.setErrCode(tfbPayResp.getRetcode());
                    tradeResp.setErrCodeDes(tfbPayResp.getRetmsg());
                    tradeResp.setTradeState(TradeState.FAIL);
                }
            }
        } catch (Throwable th) {
            logger.warn("<<< Tfb Swipe Pay UnKnow", th);
            tradeResp.setTradeState(TradeState.UNKNOW);
            tradeResp.setErrCode(CommonErrCode.UNKNOW_ERROR.getCode());
            tradeResp.setErrCodeDes(CommonErrCode.UNKNOW_ERROR.getDesc());
        }
        return tradeResp;
    }

}
