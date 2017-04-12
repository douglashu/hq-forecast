package com.hq.scrati.common.constants.trade;

import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 10/5/16.
 */
public class TradeTypes {

    // 公众号内H5网页JSAPI支付
    public static final TradeType H5_JSAPI = new TradeType("H5网页JSAPI支付", "H5_JSAPI", 1, "H5PayService");

    // 用户扫商家二维码 (订单码)
    public static final TradeType ORDER_CODE = new TradeType("订单码", "ORDER_CODE", 2, "QrCodePayService");

    // APP支付
    public static final TradeType APP = new TradeType("APP支付", "APP", 3, "InAppPayService");

    // 刷卡支付
    public static final TradeType SWIPE_CARD = new TradeType("刷卡支付", "SWIPE_CARD", 4, "SwipeCardPayService");

    public static TradeType fromString(String name) {
        if(StringUtils.isEmpty(name)) return null;
        switch (name) {
            case "H5_JSAPI":
                return H5_JSAPI;
            case "ORDER_CODE":
                return ORDER_CODE;
            case "APP":
                return APP;
            case "SWIPE_CARD":
                return SWIPE_CARD;
            default:
                return null;
        }
    }

    public static TradeType fromValue(Integer value) {
        if(value == null) return null;
        switch (value) {
            case 1:
                return H5_JSAPI;
            case 2:
                return ORDER_CODE;
            case 3:
                return APP;
            case 4:
                return SWIPE_CARD;
            default:
                return null;
        }
    }

    public static class TradeType {
        private String name;
        private String code;
        private Integer value;
        private String beanSuffix;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TradeType tradeType = (TradeType) o;
            return value.equals(tradeType.value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        public TradeType() { }

        public TradeType(String name, String code, Integer value, String beanSuffix) {
            this.name = name;
            this.code = code;
            this.value = value;
            this.beanSuffix = beanSuffix;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getBeanSuffix() {
            return beanSuffix;
        }

        public void setBeanSuffix(String beanSuffix) {
            this.beanSuffix = beanSuffix;
        }
    }

}
