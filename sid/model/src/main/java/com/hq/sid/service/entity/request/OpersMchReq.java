package com.hq.sid.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.sid.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/19 上午12:37
 */
public class OpersMchReq implements Serializable{

    private String mchId; //  真实商户号

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }
}
