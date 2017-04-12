package com.hq.scrati.model;

import java.io.Serializable;

/**
 * Created by Zale on 2016/12/13.
 * Modified by Zhaoyang on 2016/12/17.
 */
public class HqRequest implements Serializable {

    // 应用ID
    private String appId;
    // 用户ID
    private String userId;
    // 商户CoreId
    private Integer coreId;
    // 商户ID
    private String mchId;
    // 商户名
    private String mchName;

    // 业务参数
    private String bizContent;

    public void copyFrom(HqRequest hqRequest) {
        setAppId(hqRequest.getAppId());
        setUserId(hqRequest.getUserId());
        setCoreId(hqRequest.getCoreId());
        setMchId(hqRequest.getMchId());
        setMchName(hqRequest.getMchName());
        setBizContent(hqRequest.getBizContent());
    }

    public String getMchName() {
        return mchName;
    }

    public void setMchName(String mchName) {
        this.mchName = mchName;
    }

    public Integer getCoreId() {
        return coreId;
    }

    public void setCoreId(Integer coreId) {
        this.coreId = coreId;
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

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    @Override
    public String toString() {
        return "HqRequest{" +
                "appId='" + appId + '\'' +
                ", userId='" + userId + '\'' +
                ", coreId='" + coreId + '\'' +
                ", mchId='" + mchId + '\'' +
                ", bizContent='" + bizContent + '\'' +
                '}';
    }
}
