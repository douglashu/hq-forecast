package com.hq.sid.service.entity.enums;

/**
 * @包名称：com.hq.sid.service.entity.enums
 * @创建人：yyx
 * @创建时间：17/1/25 下午4:02
 */
public enum QrCodeType {

    STORE_CASHIER("STORE_CASHIER","门店收款码");

    private String type;
    private String value;

    QrCodeType(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
