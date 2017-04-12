package com.hq.diego.model.req.tfb;

/**
 * Created by zhaoyang on 13/03/2017.
 */
public class TfbTradeReq extends TfbCommonReq {

    // 商户号
    private String spid;
    // 通知回调URL
    private String notify_url;
    // 支付成功跳转URL
    private String pay_show_url;
    // 商户订单号
    private String sp_billno;
    // 商户ip
    private String spbill_create_ip;
    // 支付类型
    private String pay_type;
    // 发起交易时间
    private String tran_time;
    // 交易金额
    private Integer tran_amt;
    // 币种类型
    private String cur_type;
    // 用户二维码内容，支付方式为: 刷卡支付必填
    private String auth_code;
    // 商品名称或标示
    private String item_name;
    // 商品附加数据，如商品描述等信息
    private String item_attach;

    /*
        外接支付方式：
            wxpay:微信支付
            qqpay:QQ钱包支付
            默认为微信支付, 支付宝不需要传
    */
    private String out_channel;
    /*
        微信支付必填
        固定长度7位,商户下挂的子商户id（自定义），
        保证每个子商户id唯一，该字段将上传至微信用于风控管理
     */
    private String bank_mch_id;
    /*
        微信支付必填
        商户下挂的子商户名称（自定义）
        该字段将上传至微信用于风控管理
     */
    private String bank_mch_name;
    /*
        终端设备id
        商户设备唯一id
        QQ钱包条码支付必填该字段
     */
    private String sp_udid;

    public String getPay_show_url() {
        return pay_show_url;
    }

    public void setPay_show_url(String pay_show_url) {
        this.pay_show_url = pay_show_url;
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getSp_billno() {
        return sp_billno;
    }

    public void setSp_billno(String sp_billno) {
        this.sp_billno = sp_billno;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getTran_time() {
        return tran_time;
    }

    public void setTran_time(String tran_time) {
        this.tran_time = tran_time;
    }

    public Integer getTran_amt() {
        return tran_amt;
    }

    public void setTran_amt(Integer tran_amt) {
        this.tran_amt = tran_amt;
    }

    public String getCur_type() {
        return cur_type;
    }

    public void setCur_type(String cur_type) {
        this.cur_type = cur_type;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_attach() {
        return item_attach;
    }

    public void setItem_attach(String item_attach) {
        this.item_attach = item_attach;
    }

    public String getOut_channel() {
        return out_channel;
    }

    public void setOut_channel(String out_channel) {
        this.out_channel = out_channel;
    }

    public String getBank_mch_id() {
        return bank_mch_id;
    }

    public void setBank_mch_id(String bank_mch_id) {
        this.bank_mch_id = bank_mch_id;
    }

    public String getBank_mch_name() {
        return bank_mch_name;
    }

    public void setBank_mch_name(String bank_mch_name) {
        this.bank_mch_name = bank_mch_name;
    }

    public String getSp_udid() {
        return sp_udid;
    }

    public void setSp_udid(String sp_udid) {
        this.sp_udid = sp_udid;
    }
}
