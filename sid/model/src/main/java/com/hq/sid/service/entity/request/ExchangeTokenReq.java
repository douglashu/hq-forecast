package com.hq.sid.service.entity.request;

/**
 * Created by Zale on 2017/2/23.
 */
public class ExchangeTokenReq {
    private Integer core_id;
    private String auth_code;

    public Integer getCore_id() {
        return core_id;
    }

    public void setCore_id(Integer core_id) {
        this.core_id = core_id;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }
}
