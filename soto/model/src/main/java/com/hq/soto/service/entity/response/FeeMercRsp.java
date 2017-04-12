package com.hq.soto.service.entity.response;

import java.io.Serializable;
import java.util.List;

/**
 * @包名称：com.hq.soto.service.entity.response
 * @创建人：yyx
 * @创建时间：17/2/16 下午9:08
 */
public class FeeMercRsp implements Serializable{

    private Integer coreId;

    private List<FeeInfoRsp> info;

    public Integer getCoreId() {
        return coreId;
    }

    public void setCoreId(Integer coreId) {
        this.coreId = coreId;
    }

    public List<FeeInfoRsp> getInfo() {
        return info;
    }

    public void setInfo(List<FeeInfoRsp> info) {
        this.info = info;
    }
}
