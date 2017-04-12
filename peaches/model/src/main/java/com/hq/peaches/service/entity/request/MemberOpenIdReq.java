package com.hq.peaches.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.peaches.service.request
 * @创建人：yyx
 * @创建时间：17/1/14 下午9:08
 */
public class MemberOpenIdReq implements Serializable{

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
