package com.hq.diego.model.resp.tfb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 15/03/2017.
 */
@XStreamAlias(value = "record")
public class TfbQueryDataRecordResp {

    // 商户号
    private String spid;
    // 支付类型
    private String pay_type;
    // 商户系统的订单号
    private String sp_billno;
    // 天付宝订单号
    private String listid;
    // 平台商户单号
    private String merch_listid;
    /*
        交易状态
            1-新建；
            2-交易处理中；
            3-交易成功；
            4-交易失败；
            5-关闭
     */
    private Integer state;
    // 交易金额
    private Integer tran_amt;
    private String item_name;
    private String item_attach;
    private String pay_time;
    private String close_time;
    private String create_time;

    private String sub_openid;

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getSp_billno() {
        return sp_billno;
    }

    public void setSp_billno(String sp_billno) {
        this.sp_billno = sp_billno;
    }

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }

    public String getMerch_listid() {
        return merch_listid;
    }

    public void setMerch_listid(String merch_listid) {
        this.merch_listid = merch_listid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTran_amt() {
        return tran_amt;
    }

    public void setTran_amt(Integer tran_amt) {
        this.tran_amt = tran_amt;
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

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getSub_openid() {
        return sub_openid;
    }

    public void setSub_openid(String sub_openid) {
        this.sub_openid = sub_openid;
    }
}
