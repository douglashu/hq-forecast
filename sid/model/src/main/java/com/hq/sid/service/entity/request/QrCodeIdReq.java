package com.hq.sid.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.sid.service.entity.request
 * @创建人：yyx
 * @创建时间：17/1/25 下午4:16
 */
public class QrCodeIdReq implements Serializable{

    private static final long serialVersionUID = 5138326493719257605L;
    private Integer codeId;

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }
}
