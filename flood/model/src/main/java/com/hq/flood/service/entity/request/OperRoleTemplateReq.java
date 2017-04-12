package com.hq.flood.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.flood.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/10 下午9:15
 */
public class OperRoleTemplateReq implements Serializable{

    private static final long serialVersionUID = 134811470629560667L;
    private Integer operId;

    private String coreId; // 商户id

    private Integer templateId;

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    public String getCoreId() {
        return coreId;
    }

    public void setCoreId(String coreId) {
        this.coreId = coreId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
}
