package com.hq.brooke.model.view.req;

import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 15/01/2017.
 */
public class WxCommonReq extends HqRequest {
    private String tAppId;
    private String tAppSecret;

    public String gettAppId() {
        return tAppId;
    }

    public void settAppId(String tAppId) {
        this.tAppId = tAppId;
    }

    public String gettAppSecret() {
        return tAppSecret;
    }

    public void settAppSecret(String tAppSecret) {
        this.tAppSecret = tAppSecret;
    }
}
