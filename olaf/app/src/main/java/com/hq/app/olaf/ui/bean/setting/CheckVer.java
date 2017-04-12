package com.hq.app.olaf.ui.bean.setting;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huwentao on 16-5-18.
 */
public class CheckVer implements Parcelable {
    private boolean newVersionFound;
    private AppVersion appVersion;

    public boolean isNewVersionFound() {
        return newVersionFound;
    }

    public void setNewVersionFound(boolean newVersionFound) {
        this.newVersionFound = newVersionFound;
    }

    public AppVersion getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(AppVersion appVersion) {
        this.appVersion = appVersion;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.newVersionFound ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.appVersion, flags);
    }

    public CheckVer() {
    }

    protected CheckVer(Parcel in) {
        this.newVersionFound = in.readByte() != 0;
        this.appVersion = in.readParcelable(AppVersion.class.getClassLoader());
    }

    public static final Creator<CheckVer> CREATOR = new Creator<CheckVer>() {
        @Override
        public CheckVer createFromParcel(Parcel source) {
            return new CheckVer(source);
        }

        @Override
        public CheckVer[] newArray(int size) {
            return new CheckVer[size];
        }
    };
}
