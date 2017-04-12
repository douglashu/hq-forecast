package com.hq.diego.model.req;

import com.hq.diego.model.TradeCommonReq;
import java.util.List;

/**
 * Created by zhaoyang on 02/01/2017.
 */
public class OrderQueryReq extends TradeCommonReq {

    // for find one

    private String id;
    private String type;


    // for find list

    private String operatorId;
    private String payChannel;
    private String tradeType;
    private String startDate;
    private String endDate;
    private List<String> inTradeStates;
    private List<String> notInTradeStates;
    private Integer page;
    private Integer size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public List<String> getInTradeStates() {
        return inTradeStates;
    }

    public List<String> getNotInTradeStates() {
        return notInTradeStates;
    }

    public void setNotInTradeStates(List<String> notInTradeStates) {
        this.notInTradeStates = notInTradeStates;
    }

    public void setInTradeStates(List<String> inTradeStates) {
        this.inTradeStates = inTradeStates;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
