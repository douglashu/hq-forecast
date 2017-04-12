package com.hq.louis.model.umeng;

import com.hq.louis.model.apns.APNs;

import java.util.Map;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public class UMengMsgPayload {

    // 消息类型，值可以为: notification-通知，message-消息
    private String display_type;

    private UMengMsgBody body;

    private APNs aps;

    private Map<String, String> extra;

    private String custom;

    public String getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(String display_type) {
        this.display_type = display_type;
    }

    public UMengMsgBody getBody() {
        return body;
    }

    public void setBody(UMengMsgBody body) {
        this.body = body;
    }

    public APNs getAps() {
        return aps;
    }

    public void setAps(APNs aps) {
        this.aps = aps;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }
}
