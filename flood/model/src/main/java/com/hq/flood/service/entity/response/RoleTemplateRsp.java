package com.hq.flood.service.entity.response;

import java.io.Serializable;

/**
 * @包名称：com.hq.flood.service.entity.response
 * @创建人：yyx
 * @创建时间：17/2/10 下午2:52
 */
public class RoleTemplateRsp implements Serializable{

    private Integer id;

    private String templateName;

    private Integer sequence;

    private Integer state;

    private String describes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
