package com.hq.app.olaf.ui.bean.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.hq.app.olaf.ui.base.HqPayApplication;

import java.util.List;

/**
 * Created by huwentao on 16/7/4.
 */
public class Permissions implements Parcelable {
    private final static String TAG = "LOGIN_USER_PERMISSION";
    private String id;
    private String name;
    private String value;

    public static List<Permissions> load() {
        HqPayApplication application = (HqPayApplication) HqPayApplication.getAppContext();
        return application.getArrays(TAG, Permissions.class);
    }

    public void save() {
        HqPayApplication application = (HqPayApplication) HqPayApplication.getAppContext();
        application.saveCache(TAG, this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.value);
    }

    protected Permissions(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Creator<Permissions> CREATOR = new Creator<Permissions>() {
        @Override
        public Permissions createFromParcel(Parcel source) {
            return new Permissions(source);
        }

        @Override
        public Permissions[] newArray(int size) {
            return new Permissions[size];
        }
    };
}
