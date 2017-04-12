package com.hq.sid.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.sid.service.entity.request
 * @创建人：yyx
 * @创建时间：17/1/26 下午4:15
 */
public class QrCodePageReq implements Serializable {

    private static final long serialVersionUID = 2846569346009866954L;

    private Integer coreId;

    private String type;

    private String name;

    private Integer page;

    private Integer pageSize;

    public Integer getCoreId() {
        return coreId;
    }

    public void setCoreId(Integer coreId) {
        this.coreId = coreId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
