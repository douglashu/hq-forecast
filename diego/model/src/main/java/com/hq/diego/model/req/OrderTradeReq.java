package com.hq.diego.model.req;

import com.hq.diego.model.Goods;
import com.hq.diego.model.TradeCommonReq;
import java.util.List;

/**
 * Created by zhaoyang on 10/5/16.
 */
public class OrderTradeReq extends TradeCommonReq {

    // 第三方门店id
    private String tStoreId;
    // 设备ID
    private String terminalId;
    // 操作员ID
    private String operatorId;
    // 操作员姓名
    private String operatorName;
    // 终端设备IP地址
    private String ipAddress;

    // 商户订单号
    private String orderId;
    private String payChannel;
    // 交易类型: H5/APP/订单码/刷卡
    private String tradeType;

    // H5支付 微信用户openId
    private String openId;
    // H5支付 微信子商户用户openId
    private String subOpenId;
    // H5支付 支付宝用户id
    private String buyerId;
    // 用户付款码, 刷卡支付必传
    private String authCode;

    // 商品标题
    private String title;
    // 商品描述
    private String body;
    // 商品详情
    private List<Goods> goodsList;
    // 交易总金额: 单位分
    private Integer totalAmount;
    private String feeType;
    // 自定义字符串
    private String custom;
    private String attach;

    // 交易通道, 如果不指定, 自动路由
    private String tradeChannel;

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getTradeChannel() {
        return tradeChannel;
    }

    public void setTradeChannel(String tradeChannel) {
        this.tradeChannel = tradeChannel;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSubOpenId() {
        return subOpenId;
    }

    public void setSubOpenId(String subOpenId) {
        this.subOpenId = subOpenId;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String gettStoreId() {
        return tStoreId;
    }

    public void settStoreId(String tStoreId) {
        this.tStoreId = tStoreId;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
}
