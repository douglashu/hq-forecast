package com.hq.diego.gateway.service.core.cib;

import com.hq.diego.gateway.constant.config.GatewayConfig;
import com.hq.diego.gateway.httpclient.executor.CibServiceExecutor;
import com.hq.diego.gateway.service.core.common.CommonPayService;
import com.hq.diego.gateway.service.core.common.SwipeCardPayService;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.req.cib.CibSwipeCardPayReq;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.diego.model.resp.cib.CibPayResp;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;

/**
 * Created by zhaoyang on 02/03/2017.
 */
@Service("CibSwipeCardPayService")
public class CibSwipeCardPayService extends CommonPayService implements SwipeCardPayService {

    private static final Logger logger = Logger.getLogger(CibSwipeCardPayService.class);

    @Autowired
    private CibServiceExecutor executor;

    @Override
    public OrderTradeResp onSwipePay(OrderTradeReq tradeReq, ChannelRoute route) {
        final CibSwipeCardPayReq payReq = new CibSwipeCardPayReq();
        DateTime now = DateTime.now();
        payReq.setService("unified.trade.micropay");
        payReq.setVersion("2.0");
        payReq.setMch_id(route.getMchId());
        payReq.setOut_trade_no(tradeReq.getOrderId());
        payReq.setDevice_info(tradeReq.getTerminalId());
        payReq.setBody(tradeReq.getTitle());
        payReq.setTotal_fee(tradeReq.getTotalAmount());
        payReq.setMch_create_ip(tradeReq.getIpAddress());
        payReq.setAuth_code(tradeReq.getAuthCode());
        payReq.setTime_start(now.toString("YYYYMMddHHmmss"));
        payReq.setAttach(tradeReq.getCustom());
        payReq.setTime_expire(now.plusMinutes(GatewayConfig
                .ORDER_DEFAULT_EXPIRE_MINUTES).toString("YYYYMMddHHmmss"));
        payReq.setOp_user_id(tradeReq.getOperatorId());

        final OrderTradeResp tradeResp = new OrderTradeResp();
        tradeResp.from(tradeReq);
        try {
            CibPayResp payResp = this.executor.execute(payReq, CibPayResp.class, route.getKey());
            if ("0".equals(payResp.getStatus())) {
                if ("0".equals(payResp.getResult_code()) && 0 == payResp.getPay_result()) {
                    tradeResp.setTradeState(TradeState.SUCCESS);
                    tradeResp.setTotalAmount(payResp.getTotal_fee());
                    tradeResp.settOrderId(payResp.getTransaction_id());
                    tradeResp.setTtOrderId(payResp.getOut_transaction_id());
                    if(!StringUtils.isEmpty(payResp.getTime_end())) {
                        tradeResp.setEndDate(payResp.getTime_end().substring(0, 8));
                        tradeResp.setEndTime(payResp.getTime_end().substring(8));
                    }
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
                } else {
                    if ("Y".equals(payResp.getNeed_query())) {
                        // 需要查询订单状态
                        if ("USERPAYING".equals(payResp.getErr_code())) {
                            tradeResp.setTradeState(TradeState.WAIT_PAY);
                        } else {
                            tradeResp.setTradeState(TradeState.UNKNOW);
                        }
                    } else {
                        tradeResp.setTradeState(TradeState.FAIL);
                        tradeResp.setErrCode(payResp.getErr_code());
                        tradeResp.setErrCodeDes(payResp.getErr_msg());
                    }
                }
            } else if ("400".equals(payResp.getStatus())) {
                logger.warn("<<< Cib Swipe Pay Fail(" + payResp.getMessage() + ")");
                tradeResp.setTradeState(TradeState.FAIL);
                tradeResp.setErrCode(payResp.getStatus());
                tradeResp.setErrCodeDes(payResp.getMessage());
            } else {
                // 通讯失败或者未知, 需要查询订单状态
                tradeResp.setTradeState(TradeState.UNKNOW);
                tradeResp.setErrCode(payResp.getStatus());
                tradeResp.setErrCodeDes(payResp.getMessage());
            }
        } catch (Throwable th) {
            logger.warn("<<< Cib Swipe Pay UnKnow", th);
            tradeResp.setTradeState(TradeState.UNKNOW);
            tradeResp.setErrCode(CommonErrCode.UNKNOW_ERROR.getCode());
            tradeResp.setErrCodeDes(CommonErrCode.UNKNOW_ERROR.getDesc());
        }
        return tradeResp;
    }
}
