package com.hq.flood.service.entity.response;

import java.io.Serializable;

/**
 * @包名称：com.hq.flood.service.response
 * @创建人：yyx
 * @创建时间：16/12/15 下午7:22
 */
public class RoleRsp implements Serializable{

    private static final long serialVersionUID = 1986185083635113561L;
    /**
     * 主键
     */
    private String roleId;

    /**
     * 所属客户
     */
    private String urmId;

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

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String lastupdatetime;

    /**
     * 最后修改人
     */
    private String modifiedUser;

    /**
     * 5位一级编码，共50级
     */
    private String nodeCode;

    /**
     * 上级角色ID
     */
    private String pRoleId;

    private Integer templateId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUrmId() {
        return urmId;
    }

    public void setUrmId(String urmId) {
        this.urmId = urmId;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(String lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public String getpRoleId() {
        return pRoleId;
    }

    public void setpRoleId(String pRoleId) {
        this.pRoleId = pRoleId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
}
