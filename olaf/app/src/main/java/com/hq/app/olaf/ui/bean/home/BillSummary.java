package com.hq.app.olaf.ui.bean.home;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jyl on 2017/2/18.
 */

public class BillSummary implements Parcelable{

    private float aliTotalAmount;
    private Integer aliTradeCount;
    private float incomeAmount;
    private float rateFee;
    private float receiptAmount;
    private float refundAmount;
    private Integer refundCount;
    private float refundRateFee;
    private float totalAmount;
    private Integer tradeCount;
    private float wxTotalAmount;
    private Integer wxTradeCount;

    public float getAliTotalAmount() {
        return aliTotalAmount;
    }

    public void setAliTotalAmount(float aliTotalAmount) {
        this.aliTotalAmount = aliTotalAmount;
    }

    public Integer getAliTradeCount() {
        return aliTradeCount;
    }

    public void setAliTradeCount(Integer aliTradeCount) {
        this.aliTradeCount = aliTradeCount;
    }

    public float getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(float incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public float getRateFee() {
        return rateFee;
    }

    public void setRateFee(float rateFee) {
        this.rateFee = rateFee;
    }

    public float getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(float receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public float getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(float refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Integer getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(Integer refundCount) {
        this.refundCount = refundCount;
    }

    public float getRefundRateFee() {
        return refundRateFee;
    }

    public void setRefundRateFee(float refundRateFee) {
        this.refundRateFee = refundRateFee;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Integer tradeCount) {
        this.tradeCount = tradeCount;
    }

    public float getWxTotalAmount() {
        return wxTotalAmount;
    }

    public void setWxTotalAmount(float wxTotalAmount) {
        this.wxTotalAmount = wxTotalAmount;
    }

    public Integer getWxTradeCount() {
        return wxTradeCount;
    }

    public void setWxTradeCount(Integer wxTradeCount) {
        this.wxTradeCount = wxTradeCount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.aliTotalAmount);
        dest.writeValue(this.aliTradeCount);
        dest.writeFloat(this.incomeAmount);
        dest.writeFloat(this.rateFee);
        dest.writeFloat(this.receiptAmount);
        dest.writeFloat(this.refundAmount);
        dest.writeValue(this.refundCount);
        dest.writeFloat(this.refundRateFee);
        dest.writeFloat(this.totalAmount);
        dest.writeValue(this.tradeCount);
        dest.writeFloat(this.wxTotalAmount);
        dest.writeValue(this.wxTradeCount);
    }

    public BillSummary() {
    }

    protected BillSummary(Parcel in) {
        this.aliTotalAmount = in.readFloat();
        this.aliTradeCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.incomeAmount = in.readFloat();
        this.rateFee = in.readFloat();
        this.receiptAmount = in.readFloat();
        this.refundAmount = in.readFloat();
        this.refundCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.refundRateFee = in.readFloat();
        this.totalAmount = in.readFloat();
        this.tradeCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.wxTotalAmount = in.readFloat();
        this.wxTradeCount = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<BillSummary> CREATOR = new Creator<BillSummary>() {
        @Override
        public BillSummary createFromParcel(Parcel source) {
            return new BillSummary(source);
        }

        @Override
        public BillSummary[] newArray(int size) {
            return new BillSummary[size];
        }
    };
}
