package com.hq.app.olaf.ui.bean.merchant;


import android.os.Parcel;
import android.os.Parcelable;

import com.hq.app.olaf.ui.bean.BaseEntity;

/**
 * Created by liaob on 2016/4/26.
 */
public class RateInfo extends BaseEntity<RateInfo> implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public RateInfo() {
    }

    protected RateInfo(Parcel in) {
    }

    public static final Creator<RateInfo> CREATOR = new Creator<RateInfo>() {
        @Override
        public RateInfo createFromParcel(Parcel source) {
            return new RateInfo(source);
        }

        @Override
        public RateInfo[] newArray(int size) {
            return new RateInfo[size];
        }
    };
}
