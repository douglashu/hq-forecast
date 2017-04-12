package com.hq.sid.service.entity.request;

/**
 * Created by Zale on 2017/2/8.
 */
public class MercListSearchReq {
    private Integer page;
    private Integer pageSize;
    private MercSearchReq req;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public MercSearchReq getReq() {
        return req;
    }

    public void setReq(MercSearchReq req) {
        this.req = req;
    }

    public Integer getPage() {

        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
