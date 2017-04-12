package com.hq.flood.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.flood.service.request
 * @创建人：yyx
 * @创建时间：16/12/16 下午11:57
 */
public class PrdResourcesReq implements Serializable{

    private static final long serialVersionUID = 4680032552472204534L;
    private String prdCode;

    public String getPrdCode() {
        return prdCode;
    }

    public void setPrdCode(String prdCode) {
        this.prdCode = prdCode;
    }
}
