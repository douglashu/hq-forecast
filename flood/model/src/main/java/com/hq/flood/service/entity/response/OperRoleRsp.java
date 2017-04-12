package com.hq.flood.service.entity.response;

import java.io.Serializable;

/**
 * @包名称：com.hq.flood.service.entity.response
 * @创建人：yyx
 * @创建时间：17/2/16 上午8:27
 */
public class OperRoleRsp implements Serializable{

    private Integer id;

    private Integer operId;

    private String roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
