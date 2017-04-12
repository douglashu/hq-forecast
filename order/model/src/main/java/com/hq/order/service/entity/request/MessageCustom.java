package com.hq.order.service.entity.request;

/**
 * @包名称：com.hq.tony.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/13 上午11:35
 */
public class MessageCustom {

    private String type;

    private String orderId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
