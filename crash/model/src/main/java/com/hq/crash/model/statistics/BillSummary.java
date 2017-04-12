package com.hq.crash.model.statistics;

import java.math.BigDecimal;

/**
 * Created by zhaoyang on 05/02/2017.
 */
public class BillSummary {

    // 商家实收
    private BigDecimal receiptAmount;
    // 实收净额
    private BigDecimal incomeAmount;

    // 订单金额
    private BigDecimal totalAmount;
    // 订单笔数
    private Integer tradeCount;
    // 微信金额
    private BigDecimal wxTotalAmount;
    // 微信笔数
    private Integer wxTradeCount;
    // 支付宝金额
    private BigDecimal aliTotalAmount;
    // 支付宝笔数
    private Integer aliTradeCount;

    // 退款金额
    private BigDecimal refundAmount;
    // 退款笔数
    private Integer refundCount;

    // 手续费
    private BigDecimal rateFee;
    // 退手续费
    private BigDecimal refundRateFee;

    public BigDecimal getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(BigDecimal receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Integer tradeCount) {
        this.tradeCount = tradeCount;
    }

    public BigDecimal getWxTotalAmount() {
        return wxTotalAmount;
    }

    public void setWxTotalAmount(BigDecimal wxTotalAmount) {
        this.wxTotalAmount = wxTotalAmount;
    }

    public Integer getWxTradeCount() {
        return wxTradeCount;
    }

    public void setWxTradeCount(Integer wxTradeCount) {
        this.wxTradeCount = wxTradeCount;
    }

    public BigDecimal getAliTotalAmount() {
        return aliTotalAmount;
    }

    public void setAliTotalAmount(BigDecimal aliTotalAmount) {
        this.aliTotalAmount = aliTotalAmount;
    }

    public Integer getAliTradeCount() {
        return aliTradeCount;
    }

    public void setAliTradeCount(Integer aliTradeCount) {
        this.aliTradeCount = aliTradeCount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Integer getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(Integer refundCount) {
        this.refundCount = refundCount;
    }

    public BigDecimal getRateFee() {
        return rateFee;
    }

    public void setRateFee(BigDecimal rateFee) {
        this.rateFee = rateFee;
    }

    public BigDecimal getRefundRateFee() {
        return refundRateFee;
    }

    public void setRefundRateFee(BigDecimal refundRateFee) {
        this.refundRateFee = refundRateFee;
    }

}
