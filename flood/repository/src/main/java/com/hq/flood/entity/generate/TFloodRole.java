package com.hq.flood.entity.generate;

import java.io.Serializable;
import java.util.Date;

public class TFloodRole implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role.ROLE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private String roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role.URM_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private String urmId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role.SYSTEM_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private String systemId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role.ROLE_NAME
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private String roleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role.ROLE_DESC
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private String roleDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role.ROLE_STATUS
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private String roleStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role.CREATE_TIME
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role.LASTUPDATETIME
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private Date lastupdatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role.MODIFIED_USER
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private String modifiedUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role.NODE_CODE
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private String nodeCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role.P_ROLE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private String pRoleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role.TEMPLATE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private Integer templateId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_flood_role
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role.ROLE_ID
     *
     * @return the value of t_flood_role.ROLE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role.ROLE_ID
     *
     * @param roleId the value for t_flood_role.ROLE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role.URM_ID
     *
     * @return the value of t_flood_role.URM_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public String getUrmId() {
        return urmId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role.URM_ID
     *
     * @param urmId the value for t_flood_role.URM_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setUrmId(String urmId) {
        this.urmId = urmId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role.SYSTEM_ID
     *
     * @return the value of t_flood_role.SYSTEM_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role.SYSTEM_ID
     *
     * @param systemId the value for t_flood_role.SYSTEM_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role.ROLE_NAME
     *
     * @return the value of t_flood_role.ROLE_NAME
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role.ROLE_NAME
     *
     * @param roleName the value for t_flood_role.ROLE_NAME
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role.ROLE_DESC
     *
     * @return the value of t_flood_role.ROLE_DESC
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public String getRoleDesc() {
        return roleDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role.ROLE_DESC
     *
     * @param roleDesc the value for t_flood_role.ROLE_DESC
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role.ROLE_STATUS
     *
     * @return the value of t_flood_role.ROLE_STATUS
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public String getRoleStatus() {
        return roleStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role.ROLE_STATUS
     *
     * @param roleStatus the value for t_flood_role.ROLE_STATUS
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setRoleStatus(String roleStatus) {
        this.roleStatus = roleStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role.CREATE_TIME
     *
     * @return the value of t_flood_role.CREATE_TIME
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role.CREATE_TIME
     *
     * @param createTime the value for t_flood_role.CREATE_TIME
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role.LASTUPDATETIME
     *
     * @return the value of t_flood_role.LASTUPDATETIME
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public Date getLastupdatetime() {
        return lastupdatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role.LASTUPDATETIME
     *
     * @param lastupdatetime the value for t_flood_role.LASTUPDATETIME
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setLastupdatetime(Date lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role.MODIFIED_USER
     *
     * @return the value of t_flood_role.MODIFIED_USER
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public String getModifiedUser() {
        return modifiedUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role.MODIFIED_USER
     *
     * @param modifiedUser the value for t_flood_role.MODIFIED_USER
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role.NODE_CODE
     *
     * @return the value of t_flood_role.NODE_CODE
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public String getNodeCode() {
        return nodeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role.NODE_CODE
     *
     * @param nodeCode the value for t_flood_role.NODE_CODE
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role.P_ROLE_ID
     *
     * @return the value of t_flood_role.P_ROLE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public String getpRoleId() {
        return pRoleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role.P_ROLE_ID
     *
     * @param pRoleId the value for t_flood_role.P_ROLE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setpRoleId(String pRoleId) {
        this.pRoleId = pRoleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role.TEMPLATE_ID
     *
     * @return the value of t_flood_role.TEMPLATE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role.TEMPLATE_ID
     *
     * @param templateId the value for t_flood_role.TEMPLATE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleId=").append(roleId);
        sb.append(", urmId=").append(urmId);
        sb.append(", systemId=").append(systemId);
        sb.append(", roleName=").append(roleName);
        sb.append(", roleDesc=").append(roleDesc);
        sb.append(", roleStatus=").append(roleStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastupdatetime=").append(lastupdatetime);
        sb.append(", modifiedUser=").append(modifiedUser);
        sb.append(", nodeCode=").append(nodeCode);
        sb.append(", pRoleId=").append(pRoleId);
        sb.append(", templateId=").append(templateId);
        sb.append("]");
        return sb.toString();
    }
}