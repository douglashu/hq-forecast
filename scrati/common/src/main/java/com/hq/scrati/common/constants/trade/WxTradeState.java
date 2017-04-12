package com.hq.scrati.common.constants.trade;

/**
 * Created by zhaoyang on 24/11/2016.
 */
public interface WxTradeState {
    // 支付成功
    String SUCCESS = "SUCCESS";
    // 转入退款
    String REFUND = "REFUND";
    // 未支付
    String NOTPAY = "NOTPAY";
    // 已关闭
    String CLOSED = "CLOSED";
    // 已撤销(刷卡支付)
    String REVOKED = "REVOKED";
    // 用户支付中
    String USERPAYING = "USERPAYING";
    //支付失败(其他原因，如银行返回失败)
    String PAYERROR = "PAYERROR";
}
