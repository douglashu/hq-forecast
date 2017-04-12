package com.hq.sid.service.entity.request;

import java.io.Serializable;
import java.util.Date;

/**
 * @包名称：com.hq.sid.service.entity.request
 * @创建人：yyx
 * @创建时间：17/1/30 下午3:59
 */
public class OperUpdateReq implements Serializable{
    private Integer id;

    private String operName;

    private String operAlias;

    private String status;

    private Integer roleType;

    private String refundAuth;

    public String getRefundAuth() {
        return refundAuth;
    }

    public void setRefundAuth(String refundAuth) {
        this.refundAuth = refundAuth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getOperAlias() {
        return operAlias;
    }

    public void setOperAlias(String operAlias) {
        this.operAlias = operAlias;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
}
