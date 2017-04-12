package com.hq.app.olaf.ui.bean.login;


import com.hq.app.olaf.ui.bean.BaseEntity;

/**
 * Created by liaob on 2016/4/26.
 */
public class Challenge extends BaseEntity<Challenge> {
    private  String value;//挑战值
    private  long expiryTime;//挑战值失效时间

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }
}
