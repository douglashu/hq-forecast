package com.hq.sid.service.entity.request;

/**
 * Created by Zale on 2017/2/16.
 */
public class CheckOperExistReq {
    private String loginId;
    private String operAlias;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getOperAlias() {
        return operAlias;
    }

    public void setOperAlias(String operAlias) {
        this.operAlias = operAlias;
    }
}
