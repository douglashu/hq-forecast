package com.hq.app.olaf.ui.bean.about;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jyl on 2017/2/13.
 */

public class ProSigns implements Parcelable {

    private  float vid ;
    private  float rate ;
    private  String  name ;
    private  String  channel ;
    private  int   state ;
    private  float discountRate;

    public float getVid() {
        return vid;
    }

    public void setVid(float vid) {
        this.vid = vid;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(float discountRate) {
        this.discountRate = discountRate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.vid);
        dest.writeFloat(this.rate);
        dest.writeString(this.name);
        dest.writeString(this.channel);
        dest.writeInt(this.state);
        dest.writeFloat(this.discountRate);
    }

    public ProSigns() {
    }

    protected ProSigns(Parcel in) {
        this.vid = in.readFloat();
        this.rate = in.readFloat();
        this.name = in.readString();
        this.channel = in.readString();
        this.state = in.readInt();
        this.discountRate = in.readFloat();
    }

    public static final Creator<ProSigns> CREATOR = new Creator<ProSigns>() {
        @Override
        public ProSigns createFromParcel(Parcel source) {
            return new ProSigns(source);
        }

        @Override
        public ProSigns[] newArray(int size) {
            return new ProSigns[size];
        }
    };
}
