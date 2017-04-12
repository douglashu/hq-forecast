package com.hq.flood.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.flood.service.request
 * @创建人：yyx
 * @创建时间：16/12/16 下午11:29
 */
public class ProdCodeReq implements Serializable{
    private static final long serialVersionUID = 7025332640623515865L;
    private String prdCode;

    public String getPrdCode() {
        return prdCode;
    }

    public void setPrdCode(String prdCode) {
        this.prdCode = prdCode;
    }
}
