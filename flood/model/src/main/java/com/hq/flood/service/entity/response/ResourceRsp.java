package com.hq.flood.service.entity.response;

import java.io.Serializable;
import java.util.List;

/**
 * @包名称：com.hq.flood.service.response
 * @创建人：yyx
 * @创建时间：16/12/15 下午7:17
 */
public class ResourceRsp implements Serializable{
    private static final long serialVersionUID = 1032291385390211986L;

    private String roleId;

    private String roleName;

    private Integer templateId;

    private String resourceId;

    private String systemId;

    private String resourceName;

    private String resourceCode;

    private String resourceType;

    private String resourceDesc;

    private String resourceStatus;

    private String resourceParentId;

    private String resourceOrderId;

    private String prdId;


    /**
     * 子节点
     **/
    private List<ResourceRsp> childs;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    public String getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(String resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    public String getResourceParentId() {
        return resourceParentId;
    }

    public void setResourceParentId(String resourceParentId) {
        this.resourceParentId = resourceParentId;
    }

    public String getResourceOrderId() {
        return resourceOrderId;
    }

    public void setResourceOrderId(String resourceOrderId) {
        this.resourceOrderId = resourceOrderId;
    }

    public String getPrdId() {
        return prdId;
    }

    public void setPrdId(String prdId) {
        this.prdId = prdId;
    }

    public List<ResourceRsp> getChilds() {
        return childs;
    }

    public void setChilds(List<ResourceRsp> childs) {
        this.childs = childs;
    }
}
