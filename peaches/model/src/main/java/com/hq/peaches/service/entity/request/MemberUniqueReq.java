package com.hq.peaches.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.peaches.service.request
 * @创建人：yyx
 * @创建时间：16/12/14 下午1:48
 */
public class MemberUniqueReq implements Serializable {

    private static final long serialVersionUID = -7246537762050079231L;
    private String mchId;
    private String phone;

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
