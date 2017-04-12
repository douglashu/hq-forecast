package com.hq.flood.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.flood.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/10 下午10:27
 */
public class RoleInitReq implements Serializable{

    private static final long serialVersionUID = 5430471087929693999L;
    private String systemId;

    private Integer coreId;

    private Boolean isAdmin;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Integer getCoreId() {
        return coreId;
    }

    public void setCoreId(Integer coreId) {
        this.coreId = coreId;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
