package com.hq.diego.model.req;

import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 15/03/2017.
 */
public class OrderPollingReq extends HqRequest {
    private String tradeId;

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }
}
