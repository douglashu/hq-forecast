package com.hq.diego.model.req.cib;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 21/03/2017.
 */
@XStreamAlias(value = "xml")
public class CibOrderPrepayReq extends CibCommonReq {

    // 用户微信h5支付, 值为1：是；值为0：否；不传默认是0
    private String is_raw;
    // 商户公众账号ID
    private String sub_appid;
    // 微信用户关注商家公众号的openid
    // 注：使用测试号时此参数置空，即不要传这个参数，使用正式商户号时才传入，参数名是sub_openid
    private String sub_openid;
    // 买家的支付宝用户 ID
    private String buyer_id;
    // 交易完成后跳转的URL，需给绝对路径
    private String callback_url;

    // 商户订单号
    private String out_trade_no;
    // 设备号, 非必填
    private String device_info;
    // 商品描述
    private String body;
    // 接收威富通通知的URL，需给绝对路径，255字符
    private String notify_url;
    // 附加信息, 非必填
    private String attach;
    // 总金额，以分为单位
    private Integer total_fee;
    // 订单生成的机器 IP
    private String mch_create_ip;
    // 订单生成时间，非必填, 格式为yyyymmddhhmmss
    private String time_start;
    // 订单失效时间，非必填, 格式为yyyymmddhhmmss
    private String time_expire;
    // 操作员, 非必填
    private String op_user_id;

    // 商品标记，用于优惠券或者满减使用
    private String goods_tag;
    // 预留字段此 id 为静态可打印的二维码中包含的商品
    private String product_id;
    // 限定用户使用微信支付时能否使用信用卡，值为1，禁用信用卡；值为0或者不传此参数则不禁用
    private String limit_credit_pay;

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public String getMch_create_ip() {
        return mch_create_ip;
    }

    public void setMch_create_ip(String mch_create_ip) {
        this.mch_create_ip = mch_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getOp_user_id() {
        return op_user_id;
    }

    public void setOp_user_id(String op_user_id) {
        this.op_user_id = op_user_id;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getLimit_credit_pay() {
        return limit_credit_pay;
    }

    public void setLimit_credit_pay(String limit_credit_pay) {
        this.limit_credit_pay = limit_credit_pay;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getIs_raw() {
        return is_raw;
    }

    public void setIs_raw(String is_raw) {
        this.is_raw = is_raw;
    }

    public String getSub_appid() {
        return sub_appid;
    }

    public void setSub_appid(String sub_appid) {
        this.sub_appid = sub_appid;
    }

    public String getSub_openid() {
        return sub_openid;
    }

    public void setSub_openid(String sub_openid) {
        this.sub_openid = sub_openid;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }
}
