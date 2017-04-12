package com.hq.sid.service.entity.request;

/**
 * Created by Zale on 2017/2/17.
 */
public class ForgetPwdReq {
    private String loginId;
    private String code;
    private String newPwd;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
