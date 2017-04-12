package com.hq.scrati.common.constants.trade;

import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 30/11/2016.
 */
public class TradeState {

    // 预创建
    public static final String PRE_CREATE = "PRE_CREATE";

    // 等待客户付款
    public static final String WAIT_PAY = "WAIT_PAY";

    // 已关闭
    public static final String CLOSED = "CLOSED";

    // 已撤销
    public static final String REVOKED = "REVOKED";

    // 已退款
    public static final String REFUND = "REFUND";

    // 交易失败
    public static final String FAIL = "FAIL";

    // 交易成功
    public static final String SUCCESS = "SUCCESS";

    // 未知状态, 需要继续查询
    public static final String UNKNOW = "UNKNOW";

    // 异常, 需要人工干预
    public static final String ABNORMAL = "ABNORMAL";

    public static String getCNString(String tradeState) {
        if(StringUtils.isEmpty(tradeState)) return "";
        switch (tradeState) {
            case PRE_CREATE:
                return "交易预创建";
            case WAIT_PAY:
                return "等待客户付款";
            case CLOSED:
                return "交易已关闭";
            case REVOKED:
                return "交易已撤销";
            case REFUND:
                return "交易已退款";
            case FAIL:
                return "交易失败";
            case SUCCESS:
                return "交易成功";
            case UNKNOW:
                return "响应超时或未知异常";
            case ABNORMAL:
                return "交易状态异常";
            default:
                return "";
        }
    }

    public static String fromAliTradeState(String aliTradeState) {
        if(!StringUtils.isEmpty(aliTradeState)) {
            if (AliTradeState.TRADE_SUCCESS.equals(aliTradeState)) {
                return SUCCESS;
            } else if (AliTradeState.TRADE_CLOSED.equals(aliTradeState)) {
                return CLOSED;
            } else if (AliTradeState.WAIT_BUYER_PAY.equals(aliTradeState)) {
                return WAIT_PAY;
            } else if (AliTradeState.TRADE_FINISHED.equals(aliTradeState)) {
                return CLOSED;
            }
        }
        return UNKNOW;
    }

    public static String fromWxTradeState(String wxTradeState) {
        if(!StringUtils.isEmpty(wxTradeState)) {
            if (WxTradeState.SUCCESS.equals(wxTradeState)) {
                return SUCCESS;
            } else if (WxTradeState.CLOSED.equals(wxTradeState)) {
                return CLOSED;
            } else if (WxTradeState.USERPAYING.equals(wxTradeState)) {
                return WAIT_PAY;
            } else if (WxTradeState.REFUND.equals(wxTradeState)) {
                return REFUND;
            } else if (WxTradeState.REVOKED.equals(wxTradeState)) {
                return REVOKED;
            } else if (WxTradeState.NOTPAY.equals(wxTradeState)) {
                return WAIT_PAY;
            } else if (WxTradeState.PAYERROR.equals(wxTradeState)) {
                return FAIL;
            }
        }
        return UNKNOW;
    }

    public static String fromCibTradeState(String cibTradeState) {
        if ("SUCCESS".equals(cibTradeState)) {
            return SUCCESS;
        } else if ("REFUND".equals(cibTradeState)) {
            return REFUND;
        } else if ("NOTPAY".equals(cibTradeState)) {
            return CLOSED;
        } else if ("CLOSED".equals(cibTradeState)) {
            return CLOSED;
        } else if ("REVOKED".equals(cibTradeState)) {
            return REVOKED;
        } else if ("USERPAYING".equals(cibTradeState)) {
            return WAIT_PAY;
        } else if ("PAYERROR".equals(cibTradeState)) {
            return FAIL;
        }
        return UNKNOW;
    }

    public static String fromTfbTradeState(Integer tfbTradeState) {
        switch (tfbTradeState) {
            case 1:
                return PRE_CREATE;
            case 2:
                return WAIT_PAY;
            case 3:
                return SUCCESS;
            case 4:
                return FAIL;
            case 5:
                return CLOSED;
            default:
                return UNKNOW;
        }
    }

}
