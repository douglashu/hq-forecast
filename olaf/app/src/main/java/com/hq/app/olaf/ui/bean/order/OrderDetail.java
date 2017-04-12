package com.hq.app.olaf.ui.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.hq.app.olaf.ui.bean.BaseEntity;

/**
 * Created by huwentao on 16-5-11.
 */
public class OrderDetail extends BaseEntity<OrderDetail> implements Parcelable {
    private double amount;//交易金额   ": 0.01,
    private String orderId;//订单号,既系统交易流水号   ": "05050000558824",
    private String tOrderId;//订单号,既系统交易流水号   ": "05050000558824",
    private String orgNo;//机构号 :"CUPS":银联  CMPAY：和包
    private String runningNo;//流水号   ": " ",
    private String settleDate;// 清算日期  ": "20160505",
    private String sn;//终端号   ": "01000088",
    private String traceAuditNo;//系统跟踪号（凭证号）   ": "000005",
    private double tradeFee;//交易手续费   ": 0,
    private String tradeState;//交易状态   ": "N",
    private String tradeTime;//订单成交时间(终端交易时间)   ": "20160511145551",
    private String tradeType;// 交易类型  ": "2010020"
    private String srefNo;
    private String tradeSts;//交易结果

    public String getSrefNo() {
        return srefNo;
    }

    public void setSrefNo(String srefNo) {
        this.srefNo = srefNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRunningNo() {
        return runningNo;
    }

    public void setRunningNo(String runningNo) {
        this.runningNo = runningNo;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTraceAuditNo() {
        return traceAuditNo;
    }

    public void setTraceAuditNo(String traceAuditNo) {
        this.traceAuditNo = traceAuditNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTradeFee() {
        return tradeFee;
    }

    public void setTradeFee(double tradeFee) {
        this.tradeFee = tradeFee;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String gettOrderId() {
        return tOrderId;
    }

    public void settOrderId(String tOrderId) {
        this.tOrderId = tOrderId;
    }

    public String getTradeSts() {
        return tradeSts;
    }

    public void setTradeSts(String tradeSts) {
        this.tradeSts = tradeSts;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.amount);
        dest.writeString(this.orderId);
        dest.writeString(this.tOrderId);
        dest.writeString(this.orgNo);
        dest.writeString(this.runningNo);
        dest.writeString(this.settleDate);
        dest.writeString(this.sn);
        dest.writeString(this.traceAuditNo);
        dest.writeDouble(this.tradeFee);
        dest.writeString(this.tradeState);
        dest.writeString(this.tradeTime);
        dest.writeString(this.tradeType);
        dest.writeString(this.srefNo);
        dest.writeString(this.tradeSts);
    }

    public OrderDetail() {
    }

    protected OrderDetail(Parcel in) {
        this.amount = in.readDouble();
        this.orderId = in.readString();
        this.tOrderId = in.readString();
        this.orgNo = in.readString();
        this.runningNo = in.readString();
        this.settleDate = in.readString();
        this.sn = in.readString();
        this.traceAuditNo = in.readString();
        this.tradeFee = in.readDouble();
        this.tradeState = in.readString();
        this.tradeTime = in.readString();
        this.tradeType = in.readString();
        this.srefNo = in.readString();
        this.tradeSts = in.readString();
    }

    public static final Creator<OrderDetail> CREATOR = new Creator<OrderDetail>() {
        @Override
        public OrderDetail createFromParcel(Parcel source) {
            return new OrderDetail(source);
        }

        @Override
        public OrderDetail[] newArray(int size) {
            return new OrderDetail[size];
        }
    };
}
