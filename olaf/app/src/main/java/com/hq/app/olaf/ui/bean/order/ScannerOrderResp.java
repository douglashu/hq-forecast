package com.hq.app.olaf.ui.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.hq.app.olaf.ui.bean.BaseEntity;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ScannerOrderResp extends BaseEntity<ScannerOrderResp> implements Parcelable{

    //SWIPE_CARD
    private String bankType;
    // 交易金额单位为：分
    private  int buyerPayAmount ;
    //20170123 支付日期格式：yyyyMMdd
    private  String endDate ;
    //143226 支付时间h24mmss
    private String endTime;
    //errCode 错误码
    private  String errCode;
    // 错误描述
    private  String  errCodeDes;
    //商户单号
    private String orderId;
    //交易渠道
    private String payChannel;
    //商户实收金额
    private String receiptAmount;
    //外部渠道交易单号
    private String tOrderId;
    private String tips;
    private  int totalAmount;
    //内部订单号
    private String tradeId;
    //交易状态
    private  String tradeState;
    //交易类型
    private String tradeType;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.buyerPayAmount);
        dest.writeString(this.endDate);
        dest.writeString(this.endTime);
        dest.writeString(this.errCode);
        dest.writeString(this.errCodeDes);
        dest.writeString(this.orderId);
        dest.writeString(this.payChannel);
        dest.writeString(this.receiptAmount);
        dest.writeString(this.tOrderId);
        dest.writeString(this.tips);
        dest.writeInt(this.totalAmount);
        dest.writeString(this.tradeId);
        dest.writeString(this.tradeState);
        dest.writeString(this.tradeType);
    }

    public ScannerOrderResp() {
    }

    protected ScannerOrderResp(Parcel in) {
        this.buyerPayAmount = in.readInt();
        this.endDate = in.readString();
        this.endTime = in.readString();
        this.errCode = in.readString();
        this.errCodeDes = in.readString();
        this.orderId = in.readString();
        this.payChannel = in.readString();
        this.receiptAmount = in.readString();
        this.tOrderId = in.readString();
        this.tips = in.readString();
        this.totalAmount = in.readInt();
        this.tradeId = in.readString();
        this.tradeState = in.readString();
        this.tradeType = in.readString();
    }

    public static final Creator<ScannerOrderResp> CREATOR = new Creator<ScannerOrderResp>() {
        @Override
        public ScannerOrderResp createFromParcel(Parcel source) {
            return new ScannerOrderResp(source);
        }

        @Override
        public ScannerOrderResp[] newArray(int size) {
            return new ScannerOrderResp[size];
        }
    };

    public int getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public void setBuyerPayAmount(int buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }

    public static Creator<ScannerOrderResp> getCREATOR() {
        return CREATOR;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(String receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String gettOrderId() {
        return tOrderId;
    }

    public void settOrderId(String tOrderId) {
        this.tOrderId = tOrderId;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
