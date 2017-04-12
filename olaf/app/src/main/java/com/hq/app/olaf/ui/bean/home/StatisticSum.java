package com.hq.app.olaf.ui.bean.home;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jyl on 2017/2/15.
 */

public class StatisticSum  implements Parcelable {

    private  int couponVerifyCountToday;
    private  int memberIncomesToday;
    private  double   totalAmount;
    private  int   tradeCount;

    public int getCouponVerifyCountToday() {
        return couponVerifyCountToday;
    }

    public void setCouponVerifyCountToday(int couponVerifyCountToday) {
        this.couponVerifyCountToday = couponVerifyCountToday;
    }

    public int getMemberIncomesToday() {
        return memberIncomesToday;
    }

    public void setMemberIncomesToday(int memberIncomesToday) {
        this.memberIncomesToday = memberIncomesToday;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(int tradeCount) {
        this.tradeCount = tradeCount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.couponVerifyCountToday);
        dest.writeInt(this.memberIncomesToday);
        dest.writeDouble(this.totalAmount);
        dest.writeInt(this.tradeCount);
    }

    public StatisticSum() {
    }

    protected StatisticSum(Parcel in) {
        this.couponVerifyCountToday = in.readInt();
        this.memberIncomesToday = in.readInt();
        this.totalAmount = in.readDouble();
        this.tradeCount = in.readInt();
    }

    public static final Creator<StatisticSum> CREATOR = new Creator<StatisticSum>() {
        @Override
        public StatisticSum createFromParcel(Parcel source) {
            return new StatisticSum(source);
        }

        @Override
        public StatisticSum[] newArray(int size) {
            return new StatisticSum[size];
        }
    };
}
