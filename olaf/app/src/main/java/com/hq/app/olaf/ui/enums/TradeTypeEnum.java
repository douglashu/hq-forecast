package com.hq.app.olaf.ui.enums;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/1/23.
 */

public enum TradeTypeEnum {
    DEFAULT( "DEFAULT", "其他"),
    //所有的交易状态
    SWIPE_CARD("SWIPE_CARD","扫码支付"),
    H5_JSAPI("H5_JSAPI","二维码支付"),


    ORDER_CODE("ORDER_CODE","扫码支付");


    private  String code;
    private  String desc;

    TradeTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public static TradeTypeEnum getType(String code) {
        if (TextUtils.isEmpty(code)) return DEFAULT;
        switch (code) {
            case "SWIPE_CARD":
                return SWIPE_CARD;
            case "H5_JSAPI":
                return H5_JSAPI;
            case "ORDER_CODE":
                return ORDER_CODE;
            default:
                return DEFAULT;
        }
    }
}
