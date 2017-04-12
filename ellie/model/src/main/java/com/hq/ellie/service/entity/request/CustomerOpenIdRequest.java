package com.hq.ellie.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.ellie.service.entity.request
 * @创建人：yyx
 * @创建时间：17/1/23 上午10:04
 */
public class CustomerOpenIdRequest implements Serializable{

    private static final long serialVersionUID = -2880379598455118121L;
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
