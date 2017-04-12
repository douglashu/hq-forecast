package com.hq.sid.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.sid.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/3 上午9:11
 */
public class QrCodeStateReq implements Serializable{

    private Integer codeId;

    private String state;

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
