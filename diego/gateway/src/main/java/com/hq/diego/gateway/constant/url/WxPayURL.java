package com.hq.diego.gateway.constant.url;

/**
 * Created by zhaoyang on 1/14/16.
 */
public class WxPayURL {

    /*
        微信支付统一下单接口
     */
    private static final String CREATE_PREPAY_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static String getCreatePrepayOrderUrl() {
        return CREATE_PREPAY_ORDER;
    }

    /*
        微信刷卡支付
     */
    private static final String DO_SWIPE_PAY = "https://api.mch.weixin.qq.com/pay/micropay";
    public static String getDoSwipePayUrl() {
        return DO_SWIPE_PAY;
    }

    /*
        查询订单
     */
    private static final String QUERY_ORDER = "https://api.mch.weixin.qq.com/pay/orderquery";
    public static String getQueryOrderUrl() {
        return QUERY_ORDER;
    }

    /*
        撤销订单
     */
    private static final String CANCEL_ORDER = "https://api.mch.weixin.qq.com/secapi/pay/reverse";
    public static String getCancelOrderUrl() {
        return CANCEL_ORDER;
    }

    /*
        申请退款
     */
    private static final String REFUND_ORDER = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    public static String getRefundOrderUrl() {
        return REFUND_ORDER;
    }

    /*
        查询退款
     */
    private static final String QUERY_REFUND = "https://api.mch.weixin.qq.com/pay/refundquery";
    public static String getQueryRefund() {
        return QUERY_REFUND;
    }

    /*
        下载对账单
     */
    private static final String DOWNLOAD_BILL = "https://api.mch.weixin.qq.com/pay/downloadbill";
    public static String getDownloadBillUrl() {
        return DOWNLOAD_BILL;
    }

}
