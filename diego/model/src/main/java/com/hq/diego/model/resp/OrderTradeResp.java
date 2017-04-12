package com.hq.diego.model.resp;

import com.hq.diego.model.req.OrderTradeReq;

/**
 * Created by zhaoyang on 25/11/2016.
 */
public class OrderTradeResp extends OrderCommonResp {

    // 交易流水号
    private String tradeId;
    // 平台订单号
    private String orderId;
    // 支付渠道
    private String payChannel;
    // 交易类型 (SWIPE_CARD, H5_JSAPI, ORDER_CODE, APP)
    private String tradeType;
    // 平台交易状态
    private String tradeState;

    /*
        对于支付宝:
        ---------------------------------------------------------------------
        total_amount	订单总金额，与请求中的订单金额一致
        receipt_amount	实收金额，商户实际入账的金额（扣手续费之前）
        buyer_pay_amount	用户实付金额，建议打印在小票上避免退款时出现纠纷
        invoice_pay_amount	开票金额，快速告知商户应该给用户开多少钱发票
        上述四个金额的关系如下：

        total_amount - 商户出资的优惠金额 = receipt_amount；
        receipt_amount - 支付宝出资的优惠金额 = buyer_pay_amount；
        buyer_pay_amount - 用户自由的营销工具（目前只有集分宝）= invoice_amount;

        对于微信:
        ---------------------------------------------------------------------
     */

    // 订单总金额
    private Integer totalAmount;
    // 实收金额，商户实际入账的金额（扣手续费之前）
    private Integer receiptAmount;
    // 用户实付金额
    private Integer buyerPayAmount;


    // 小提示
    private String tips;
    // 第三方渠道订单ID
    private String tOrderId;
    // 第三方的第三方订单ID
    private String ttOrderId;

    // 付款日期
    private String endDate;
    // 付款时间
    private String endTime;

    private String openId;
    private String isSubscribe;
    private String subOpenId;
    private String subIsSubscribe;
    private String tUserId;
    private String tLoginId;

    private String fundBills;
    private String bankType;

    public void from(OrderTradeReq orderReq) {
        if(orderReq == null) return;
        this.setOrderId(orderReq.getOrderId());
        this.setPayChannel(orderReq.getPayChannel());
        this.setTradeType(orderReq.getTradeType());
        this.setTotalAmount(orderReq.getTotalAmount());
    }

    public void clearSecureInfo() {
        settUserId(null);
        settLoginId(null);
        setOpenId(null);
        setIsSubscribe(null);
        setSubOpenId(null);
        setSubIsSubscribe(null);
    }

    public String getTtOrderId() {
        return ttOrderId;
    }

    public void setTtOrderId(String ttOrderId) {
        this.ttOrderId = ttOrderId;
    }

    public Integer getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public void setBuyerPayAmount(Integer buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String gettOrderId() {
        return tOrderId;
    }

    public void settOrderId(String tOrderId) {
        this.tOrderId = tOrderId;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Integer receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getSubOpenId() {
        return subOpenId;
    }

    public void setSubOpenId(String subOpenId) {
        this.subOpenId = subOpenId;
    }

    public String getSubIsSubscribe() {
        return subIsSubscribe;
    }

    public void setSubIsSubscribe(String subIsSubscribe) {
        this.subIsSubscribe = subIsSubscribe;
    }

    public String gettUserId() {
        return tUserId;
    }

    public void settUserId(String tUserId) {
        this.tUserId = tUserId;
    }

    public String gettLoginId() {
        return tLoginId;
    }

    public void settLoginId(String tLoginId) {
        this.tLoginId = tLoginId;
    }

    public String getFundBills() {
        return fundBills;
    }

    public void setFundBills(String fundBills) {
        this.fundBills = fundBills;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
