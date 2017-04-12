package com.hq.flood.service.entity.request;

import java.io.Serializable;
import java.util.List;

/**
 * @包名称：com.hq.flood.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/12 下午8:35
 */
public class BatchOperRoleReq implements Serializable{

    private List<Integer> operIds;

    private String systemId;

    public List<Integer> getOperIds() {
        return operIds;
    }

    public void setOperIds(List<Integer> operIds) {
        this.operIds = operIds;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}
