package com.hq.flood.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.flood.service.request
 * @创建人：yyx
 * @创建时间：16/12/17 上午12:01
 */
public class ResourceIdReq implements Serializable{

    private static final long serialVersionUID = -9055696498220653624L;
    private String resourceId;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
