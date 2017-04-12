package com.hq.diego.model.route;

import java.math.BigDecimal;

/**
 * Created by zhaoyang on 11/03/2017.
 */
public class ChannelRoute {

    private String id;

    private String appId;
    private String mchId;
    private String key;
    private BigDecimal rate;

    private String tradeService;
    private String queryService;
    private String cancelService;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTradeService() {
        return tradeService;
    }

    public void setTradeService(String tradeService) {
        this.tradeService = tradeService;
    }

    public String getQueryService() {
        return queryService;
    }

    public void setQueryService(String queryService) {
        this.queryService = queryService;
    }

    public String getCancelService() {
        return cancelService;
    }

    public void setCancelService(String cancelService) {
        this.cancelService = cancelService;
    }
}
