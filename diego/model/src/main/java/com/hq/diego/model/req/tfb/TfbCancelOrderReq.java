package com.hq.diego.model.req.tfb;

/**
 * Created by zhaoyang on 14/03/2017.
 */
public class TfbCancelOrderReq extends TfbCommonReq {

    private String spid;
    private String listid;
    private String sp_billno;
    private String tran_time;
    private Integer tran_amt;

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }

    public String getSp_billno() {
        return sp_billno;
    }

    public void setSp_billno(String sp_billno) {
        this.sp_billno = sp_billno;
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
}
