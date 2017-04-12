package com.hq.diego.model.req.channel;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhaoyang on 22/03/2017.
 */
public class TradeChannelReq {
    private String id;
    private String name;
    private String channel;
    private List<String> payChannels;
    private String tradeType;
    private BigDecimal costRate;
    private BigDecimal guidingRate;
    private String state;
    private String tradeService;
    private String queryService;
    private String cancelService;
    private Integer priority;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCostRate() {
        return costRate;
    }

    public void setCostRate(BigDecimal costRate) {
        this.costRate = costRate;
    }

    public BigDecimal getGuidingRate() {
        return guidingRate;
    }

    public void setGuidingRate(BigDecimal guidingRate) {
        this.guidingRate = guidingRate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public List<String> getPayChannels() {
        return payChannels;
    }

    public void setPayChannels(List<String> payChannels) {
        this.payChannels = payChannels;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
