package com.hq.flood.service.entity.request;

import java.io.Serializable;
import java.util.List;

/**
 * @包名称：com.hq.flood.service.request
 * @创建人：yyx
 * @创建时间：16/12/15 下午7:16
 */
public class ResourceReq implements Serializable{

    private static final long serialVersionUID = -7824036307303304783L;

    private String resourceId;

    private String systemId;

    private String resourceName;

    private String resourceCode;

    private String resourceType;

    private String resourceDesc;

    private String resourceStatus;

    private String resourceParentId;

    private String resourceOrderId;

    private List<String> prdId;

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

    public List<String> getPrdId() {
        return prdId;
    }

    public void setPrdId(List<String> prdId) {
        this.prdId = prdId;
    }
}
