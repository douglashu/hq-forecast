package com.hq.sid.service.entity.response;

/**
 * @包名称：com.hq.sid.service.entity.response
 * @创建人：yyx
 * @创建时间：17/2/15 下午11:17
 */
public class OperConfigRsp {

    private Integer operId;

    private Boolean isPush;

    private String refundPassword;

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    public Boolean getIsPush() {
        return isPush;
    }

    public void setIsPush(Boolean isPush) {
        this.isPush = isPush;
    }

    public String getRefundPassword() {
        return refundPassword;
    }

    public void setRefundPassword(String refundPassword) {
        this.refundPassword = refundPassword;
    }
}
