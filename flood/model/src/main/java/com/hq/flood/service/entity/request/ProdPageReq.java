package com.hq.flood.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.flood.service.request
 * @创建人：yyx
 * @创建时间：16/12/16 下午11:35
 */
public class ProdPageReq implements Serializable{

    private static final long serialVersionUID = -700292507734620037L;
    private String prdName;
    private Integer page;
    private Integer pageSize;

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
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
