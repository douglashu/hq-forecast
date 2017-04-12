package com.hq.louis.model.req;

import java.util.Map;

/**
 * Created by Zale on 2017/2/15.
 */
public class SMSReq {
    private String templateId;
    private String address;
    private Map<String,String> params;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
