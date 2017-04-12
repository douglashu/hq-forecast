package com.hq.sid.entity.generate;

import java.io.Serializable;
import java.util.Date;

public class TSidOper implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.BELONE_CORE_ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private Integer beloneCoreId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.OPER_NAME
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private String operName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.OPER_ALIAS
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private String operAlias;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.MOBILE_PHONE
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private String mobilePhone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.LOGIN_ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private String loginId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.OPER_PWD
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private String operPwd;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.NEED_CHANGE_PWD
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private Boolean needChangePwd;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.IS_ADMIN
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private Boolean isAdmin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.LOGIN_TIME
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private Date loginTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.FAIL_CNT
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private Integer failCnt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.STATUS
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.CRT_OPER_ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private Integer crtOperId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.CRT_TIME
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private Date crtTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.MDF_OPER_ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private Integer mdfOperId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_oper.MDF_TIME
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private Date mdfTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_sid_oper
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.ID
     *
     * @return the value of t_sid_oper.ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.ID
     *
     * @param id the value for t_sid_oper.ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.BELONE_CORE_ID
     *
     * @return the value of t_sid_oper.BELONE_CORE_ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public Integer getBeloneCoreId() {
        return beloneCoreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.BELONE_CORE_ID
     *
     * @param beloneCoreId the value for t_sid_oper.BELONE_CORE_ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setBeloneCoreId(Integer beloneCoreId) {
        this.beloneCoreId = beloneCoreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.OPER_NAME
     *
     * @return the value of t_sid_oper.OPER_NAME
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public String getOperName() {
        return operName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.OPER_NAME
     *
     * @param operName the value for t_sid_oper.OPER_NAME
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setOperName(String operName) {
        this.operName = operName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.OPER_ALIAS
     *
     * @return the value of t_sid_oper.OPER_ALIAS
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public String getOperAlias() {
        return operAlias;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.OPER_ALIAS
     *
     * @param operAlias the value for t_sid_oper.OPER_ALIAS
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setOperAlias(String operAlias) {
        this.operAlias = operAlias;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.MOBILE_PHONE
     *
     * @return the value of t_sid_oper.MOBILE_PHONE
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.MOBILE_PHONE
     *
     * @param mobilePhone the value for t_sid_oper.MOBILE_PHONE
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.LOGIN_ID
     *
     * @return the value of t_sid_oper.LOGIN_ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.LOGIN_ID
     *
     * @param loginId the value for t_sid_oper.LOGIN_ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.OPER_PWD
     *
     * @return the value of t_sid_oper.OPER_PWD
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public String getOperPwd() {
        return operPwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.OPER_PWD
     *
     * @param operPwd the value for t_sid_oper.OPER_PWD
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setOperPwd(String operPwd) {
        this.operPwd = operPwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.NEED_CHANGE_PWD
     *
     * @return the value of t_sid_oper.NEED_CHANGE_PWD
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public Boolean getNeedChangePwd() {
        return needChangePwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.NEED_CHANGE_PWD
     *
     * @param needChangePwd the value for t_sid_oper.NEED_CHANGE_PWD
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setNeedChangePwd(Boolean needChangePwd) {
        this.needChangePwd = needChangePwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.IS_ADMIN
     *
     * @return the value of t_sid_oper.IS_ADMIN
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.IS_ADMIN
     *
     * @param isAdmin the value for t_sid_oper.IS_ADMIN
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.LOGIN_TIME
     *
     * @return the value of t_sid_oper.LOGIN_TIME
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.LOGIN_TIME
     *
     * @param loginTime the value for t_sid_oper.LOGIN_TIME
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.FAIL_CNT
     *
     * @return the value of t_sid_oper.FAIL_CNT
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public Integer getFailCnt() {
        return failCnt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.FAIL_CNT
     *
     * @param failCnt the value for t_sid_oper.FAIL_CNT
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setFailCnt(Integer failCnt) {
        this.failCnt = failCnt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.STATUS
     *
     * @return the value of t_sid_oper.STATUS
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.STATUS
     *
     * @param status the value for t_sid_oper.STATUS
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.CRT_OPER_ID
     *
     * @return the value of t_sid_oper.CRT_OPER_ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public Integer getCrtOperId() {
        return crtOperId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.CRT_OPER_ID
     *
     * @param crtOperId the value for t_sid_oper.CRT_OPER_ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setCrtOperId(Integer crtOperId) {
        this.crtOperId = crtOperId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.CRT_TIME
     *
     * @return the value of t_sid_oper.CRT_TIME
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public Date getCrtTime() {
        return crtTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.CRT_TIME
     *
     * @param crtTime the value for t_sid_oper.CRT_TIME
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.MDF_OPER_ID
     *
     * @return the value of t_sid_oper.MDF_OPER_ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public Integer getMdfOperId() {
        return mdfOperId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.MDF_OPER_ID
     *
     * @param mdfOperId the value for t_sid_oper.MDF_OPER_ID
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setMdfOperId(Integer mdfOperId) {
        this.mdfOperId = mdfOperId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_oper.MDF_TIME
     *
     * @return the value of t_sid_oper.MDF_TIME
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public Date getMdfTime() {
        return mdfTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_oper.MDF_TIME
     *
     * @param mdfTime the value for t_sid_oper.MDF_TIME
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    public void setMdfTime(Date mdfTime) {
        this.mdfTime = mdfTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sid_oper
     *
     * @mbg.generated Tue Feb 21 20:05:58 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", beloneCoreId=").append(beloneCoreId);
        sb.append(", operName=").append(operName);
        sb.append(", operAlias=").append(operAlias);
        sb.append(", mobilePhone=").append(mobilePhone);
        sb.append(", loginId=").append(loginId);
        sb.append(", operPwd=").append(operPwd);
        sb.append(", needChangePwd=").append(needChangePwd);
        sb.append(", isAdmin=").append(isAdmin);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", failCnt=").append(failCnt);
        sb.append(", status=").append(status);
        sb.append(", crtOperId=").append(crtOperId);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", mdfOperId=").append(mdfOperId);
        sb.append(", mdfTime=").append(mdfTime);
        sb.append("]");
        return sb.toString();
    }
}