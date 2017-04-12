package com.hq.diego.model.req.ali;

/**
 * Created by zhaoyang on 26/11/2016.
 */
public class AliOrderPrepayReq extends AliPayCommonReq {
    // 非必填, 买家支付宝账号
    private String buyer_logon_id;

    public String getBuyer_logon_id() {
        return buyer_logon_id;
    }

    public void setBuyer_logon_id(String buyer_logon_id) {
        this.buyer_logon_id = buyer_logon_id;
    }
}
