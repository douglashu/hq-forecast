package com.hq.crash.model.statistics;

import java.math.BigDecimal;

/**
 * Created by zhaoyang on 05/02/2017.
 */
public class StatisticSummary {

    // 订单金额
    private BigDecimal totalAmount;
    // 订单笔数
    private Integer tradeCount;

    // 今日新增会员
    private Integer memberIncomesToday;
    // 今日卡券核销数
    private Integer couponVerifyCountToday;

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

    public Integer getMemberIncomesToday() {
        return memberIncomesToday;
    }

    public void setMemberIncomesToday(Integer memberIncomesToday) {
        this.memberIncomesToday = memberIncomesToday;
    }

    public Integer getCouponVerifyCountToday() {
        return couponVerifyCountToday;
    }

    public void setCouponVerifyCountToday(Integer couponVerifyCountToday) {
        this.couponVerifyCountToday = couponVerifyCountToday;
    }
}
