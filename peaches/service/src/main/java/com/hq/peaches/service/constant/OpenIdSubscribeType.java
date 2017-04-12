package com.hq.peaches.service.constant;

/**
 * @包名称：com.hq.tony.service.enums
 * @创建人：yyx
 * @创建时间：17/1/24 下午5:21
 */
public enum OpenIdSubscribeType {

    SUBSCRIBE_Y("Y"),
    SUBSCRIBE_N("N");

    private String value;

    OpenIdSubscribeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
