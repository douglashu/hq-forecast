package com.hq.diego.gateway.service.core.common;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * Created by zhaoyang on 25/11/2016.
 */
public class CommonPayService {

    public static BigDecimal getPrice(Integer cent) {
        if(cent == null) return null;
        return BigDecimal.valueOf(cent).divide(BigDecimal.valueOf(100));
    }

    public static BigDecimal toPrice(String price) {
        if(StringUtils.isEmpty(price)) return null;
        BigDecimal decimal = new BigDecimal(price);
        return decimal.multiply(BigDecimal.valueOf(100));
    }

}
