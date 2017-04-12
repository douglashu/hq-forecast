package com.hq.flood.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.flood.service.request
 * @创建人：yyx
 * @创建时间：16/12/16 下午11:37
 */
public class RolePageReq implements Serializable{

    private static final long serialVersionUID = 1852054626947760690L;
    private String roleName;
    private String roleStatus;
    private String urmId;
    private String pRoleId;
    private Integer page;
    private Integer pageSize;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(String roleStatus) {
        this.roleStatus = roleStatus;
    }

    public String getUrmId() {
        return urmId;
    }

    public void setUrmId(String urmId) {
        this.urmId = urmId;
    }

    public String getpRoleId() {
        return pRoleId;
    }

    public void setpRoleId(String pRoleId) {
        this.pRoleId = pRoleId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
