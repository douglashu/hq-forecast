package com.hq.crash.model.auth.resp;

import com.hq.crash.model.auth.Role;
import java.util.List;

/**
 * Created by zhaoyang on 19/12/2016.
 */
public class LoginResp {

    private String userId;
    private String userName;
    private String mobile;
    private String belongCoreId;
    private List<Role> role;
    private Boolean needChangePwd;

    private List<LoginMchResp> mchs;

    public String getBelongCoreId() {
        return belongCoreId;
    }

    public void setBelongCoreId(String belongCoreId) {
        this.belongCoreId = belongCoreId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public List<LoginMchResp> getMchs() {
        return mchs;
    }

    public void setMchs(List<LoginMchResp> mchs) {
        this.mchs = mchs;
    }

    public Boolean getNeedChangePwd() {
        return needChangePwd;
    }

    public void setNeedChangePwd(Boolean needChangePwd) {
        this.needChangePwd = needChangePwd;
    }

}
