package com.hq.flood.entity.generate;

import java.io.Serializable;

public class TFloodRoleResourceKey implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role_resource.ROLE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private String roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role_resource.RESOURCE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private String resourceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_flood_role_resource
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role_resource.ROLE_ID
     *
     * @return the value of t_flood_role_resource.ROLE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role_resource.ROLE_ID
     *
     * @param roleId the value for t_flood_role_resource.ROLE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role_resource.RESOURCE_ID
     *
     * @return the value of t_flood_role_resource.RESOURCE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role_resource.RESOURCE_ID
     *
     * @param resourceId the value for t_flood_role_resource.RESOURCE_ID
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role_resource
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
        sb.append(", resourceId=").append(resourceId);
        sb.append("]");
        return sb.toString();
    }
}