package com.hq.diego.model.resp.wx;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 24/11/2016.
 */
@XStreamAlias(value = "xml")
public class WxPayResp extends WxCommonResp {

    // 微信支付订单号
    private String transaction_id;
    // 商户订单号
    private String out_trade_no;
    // 交易类型
    private String trade_type;
    // 付款银行: 值列表详见银行类型
    private String bank_type;
    /*
       SUCCESS—支付成功
       REFUND—转入退款
       NOTPAY—未支付
       CLOSED—已关闭
       REVOKED—已撤销(刷卡支付)
       USERPAYING--用户支付中
       PAYERROR--支付失败(其他原因，如银行返回失败)
    */
    private String trade_state;
    private String trade_state_desc;

    // 货币类型
    private String fee_type;
    // 总金额
    private Integer total_fee;
    // 现金支付货币类型
    private String cash_fee_type;
    // 现金支付金额
    private Integer cash_fee;
    // 应结订单金额
    private Integer settlement_total_fee;

    // 代金券金额
    private Integer coupon_fee;
    private Integer coupon_count;
    // coupon_batch_id_$n
    // coupon_id_$n
    // coupon_fee_$n

    // 商品详情
    private String detail;
    // 商家数据包
    private String attach;
    // 支付完成时间
    private String time_end;

    // 用户标识
    private String openid;
    // 是否关注公众账号
    private String is_subscribe;
    // 用户子标识
    private String sub_openid;
    // 是否关注子公众账号
    private String sub_is_subscribe;

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public String getCash_fee_type() {
        return cash_fee_type;
    }

    public void setCash_fee_type(String cash_fee_type) {
        this.cash_fee_type = cash_fee_type;
    }

    public Integer getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(Integer cash_fee) {
        this.cash_fee = cash_fee;
    }

    public Integer getSettlement_total_fee() {
        return settlement_total_fee;
    }

    public void setSettlement_total_fee(Integer settlement_total_fee) {
        this.settlement_total_fee = settlement_total_fee;
    }

    public Integer getCoupon_fee() {
        return coupon_fee;
    }

    public void setCoupon_fee(Integer coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }

    public String getTrade_state_desc() {
        return trade_state_desc;
    }

    public void setTrade_state_desc(String trade_state_desc) {
        this.trade_state_desc = trade_state_desc;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getSub_openid() {
        return sub_openid;
    }

    public void setSub_openid(String sub_openid) {
        this.sub_openid = sub_openid;
    }

    public String getSub_is_subscribe() {
        return sub_is_subscribe;
    }

    public void setSub_is_subscribe(String sub_is_subscribe) {
        this.sub_is_subscribe = sub_is_subscribe;
    }

    public Integer getCoupon_count() {
        return coupon_count;
    }

    public void setCoupon_count(Integer coupon_count) {
        this.coupon_count = coupon_count;
    }
}
