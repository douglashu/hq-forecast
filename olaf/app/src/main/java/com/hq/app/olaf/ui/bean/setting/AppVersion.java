package com.hq.app.olaf.ui.bean.setting;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jyl on 2017/2/14.
 */

public class AppVersion implements Parcelable {
    private  String whatsNew;
    private String createTime;
    private  String appId;
    private String updateTime;
    private  boolean force;
    private  String id;
    private  String version;
    private  String url;
    private  String platform;

    public String getWhatsNew() {
        return whatsNew;
    }

    public void setWhatsNew(String whatsNew) {
        this.whatsNew = whatsNew;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.whatsNew);
        dest.writeString(this.createTime);
        dest.writeString(this.appId);
        dest.writeString(this.updateTime);
        dest.writeByte(this.force ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
        dest.writeString(this.version);
        dest.writeString(this.url);
        dest.writeString(this.platform);
    }

    public AppVersion() {
    }

    protected AppVersion(Parcel in) {
        this.whatsNew = in.readString();
        this.createTime = in.readString();
        this.appId = in.readString();
        this.updateTime = in.readString();
        this.force = in.readByte() != 0;
        this.id = in.readString();
        this.version = in.readString();
        this.url = in.readString();
        this.platform = in.readString();
    }

    public static final Creator<AppVersion> CREATOR = new Creator<AppVersion>() {
        @Override
        public AppVersion createFromParcel(Parcel source) {
            return new AppVersion(source);
        }

        @Override
        public AppVersion[] newArray(int size) {
            return new AppVersion[size];
        }
    };
}
