package com.hq.sid.service.entity.response;

import java.util.List;
import java.util.Map;

/**
 * Created by Zale on 2016/12/21.
 */
public class MercLoginResp {
    public static final String MCHID_KEY = "mchId";
    public static final String COREID_KEY = "coreId";
    public static final String MCHNAME_KEY = "mchName";
    public static final String MCHSHORTNAME_KEY = "mchShortName";
    public static final String STORENAME_KEY = "storeName";
    private String userId;
    private String userName;
    private String mobile;
    private Boolean admin;
    private Boolean needChangePwd;
    private List<Map<String,Object>> mchs;
    private String belongCoreId;
//    role (角色信息， id,  name,  desc)
    private List<RoleResp> role;

    public List<RoleResp> getRole() {
        return role;
    }

    public void setRole(List<RoleResp> role) {
        this.role = role;
    }

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

    public List<Map<String, Object>> getMchs() {
        return mchs;
    }

    public void setMchs(List<Map<String, Object>> mchs) {
        this.mchs = mchs;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getNeedChangePwd() {
        return needChangePwd;
    }

    public void setNeedChangePwd(Boolean needChangePwd) {
        this.needChangePwd = needChangePwd;
    }
}
