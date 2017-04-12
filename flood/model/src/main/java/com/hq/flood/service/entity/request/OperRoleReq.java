package com.hq.flood.service.entity.request;

import java.io.Serializable;
import java.util.List;

/**
 * @包名称：com.hq.flood.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/9 下午10:35
 */
public class OperRoleReq implements Serializable{

    private static final long serialVersionUID = -8658106619176633813L;
    private Integer operId;

    private List<String> roles;

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
