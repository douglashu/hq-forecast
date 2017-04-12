package com.hq.scrati.common.constants;

/**
 * @包名称：com.hq.tony.service.request
 * @创建人：yyx
 * @创建时间：17/1/18 下午4:45
 */
public enum RedisPubType {

    TRADE("1001");

    private String type;

    RedisPubType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
