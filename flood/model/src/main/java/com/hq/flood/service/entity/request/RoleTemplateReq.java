package com.hq.flood.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.flood.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/10 下午8:52
 */
public class RoleTemplateReq implements Serializable{

    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
