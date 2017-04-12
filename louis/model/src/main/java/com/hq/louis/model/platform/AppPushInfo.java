package com.hq.louis.model.platform;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by zhaoyang on 07/02/2017.
 */
@Document(collection = "AppPushInfo")
public class AppPushInfo {

    @Id
    private String appId;
    private String platform;
    private String iosAppKey;
    private String iosSecret;
    private String androidAppKey;
    private String androidSecret;
    private Boolean prodMode;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getIosAppKey() {
        return iosAppKey;
    }

    public void setIosAppKey(String iosAppKey) {
        this.iosAppKey = iosAppKey;
    }

    public String getIosSecret() {
        return iosSecret;
    }

    public void setIosSecret(String iosSecret) {
        this.iosSecret = iosSecret;
    }

    public String getAndroidAppKey() {
        return androidAppKey;
    }

    public void setAndroidAppKey(String androidAppKey) {
        this.androidAppKey = androidAppKey;
    }

    public String getAndroidSecret() {
        return androidSecret;
    }

    public void setAndroidSecret(String androidSecret) {
        this.androidSecret = androidSecret;
    }

    public Boolean getProdMode() {
        return prodMode;
    }

    public void setProdMode(Boolean prodMode) {
        this.prodMode = prodMode;
    }

}
