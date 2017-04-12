package com.hq.diego.model.req.cib;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 04/03/2017.
 */
@XStreamAlias(value = "xml")
public class CibCancelOrderReq extends CibCommonReq {

    private String out_trade_no;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
}
