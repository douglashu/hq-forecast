package com.hq.peaches.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.peaches.service.request
 * @创建人：yyx
 * @创建时间：16/12/14 下午1:44
 */
public class MemberPageReq implements Serializable{

    private static final long serialVersionUID = 4143680455120465472L;
    private String mchId;
    private String name;
    private String phone;
    private Integer page;
    private Integer pageSize;

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
