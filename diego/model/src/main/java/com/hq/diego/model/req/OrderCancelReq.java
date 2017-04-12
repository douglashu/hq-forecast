package com.hq.diego.model.req;

import com.hq.diego.model.TradeCommonReq;

/**
 * Created by zhaoyang on 07/12/2016.
 */
public class OrderCancelReq extends TradeCommonReq {

    private String tradeId;
    private String orderId;
    private String tOrderId;
    private Integer totalAmount;
    private String payChannel;

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String gettOrderId() {
        return tOrderId;
    }

    public void settOrderId(String tOrderId) {
        this.tOrderId = tOrderId;
    }
}
