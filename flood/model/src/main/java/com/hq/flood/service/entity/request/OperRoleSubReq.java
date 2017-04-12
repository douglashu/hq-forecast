package com.hq.flood.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.flood.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/16 上午8:29
 */
public class OperRoleSubReq implements Serializable{

    private String roleId;

    private Integer pcoreId;

    private Integer coreId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getPcoreId() {
        return pcoreId;
    }

    public void setPcoreId(Integer pcoreId) {
        this.pcoreId = pcoreId;
    }

    public Integer getCoreId() {
        return coreId;
    }

    public void setCoreId(Integer coreId) {
        this.coreId = coreId;
    }
}
