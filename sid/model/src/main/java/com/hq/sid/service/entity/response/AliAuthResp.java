package com.hq.sid.service.entity.response;

/**
 * Created by Zale on 2017/1/9.
 */
public class AliAuthResp {
    private String mercId;
    private Integer coreId;
    private String authToken;

    public String getMercId() {
        return mercId;
    }

    public void setMercId(String mercId) {
        this.mercId = mercId;
    }

    public Integer getCoreId() {
        return coreId;
    }

    public void setCoreId(Integer coreId) {
        this.coreId = coreId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
