package com.hq.crash.model.auth;

import com.hq.crash.model.auth.resp.LoginMchResp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.List;

/**
 * Created by zhaoyang on 18/12/2016.
 */
@Document(collection = "UserSession")
public class UserSession {

    @Id
    private String id;
    private String appId;

    private String userId;
    private String userName;
    private String mobile;
    private String belongCoreId;

    private String activeMch;
    private List<LoginMchResp> mchs;

    // 授权访问令牌
    private String authToken;
    private String deviceName;
    private String osPlatform;

    // 角色
    private Role role;

    private Long expiryTime;
    private Long createTime;

    private Boolean pwdNeedReset;

    public LoginMchResp activeMchInfo() {
        if(CollectionUtils.isEmpty(getMchs())) return null;
        if(StringUtils.isEmpty(getActiveMch())) {
            return getMchs().get(0);
        }
        for(LoginMchResp mchResp: getMchs()) {
            if(mchResp.getMchId().equals(getActiveMch())) return mchResp;
        }
        return null;
    }

    public String getActiveMch() {
        return activeMch;
    }

    public void setActiveMch(String activeMch) {
        this.activeMch = activeMch;
    }

    public String getBelongCoreId() {
        return belongCoreId;
    }

    public void setBelongCoreId(String belongCoreId) {
        this.belongCoreId = belongCoreId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getOsPlatform() {
        return osPlatform;
    }

    public void setOsPlatform(String osPlatform) {
        this.osPlatform = osPlatform;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public List<LoginMchResp> getMchs() {
        return mchs;
    }

    public void setMchs(List<LoginMchResp> mchs) {
        this.mchs = mchs;
    }

    public Boolean getPwdNeedReset() {
        return pwdNeedReset;
    }

    public void setPwdNeedReset(Boolean pwdNeedReset) {
        this.pwdNeedReset = pwdNeedReset;
    }
}
