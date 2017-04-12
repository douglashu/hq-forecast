package com.hq.louis.model.req;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public class Message {

    private String title;
    private String content;

    /**
     * 推送类型: 单设备, 多设备
     */
    private String type;
    private String devices;

    private Long sendTime;
    private Long expiryTime;
    private Integer maxRetryTimes;

    private String sound;

    private String osPlatform;

    private String outerId;
    private String description;
    private Long timestamp;

    // iOS Only
    private Integer badge;
    private String subTitle;

    // Android Only
    private String ticker;
    private String activity;
    private Boolean playLights;
    private Boolean playSound;
    private Boolean playVibrate;

    // 自定义字符串
    private String custom;

    public Integer getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(Integer maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public Long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getOsPlatform() {
        return osPlatform;
    }

    public void setOsPlatform(String osPlatform) {
        this.osPlatform = osPlatform;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getBadge() {
        return badge;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Boolean getPlayLights() {
        return playLights;
    }

    public void setPlayLights(Boolean playLights) {
        this.playLights = playLights;
    }

    public Boolean getPlaySound() {
        return playSound;
    }

    public void setPlaySound(Boolean playSound) {
        this.playSound = playSound;
    }

    public Boolean getPlayVibrate() {
        return playVibrate;
    }

    public void setPlayVibrate(Boolean playVibrate) {
        this.playVibrate = playVibrate;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }
}
