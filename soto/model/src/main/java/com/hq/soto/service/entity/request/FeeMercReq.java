package com.hq.soto.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.soto.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/16 下午9:07
 */
public class FeeMercReq implements Serializable{

    private Integer coreId;

    public Integer getCoreId() {
        return coreId;
    }

    public void setCoreId(Integer coreId) {
        this.coreId = coreId;
    }
}
