package com.hq.diego.model.req.tfb;

/**
 * Created by zhaoyang on 14/03/2017.
 */
public class TfbQueryOrderReq extends TfbCommonReq {

    // 商户号
    private String spid;
    // 天付宝单号
    private String listid;
    // 商户订单号
    private String sp_billno;
    // 平台商户单号
    private String merch_listid;

    public String getSp_billno() {
        return sp_billno;
    }

    public void setSp_billno(String sp_billno) {
        this.sp_billno = sp_billno;
    }

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

    public String getMerch_listid() {
        return merch_listid;
    }

    public void setMerch_listid(String merch_listid) {
        this.merch_listid = merch_listid;
    }

}
