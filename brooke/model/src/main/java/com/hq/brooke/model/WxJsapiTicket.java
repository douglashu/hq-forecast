package com.hq.brooke.model;

/**
 * Created by zhaoyang on 03/01/2017.
 */
public class WxJsapiTicket {
    private String appId;
    private String appSecret;
    private String ticket;
    private Long expireTime;
    private Long createTime;
    private Integer expireInSeconds;

    public void clearSecures() {
        setAppSecret(null);
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getExpireInSeconds() {
        return expireInSeconds;
    }

    public void setExpireInSeconds(Integer expireInSeconds) {
        this.expireInSeconds = expireInSeconds;
    }
}
