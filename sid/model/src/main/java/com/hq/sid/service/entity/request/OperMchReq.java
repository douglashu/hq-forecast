package com.hq.sid.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.sid.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/5 下午11:12
 */
public class OperMchReq implements Serializable{

    private Integer coreId;

    private Integer operId;

    private String roleId;

    private Integer pcoreId;

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getCoreId() {
        return coreId;
    }

    public void setCoreId(Integer coreId) {
        this.coreId = coreId;
    }

    public Integer getPcoreId() {
        return pcoreId;
    }

    public void setPcoreId(Integer pcoreId) {
        this.pcoreId = pcoreId;
    }
}
