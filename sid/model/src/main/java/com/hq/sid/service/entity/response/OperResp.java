package com.hq.sid.service.entity.response;

import java.util.List;

import java.util.Date;

/**
 * Created by Zale on 2017/2/1.
 */
public class OperResp {
    private Integer id;

    private Integer beloneCoreId;

    private String operName;

    private String operAlias;

    private String mobilePhone;

    private String operPwd;

    private Boolean needChangePwd;

    private Boolean isAdmin;

    private Date loginTime;

    private Integer failCnt;

    private String status;

    private Integer crtOperId;

    private Date crtTime;

    private Integer mdfOperId;

    private Date mdfTime;

    private List<RoleResp> role;


    private Integer operId;

    private Boolean isPush;

    private String refundPassword;

    private String refundAuth;

    public List<RoleResp> getRole() {
        return role;
    }

    public void setRole(List<RoleResp> role) {
        this.role = role;
    }

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

    public String getOperPwd() {
        return operPwd;
    }

    public void setOperPwd(String operPwd) {
        this.operPwd = operPwd;
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

    public Integer getMdfOperId() {
        return mdfOperId;
    }

    public void setMdfOperId(Integer mdfOperId) {
        this.mdfOperId = mdfOperId;
    }

    public Date getMdfTime() {
        return mdfTime;
    }

    public void setMdfTime(Date mdfTime) {
        this.mdfTime = mdfTime;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    public Boolean getIsPush() {
        return isPush;
    }

    public void setIsPush(Boolean isPush) {
        this.isPush = isPush;
    }

    public String getRefundPassword() {
        return refundPassword;
    }

    public void setRefundPassword(String refundPassword) {
        this.refundPassword = refundPassword;
    }

    public String getRefundAuth() {
        return refundAuth;
    }

    public void setRefundAuth(String refundAuth) {
        this.refundAuth = refundAuth;
    }
}
