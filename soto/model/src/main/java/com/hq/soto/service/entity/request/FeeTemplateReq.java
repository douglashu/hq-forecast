package com.hq.soto.service.entity.request;

import java.math.BigDecimal;

/**
 * @包名称：com.hq.soto.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/17 上午12:22
 */
public class FeeTemplateReq {

    private Integer id;

    private Integer payType;

    private String name;

    private String status;

    private BigDecimal fee;

    private BigDecimal disFee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
