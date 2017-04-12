package com.hq.diego.model.resp;

import java.util.Map;

/**
 * Created by zhaoyang on 09/10/2016.
 */
public class OrderPrepayResp extends OrderTradeResp {

    private String codeUrl;
    private Map<String, Object> orderInfo;

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public Map<String, Object> getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(Map<String, Object> orderInfo) {
        this.orderInfo = orderInfo;
    }
}
