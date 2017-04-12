package com.hq.louis.model.req;


import java.io.Serializable;

/**
 * @包名称：com.hq.sid.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/5 下午10:04
 */
public class DeviceBindReq implements Serializable {

    private static final long serialVersionUID = 7883781949392751832L;

    private String osplatform;
    private String deviceToken;
    private String key;
    private String grpId;
    private Long expiryTime;

    public Long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getGrpId() {
        return grpId;
    }

    public void setGrpId(String grpId) {
        this.grpId = grpId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOsplatform() {
        return osplatform;
    }

    public void setOsplatform(String osplatform) {
        this.osplatform = osplatform;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

}
