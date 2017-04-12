package com.hq.sid.service.entity.response;

import java.io.Serializable;

/**
 * Created by Zale on 2017/2/7.
 */
public class SystemLoginResp implements Serializable{
    private static final long serialVersionUID = -8482061353072060414L;
    private Integer id;

    private Integer beloneCoreId;

    private String operName;

    private String operAlias;

    private String mobilePhone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBeloneCoreId() {
        return beloneCoreId;
    }

    public void setBeloneCoreId(Integer beloneCoreId) {
        this.beloneCoreId = beloneCoreId;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getOperAlias() {
        return operAlias;
    }

    public void setOperAlias(String operAlias) {
        this.operAlias = operAlias;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
