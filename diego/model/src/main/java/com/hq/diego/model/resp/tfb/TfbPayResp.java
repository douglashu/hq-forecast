package com.hq.diego.model.resp.tfb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 14/03/2017.
 */
@XStreamAlias(value = "root")
public class TfbPayResp extends TfbCommonResp {

    private String spid;
    private String qrcode;
    private String cur_type;
    private String listid;
    private String merch_listid;
    private String pay_type;
    private String sysd_time;
    private Integer tran_amt;
    private String sp_billno;

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getMerch_listid() {
        return merch_listid;
    }

    public void setMerch_listid(String merch_listid) {
        this.merch_listid = merch_listid;
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public String getCur_type() {
        return cur_type;
    }

    public void setCur_type(String cur_type) {
        this.cur_type = cur_type;
    }

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getSysd_time() {
        return sysd_time;
    }

    public void setSysd_time(String sysd_time) {
        this.sysd_time = sysd_time;
    }

    public Integer getTran_amt() {
        return tran_amt;
    }

    public void setTran_amt(Integer tran_amt) {
        this.tran_amt = tran_amt;
    }

    public String getSp_billno() {
        return sp_billno;
    }

    public void setSp_billno(String sp_billno) {
        this.sp_billno = sp_billno;
    }
}
