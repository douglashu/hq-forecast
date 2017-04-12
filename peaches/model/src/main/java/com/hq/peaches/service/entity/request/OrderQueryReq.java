package com.hq.peaches.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.tony.service.request
 * @创建人：yyx
 * @创建时间：17/1/23 下午8:25
 */
public class OrderQueryReq implements Serializable{


    private String id;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
