package com.hq.sid.entity.generate;

import java.io.Serializable;
import java.util.Date;

public class TSidDevice implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_device.key_id
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    private String keyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_device.app_id
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    private String appId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_device.mch_id
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    private String mchId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_device.user_id
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    private String userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_device.osplatform
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    private String osplatform;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_device.device_token
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    private String deviceToken;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_device.state
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    private Boolean state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_device.create_time
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sid_device.update_time
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_sid_device
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_device.key_id
     *
     * @return the value of t_sid_device.key_id
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public String getKeyId() {
        return keyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_device.key_id
     *
     * @param keyId the value for t_sid_device.key_id
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_device.app_id
     *
     * @return the value of t_sid_device.app_id
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public String getAppId() {
        return appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_device.app_id
     *
     * @param appId the value for t_sid_device.app_id
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_device.mch_id
     *
     * @return the value of t_sid_device.mch_id
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public String getMchId() {
        return mchId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_device.mch_id
     *
     * @param mchId the value for t_sid_device.mch_id
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_device.user_id
     *
     * @return the value of t_sid_device.user_id
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_device.user_id
     *
     * @param userId the value for t_sid_device.user_id
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_device.osplatform
     *
     * @return the value of t_sid_device.osplatform
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public String getOsplatform() {
        return osplatform;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_device.osplatform
     *
     * @param osplatform the value for t_sid_device.osplatform
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public void setOsplatform(String osplatform) {
        this.osplatform = osplatform;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_device.device_token
     *
     * @return the value of t_sid_device.device_token
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_device.device_token
     *
     * @param deviceToken the value for t_sid_device.device_token
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_device.state
     *
     * @return the value of t_sid_device.state
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public Boolean getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_device.state
     *
     * @param state the value for t_sid_device.state
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public void setState(Boolean state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_device.create_time
     *
     * @return the value of t_sid_device.create_time
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_device.create_time
     *
     * @param createTime the value for t_sid_device.create_time
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sid_device.update_time
     *
     * @return the value of t_sid_device.update_time
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sid_device.update_time
     *
     * @param updateTime the value for t_sid_device.update_time
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sid_device
     *
     * @mbg.generated Wed Feb 15 15:40:09 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", keyId=").append(keyId);
        sb.append(", appId=").append(appId);
        sb.append(", mchId=").append(mchId);
        sb.append(", userId=").append(userId);
        sb.append(", osplatform=").append(osplatform);
        sb.append(", deviceToken=").append(deviceToken);
        sb.append(", state=").append(state);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}