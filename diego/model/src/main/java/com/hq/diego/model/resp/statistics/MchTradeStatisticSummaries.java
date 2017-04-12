package com.hq.diego.model.resp.statistics;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhaoyang on 03/02/2017.
 */
public class MchTradeStatisticSummaries {

    private BigDecimal totalAmount;
    private BigDecimal receiptAmount;
    private BigDecimal rateFee;
    private Integer tradeCount;
    private List<MchTradeStatistics> statisticItems;

    public List<MchTradeStatistics> getStatisticItems() {
        return statisticItems;
    }

    public void setStatisticItems(List<MchTradeStatistics> statisticItems) {
        this.statisticItems = statisticItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(BigDecimal receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public BigDecimal getRateFee() {
        return rateFee;
    }

    public void setRateFee(BigDecimal rateFee) {
        this.rateFee = rateFee;
    }

    public Integer getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Integer tradeCount) {
        this.tradeCount = tradeCount;
    }
}
