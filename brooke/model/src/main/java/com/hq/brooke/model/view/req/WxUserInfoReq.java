package com.hq.brooke.model.view.req;

/**
 * Created by zhaoyang on 24/01/2017.
 */
public class WxUserInfoReq extends WxCommonReq {

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
