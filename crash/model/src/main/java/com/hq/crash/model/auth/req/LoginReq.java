package com.hq.crash.model.auth.req;

import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 19/12/2016.
 */
public class LoginReq extends HqRequest {

    private String account;
    private String password;
    private String passcode;
    private String osPlatform;
    private String deviceName;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getOsPlatform() {
        return osPlatform;
    }

    public void setOsPlatform(String osPlatform) {
        this.osPlatform = osPlatform;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

}
