package com.hq.order.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.tony.service.entity.request
 * @创建人：yyx
 * @创建时间：17/1/24 下午5:38
 */
public class TradeNoGenerateReq implements Serializable{

    private static final long serialVersionUID = -1082922634565745169L;
    private String mchId;

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }
}
