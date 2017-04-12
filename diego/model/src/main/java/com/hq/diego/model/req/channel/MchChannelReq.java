package com.hq.diego.model.req.channel;

import java.math.BigDecimal;

/**
 * Created by zhaoyang on 22/03/2017.
 */
public class MchChannelReq {

    private String id;
    private String channel;
    private String mchId;
    private String appId;
    private String state;
    private String apiKey;
    private BigDecimal codePayRate;
    private BigDecimal h5PayRate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public BigDecimal getCodePayRate() {
        return codePayRate;
    }

    public void setCodePayRate(BigDecimal codePayRate) {
        this.codePayRate = codePayRate;
    }

    public BigDecimal getH5PayRate() {
        return h5PayRate;
    }

    public void setH5PayRate(BigDecimal h5PayRate) {
        this.h5PayRate = h5PayRate;
    }

}
