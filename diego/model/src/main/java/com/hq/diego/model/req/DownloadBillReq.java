package com.hq.diego.model.req;

import com.hq.diego.model.TradeCommonReq;

/**
 * Created by zhaoyang on 04/02/2017.
 */
public class DownloadBillReq extends TradeCommonReq {

    private String billDate;
    private String billType;
    private String payChannel;

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

}
