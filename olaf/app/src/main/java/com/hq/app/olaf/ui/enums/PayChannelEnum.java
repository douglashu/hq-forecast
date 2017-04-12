package com.hq.app.olaf.ui.enums;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/1/23.
 */

public enum PayChannelEnum {
    ALL("","",  1, 18, "13", ""),
    DEFAULT( "其他","DEFAULT",  1, 18, "13", "Wx"),
    //支付渠道:
    WEIXIN_PAY("微信支付", "WEIXIN_PAY", 1, 18, "13", "Wx"),
    ALI_PAY("支付宝", "ALIPAY", 2, 18, "28", "Ali"),
    UNION_PAY("银联钱包", "UNION_PAY", 4, 21, "1010", "Union"),
    TEN_PAY("QQ钱包", "TEN_PAY", 8, 18, "91", "Ten"),
    BAIDU_WALLET("百度钱包", "BAIDU_PAY", 16, 18, "31", "Baidu"),
    JD_PAY("京东钱包", "JD_PAY", 32, 18, "18", "Jd");


    private  String name;
    private  String code;
    private  int minLength;
    private  int maxLength;
    private  String startCode;
    private  String shortName;

    PayChannelEnum(String name,String code, int minLength, int maxLength,  String startCode, String shortName) {
        this.name = name;
        this.code = code;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.startCode = startCode;
        this.shortName = shortName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getStartCode() {
        return startCode;
    }

    public void setStartCode(String startCode) {
        this.startCode = startCode;
    }

    public static PayChannelEnum getChannel(String code) {
        if (TextUtils.isEmpty(code)) return DEFAULT;
        switch (code) {
            case "WEIXIN_PAY":
                return WEIXIN_PAY;
            case "ALIPAY":
                return ALI_PAY;
            case "UNION_PAY":
                return UNION_PAY;
            case "TEN_PAY":
                return TEN_PAY;
            case "BAIDU_PAY":
                return BAIDU_WALLET;
            case "JD_PAY":
                return JD_PAY;
            default:
                return DEFAULT;
        }
    }
}
