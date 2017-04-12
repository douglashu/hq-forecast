package com.hq.brooke.model.view.req;

/**
 * Created by zhaoyang on 15/01/2017.
 */
public class WxOpenIdReq extends WxCommonReq {

    private String authCode;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
