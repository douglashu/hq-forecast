package com.hq.diego.gateway.service.core;

import java.math.BigDecimal;

/**
 * Created by zhaoyang on 04/02/2017.
 */
public class RateCalcUtil {

    public static void main(String[] args) {
        System.out.println(getRateFee(83, BigDecimal.valueOf(0.006)));
        System.out.println(getRateFee(272, BigDecimal.valueOf(0.0055)));
    }

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    public static BigDecimal getRateFee(Long tradeAmount, BigDecimal rate) {
        BigDecimal result = BigDecimal.valueOf(tradeAmount)
                .divide(ONE_HUNDRED).multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
        return result;
    }

    public static BigDecimal getRateFee(Integer tradeAmount, BigDecimal rate) {
        return getRateFee(tradeAmount * 1L, rate);
    }

    public static Long getRateFeeAsCentUnit(Integer tradeAmount, BigDecimal rate) {
        return getRateFee(tradeAmount, rate).multiply(ONE_HUNDRED).longValue();
    }

    public static Long getRateFeeAsCentUnit(Long tradeAmount, BigDecimal rate) {
        return getRateFee(tradeAmount, rate).multiply(ONE_HUNDRED).longValue();
    }

}
