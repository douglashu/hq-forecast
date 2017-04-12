package com.hq.app.olaf.ui.enums;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/1/23.
 */

public enum  PayStatusEnum {
    DEFAULT( "DEFAULT", "其他", "其他"),
    //所有的交易状态
    PRE_CREATE("PRE_CREATE","预创建","预创建"),
    WAIT_PAY("WAIT_PAY","等待客户付款","等待客户付款"),
    CLOSED("CLOSED","已关闭","已关闭"),
    REVOKED("REVOKED","已撤销","已撤销"),
    REFUND("REFUND","已退款","已退款"),
    FAIL("FAIL","交易失败","交易失败"),
    SUCCESS("SUCCESS","交易成功","交易成功"),
    UNKNOW("UNKNOW","未知状态","未知状态, 需要继续查询"),
    ABNORMAL("ABNORMAL","异常","异常, 需要人工干预");


    private  String code;
    private  String desc;
    private  String detailDes;//详细描述

    PayStatusEnum(String code, String desc,String detailDes) {
        this.code = code;
        this.desc = desc;
        this.detailDes=detailDes;
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

    public String getDetailDes() {
        return detailDes;
    }

    public void setDetailDes(String detailDes) {
        this.detailDes = detailDes;
    }

    public static  PayStatusEnum getPayStatus(String code) {
        if (TextUtils.isEmpty(code)) return DEFAULT;
        switch (code) {
            case "PRE_CREATE":
                return PRE_CREATE;
            case "WAIT_PAY":
                return WAIT_PAY;
            case "CLOSED":
                return CLOSED;
            case "REVOKED":
                return REVOKED;
            case "REFUND":
                return REFUND;
            case "FAIL":
                return FAIL;
            case "SUCCESS":
                return SUCCESS;
            case "UNKNOW":
                return UNKNOW;
            case "ABNORMAL":
                return ABNORMAL;
            default:
                return DEFAULT;
        }
    }
}
