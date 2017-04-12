package com.hq.app.olaf.ui.bean.about;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jyl on 2017/2/13.
 */

public class StoreCodes implements Parcelable {
    private  int id;
    private  long createTime;
    private  long updateTime;
    private  String coreId;
    private  String name;
    private  String state;
    private  String type;
    private  String url;

    private String composeUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getCoreId() {
        return coreId;
    }

    public void setCoreId(String coreId) {
        this.coreId = coreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComposeUrl() {
        return composeUrl;
    }

    public void setComposeUrl(String composeUrl) {
        this.composeUrl = composeUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeLong(this.createTime);
        dest.writeLong(this.updateTime);
        dest.writeString(this.coreId);
        dest.writeString(this.name);
        dest.writeString(this.state);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeString(this.composeUrl);
    }

    public StoreCodes() {
    }

    protected StoreCodes(Parcel in) {
        this.id = in.readInt();
        this.createTime = in.readLong();
        this.updateTime = in.readLong();
        this.coreId = in.readString();
        this.name = in.readString();
        this.state = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.composeUrl = in.readString();
    }

    public static final Creator<StoreCodes> CREATOR = new Creator<StoreCodes>() {
        @Override
        public StoreCodes createFromParcel(Parcel source) {
            return new StoreCodes(source);
        }

        @Override
        public StoreCodes[] newArray(int size) {
            return new StoreCodes[size];
        }
    };
}
