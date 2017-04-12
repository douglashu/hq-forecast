package com.hq.flood.service.entity.response;

/**
 * @包名称：com.hq.flood.service.entity.response
 * @创建人：yyx
 * @创建时间：17/2/12 下午8:32
 */
public class BatchOperRoleRsp {

    private String roleId;

    private Integer templateId;

    private Integer operId;

    /**
     * 所属子系统id
     */
    private String systemId;

    /**
     * 名称,同组不能重复
     */
    private String roleName;

    /**
     * 描述
     */
    private String roleDesc;

    /**
     * 0正常 ,1失效
     */
    private String roleStatus;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(String roleStatus) {
        this.roleStatus = roleStatus;
    }
}
