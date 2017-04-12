package com.hq.soto.service.entity.request;

import java.io.Serializable;
import java.util.Date;

/**
 * @包名称：com.hq.soto.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/16 下午11:54
 */
public class FeeMercCoreReq implements Serializable{

    private Integer coreId;

    private Integer templateId;

    private Date validDate;

    public Integer getCoreId() {
        return coreId;
    }

    public void setCoreId(Integer coreId) {
        this.coreId = coreId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }
}
