package com.hq.sid.service.entity.request;

import java.util.Date;

/**
 * Created by Zale on 2016/12/18.
 */
public class OperSearchReq {
    private Integer id;

    private Integer beloneCoreId;

    private String operName;

    private String operAlias;

    private String mobilePhone;

    private Boolean needChangePwd;

    private Boolean isAdmin;

    private Date sloginTime;
    private Date eloginTime;

    private Integer failCnt;

    private String status;

    private Integer crtOperId;

    private Date sCrtTime;
    private Date eCrtTime;

    private Integer mdfOperId;

    private Date sMdfTime;
    private Date eMdfTime;

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

    public Date getsCrtTime() {
        return sCrtTime;
    }

    public void setsCrtTime(Date sCrtTime) {
        this.sCrtTime = sCrtTime;
    }

    public Date geteCrtTime() {
        return eCrtTime;
    }

    public void seteCrtTime(Date eCrtTime) {
        this.eCrtTime = eCrtTime;
    }

    public Integer getMdfOperId() {
        return mdfOperId;
    }

    public void setMdfOperId(Integer mdfOperId) {
        this.mdfOperId = mdfOperId;
    }

    public Date getsMdfTime() {
        return sMdfTime;
    }

    public void setsMdfTime(Date sMdfTime) {
        this.sMdfTime = sMdfTime;
    }

    public Date geteMdfTime() {
        return eMdfTime;
    }

    public void seteMdfTime(Date eMdfTime) {
        this.eMdfTime = eMdfTime;
    }

    public Date getSloginTime() {
        return sloginTime;
    }

    public void setSloginTime(Date sloginTime) {
        this.sloginTime = sloginTime;
    }

    public Date getEloginTime() {
        return eloginTime;
    }

    public void setEloginTime(Date eloginTime) {
        this.eloginTime = eloginTime;
    }
}
