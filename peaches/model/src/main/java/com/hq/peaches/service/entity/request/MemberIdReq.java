package com.hq.peaches.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.peaches.service.request
 * @创建人：yyx
 * @创建时间：16/12/14 上午10:11
 */
public class MemberIdReq implements Serializable {
    private static final long serialVersionUID = 1448058838910292348L;

    private String memberId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
