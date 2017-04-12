package com.hq.sid.service.entity.request;

import java.util.Date;

/**
 * Created by Zale on 2016/12/12.
 */
public class UptOperReq {
    private Integer operId;
    private Integer beloneCoreId;
    private String operName;
    private String operAlias;
    private String mobilePhone;
    private Boolean needChangePwd;
    private Boolean isAdmin;
    private Date loginTime;
    private Integer failCnt;
    private String status;
    private Integer mdfOperId;
    private Date mdfTime;

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    public Integer getBeloneCoreId() {
        return beloneCoreId;
    }

    public void setBeloneCoreId(Integer beloneCoreId) {
        this.beloneCoreId = beloneCoreId;
    }

    public Boolean getNeedChangePwd() {
        return needChangePwd;
    }

    public void setNeedChangePwd(Boolean needChangePwd) {
        this.needChangePwd = needChangePwd;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Integer getMdfOperId() {
        return mdfOperId;
    }

    public void setMdfOperId(Integer mdfOperId) {
        this.mdfOperId = mdfOperId;
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

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getFailCnt() {
        return failCnt;
    }

    public void setFailCnt(Integer failCnt) {
        this.failCnt = failCnt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getMdfTime() {
        return mdfTime;
    }

    public void setMdfTime(Date mdfTime) {
        this.mdfTime = mdfTime;
    }

    @Override
    public String toString() {
        return "UptOperReq{" +
                "operId='" + operId + '\'' +
                ", beloneCoreId='" + beloneCoreId + '\'' +
                ", operName='" + operName + '\'' +
                ", operAlias='" + operAlias + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", needChangePwd='" + needChangePwd + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                ", loginTime=" + loginTime +
                ", failCnt=" + failCnt +
                ", status='" + status + '\'' +
                ", mdfOperId='" + mdfOperId + '\'' +
                ", mdfTime=" + mdfTime +
                '}';
    }
}
