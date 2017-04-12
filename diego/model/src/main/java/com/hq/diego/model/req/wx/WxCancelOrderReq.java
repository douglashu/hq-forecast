package com.hq.diego.model.req.wx;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 12/12/2016.
 */
@XStreamAlias(value = "xml")
public class WxCancelOrderReq extends WxCommonReq {
    private String transaction_id;
    private String out_trade_no;

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
}
