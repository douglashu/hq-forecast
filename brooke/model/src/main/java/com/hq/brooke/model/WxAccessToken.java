package com.hq.brooke.model;

/**
 * Created by zhaoyang on 27/12/2016.
 */
public class WxAccessToken {

    private String appId;
    private String appSecret;
    private String token;
    private Long expireTime;
    private Long createTime;
    private Integer expireInSeconds;

    public void clearSecures() {
        setAppSecret(null);
    }

    @Override
    public String toString() {
        return "WxAccessToken{" +
                "appId='" + appId + '\'' +
                ", token='" + token + '\'' +
                ", expireTime=" + expireTime +
                ", createTime=" + createTime +
                ", expireInSeconds=" + expireInSeconds +
                '}';
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpireInSeconds() {
        return expireInSeconds;
    }

    public void setExpireInSeconds(Integer expireInSeconds) {
        this.expireInSeconds = expireInSeconds;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
