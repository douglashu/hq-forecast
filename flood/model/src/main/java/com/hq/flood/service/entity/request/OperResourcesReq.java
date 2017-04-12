package com.hq.flood.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.flood.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/9 下午10:44
 */
public class OperResourcesReq implements Serializable{

    private String systemId;

    private Integer operId;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }
}
