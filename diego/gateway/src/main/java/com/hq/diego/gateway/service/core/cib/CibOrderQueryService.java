package com.hq.diego.gateway.service.core.cib;

import com.hq.diego.gateway.httpclient.executor.CibServiceExecutor;
import com.hq.diego.gateway.service.core.common.CommonPayService;
import com.hq.diego.gateway.service.core.common.OrderQueryService;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.req.cib.CibQueryOrderReq;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.diego.model.resp.cib.CibPayResp;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;

/**
 * Created by zhaoyang on 03/03/2017.
 */
@Service("CibOrderQueryService")
public class CibOrderQueryService extends CommonPayService implements OrderQueryService {

    @Autowired
    private CibServiceExecutor executor;

    @Override
    public OrderTradeResp queryOrder(String tradeId, OrderTradeReq tradeReq, ChannelRoute route) {
        CibQueryOrderReq req = new CibQueryOrderReq();
        req.setService("unified.trade.query");
        req.setVersion("2.0");
        req.setMch_id(route.getMchId());
        req.setOut_trade_no(tradeReq.getOrderId());
        OrderTradeResp tradeResp = new OrderTradeResp();
        try {
            CibPayResp payResp = this.executor.execute(req, CibPayResp.class, route.getKey());
            if ("0".equals(payResp.getStatus())) {
                if ("0".equals(payResp.getResult_code())) {
                    tradeResp.setTradeState(TradeState.fromCibTradeState(payResp.getTrade_state()));
                    if ("SUCCESS".equals(payResp.getTrade_state())) {
                        tradeResp.setOrderId(payResp.getOut_trade_no());
                        tradeResp.settOrderId(payResp.getTransaction_id());
                        tradeResp.setTtOrderId(payResp.getOut_transaction_id());
                        tradeResp.setTradeState(TradeState.fromCibTradeState(payResp.getTrade_state()));
                        if(!StringUtils.isEmpty(payResp.getTime_end())) {
                            tradeResp.setEndDate(payResp.getTime_end().substring(0, 8));
                            tradeResp.setEndTime(payResp.getTime_end().substring(8));
                        }
                        tradeResp.setTotalAmount(payResp.getTotal_fee());
                        if ("pay.weixin.micropay".equals(payResp.getTrade_type())) {
                            tradeResp.setReceiptAmount(payResp.getTotal_fee());
                            tradeResp.setBuyerPayAmount(payResp.getTotal_fee());
                            tradeResp.setOpenId(payResp.getOpenid());
                            tradeResp.setIsSubscribe(payResp.getIs_subscribe());
                            tradeResp.setSubOpenId(payResp.getSub_openid());
                            tradeResp.setSubIsSubscribe(payResp.getSub_is_subscribe());
                            tradeResp.setBankType(payResp.getBank_type());
                        } else if ("pay.alipay.micropay".equals(payResp.getTrade_type())) {
                            BigDecimal receiptAmt = toPrice(payResp.getReceipt_amount());
                            if(receiptAmt != null) {
                                tradeResp.setReceiptAmount(receiptAmt.intValue());
                            } else {
                                tradeResp.setReceiptAmount(tradeResp.getTotalAmount());
                            }
                            BigDecimal buyerPayAmt = toPrice(payResp.getBuyer_pay_amount());
                            if(buyerPayAmt != null) {
                                tradeResp.setBuyerPayAmount(buyerPayAmt.intValue());
                            } else {
                                tradeResp.setBuyerPayAmount(tradeResp.getReceiptAmount());
                            }
                            tradeResp.settUserId(payResp.getOpenid());
                        }
                    }
                } else {
                    tradeResp.setTradeState(TradeState.UNKNOW);
                    tradeResp.setErrCode(payResp.getErr_code());
                    tradeResp.setErrCodeDes(payResp.getErr_msg());
                }
            } else if("400".equals(payResp.getStatus())) {
                tradeResp.setTradeState(TradeState.FAIL);
                tradeResp.setErrCode(payResp.getStatus());
                tradeResp.setErrCodeDes(payResp.getMessage());
            } else {
                tradeResp.setTradeState(TradeState.UNKNOW);
                tradeResp.setErrCode(payResp.getStatus());
                tradeResp.setErrCodeDes(payResp.getMessage());
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
