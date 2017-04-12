package com.hq.scrati.common.constants.trade;

/**
 * Created by zhaoyang on 30/11/2016.
 */
public interface AliTradeState {

    // 交易创建，等待买家付款
    String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
    // 未付款交易超时关闭，或支付完成后全额退款
    String TRADE_CLOSED = "TRADE_CLOSED";
    // 交易支付成功
    String TRADE_SUCCESS = "TRADE_SUCCESS";
    // 交易结束，不可退款
    String TRADE_FINISHED = "TRADE_FINISHED";

}
