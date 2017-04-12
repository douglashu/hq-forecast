package com.hq.sid.service.entity.request;

import java.util.Date;

/**
 * Created by Zale on 2016/12/12.
 */
public class CreateOperReq {

    private Integer beloneCoreId;

    private String operName;

    private String operAlias;

    private String mobilePhone;

    private String operPwd;

    private Boolean isAdmin;

    private Boolean needChangePwd;

    private Integer crtOperId;

    private Date crtTime;

    private Integer roleType;

    private String domain;

    private String refundAuth;

    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRefundAuth() {
        return refundAuth;
    }

    public void setRefundAuth(String refundAuth) {
        this.refundAuth = refundAuth;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Boolean getNeedChangePwd() {
        return needChangePwd;
    }

    public void setNeedChangePwd(Boolean needChangePwd) {
        this.needChangePwd = needChangePwd;
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

    public String getOperPwd() {
        return operPwd;
    }

    public void setOperPwd(String operPwd) {
        this.operPwd = operPwd;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isadmin) {
        isAdmin = isadmin;
    }

    public Integer getCrtOperId() {
        return crtOperId;
    }

    public void setCrtOperId(Integer crtOperId) {
        this.crtOperId = crtOperId;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Integer getBeloneCoreId() {
        return beloneCoreId;
    }

    public void setBeloneCoreId(Integer beloneCoreId) {
        this.beloneCoreId = beloneCoreId;
    }

    @Override
    public String toString() {
        return "CreateOperReq{" +
                "beloneCoreId='" + beloneCoreId + '\'' +
                ", operName='" + operName + '\'' +
                ", operAlias='" + operAlias + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", operPwd='" + operPwd + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                ", crtOperId='" + crtOperId + '\'' +
                ", crtTime=" + crtTime +
                '}';
    }

}
