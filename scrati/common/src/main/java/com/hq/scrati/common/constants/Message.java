package com.hq.scrati.common.constants;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @包名称：com.hq.tony.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/8 下午7:01
 */
public class Message implements Serializable{

    private static final long serialVersionUID = 6601749441544855304L;
    private String mchId;

    private String orderId;

    private String payChannel;//: 'ALIPAY',

    private String tradeType;//: 'H5_JSAPI',

    private BigDecimal totalAmount;//: 2000

    private Integer operatorId;

    private String operatorName;

    private Long timestamp;

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
