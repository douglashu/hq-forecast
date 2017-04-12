package com.hq.soto.service.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @包名称：com.hq.soto.service.entity.response
 * @创建人：yyx
 * @创建时间：17/2/16 下午11:30
 */
public class FeeTemplateRsp implements Serializable{

    private Integer id;
    private String name;
    private Integer payType;
    private BigDecimal fee;
    private BigDecimal disFee;
    private String status;
    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getDisFee() {
        return disFee;
    }

    public void setDisFee(BigDecimal disFee) {
        this.disFee = disFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
