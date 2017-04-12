package com.hq.app.olaf.ui.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.hq.app.olaf.ui.bean.BaseEntity;

/**
 * Created by Administrator on 2017/2/9.
 */
public class Order extends BaseEntity<Order> implements Parcelable {

        private  String updateDate ;
        private  String orderId ;
        private  String endDate;
        private  String errorCode;
        private  String terminalId ;
        private  String body;
        private  String title;
        private  String operatorName;
        private  int receiptAmount;
        private  String errorCodeDes;
        // "fundBills": "[{\"amount\":\"0.01\",\"fundChannel\":\"PCREDIT\"}]",
        private  String fundBills ;
        //"rate": 0.0055,
        private  double rate;
        private  double rateFee;
        private  String id;
        private  String operatorId;
        private  String tradeType;
        private  String createDate;
        private  String mchId;
        private  String tMchId;
        private  String ipAddress;
        private  String updateTime;
        private  String feeType;
        private  String tradeState;
        private  int totalAmount;
        private  String tAppId;
        private  String createTime;
        private  String tUserId;
        private  String tLoginId;
        private  String tOrderId;
        private  int buyerPayAmount;
        private  String payChannel;
        private  String endTime;
        private  String nodeId;
        private  String attach;

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public int getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(int receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getErrorCodeDes() {
        return errorCodeDes;
    }

    public void setErrorCodeDes(String errorCodeDes) {
        this.errorCodeDes = errorCodeDes;
    }

    public String getFundBills() {
        return fundBills;
    }

    public void setFundBills(String fundBills) {
        this.fundBills = fundBills;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getRateFee() {
        return rateFee;
    }

    public void setRateFee(double rateFee) {
        this.rateFee = rateFee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String gettMchId() {
        return tMchId;
    }

    public void settMchId(String tMchId) {
        this.tMchId = tMchId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String gettAppId() {
        return tAppId;
    }

    public void settAppId(String tAppId) {
        this.tAppId = tAppId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String gettUserId() {
        return tUserId;
    }

    public void settUserId(String tUserId) {
        this.tUserId = tUserId;
    }

    public String gettLoginId() {
        return tLoginId;
    }

    public void settLoginId(String tLoginId) {
        this.tLoginId = tLoginId;
    }

    public String gettOrderId() {
        return tOrderId;
    }

    public void settOrderId(String tOrderId) {
        this.tOrderId = tOrderId;
    }

    public int getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public void setBuyerPayAmount(int buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.updateDate);
        dest.writeString(this.orderId);
        dest.writeString(this.endDate);
        dest.writeString(this.errorCode);
        dest.writeString(this.terminalId);
        dest.writeString(this.body);
        dest.writeString(this.title);
        dest.writeString(this.operatorName);
        dest.writeInt(this.receiptAmount);
        dest.writeString(this.errorCodeDes);
        dest.writeString(this.fundBills);
        dest.writeDouble(this.rate);
        dest.writeDouble(this.rateFee);
        dest.writeString(this.id);
        dest.writeString(this.operatorId);
        dest.writeString(this.tradeType);
        dest.writeString(this.createDate);
        dest.writeString(this.mchId);
        dest.writeString(this.tMchId);
        dest.writeString(this.ipAddress);
        dest.writeString(this.updateTime);
        dest.writeString(this.feeType);
        dest.writeString(this.tradeState);
        dest.writeInt(this.totalAmount);
        dest.writeString(this.tAppId);
        dest.writeString(this.createTime);
        dest.writeString(this.tUserId);
        dest.writeString(this.tLoginId);
        dest.writeString(this.tOrderId);
        dest.writeInt(this.buyerPayAmount);
        dest.writeString(this.payChannel);
        dest.writeString(this.endTime);
        dest.writeString(this.nodeId);
        dest.writeString(this.attach);
    }

    public Order() {
    }

    protected Order(Parcel in) {
        this.updateDate = in.readString();
        this.orderId = in.readString();
        this.endDate = in.readString();
        this.errorCode = in.readString();
        this.terminalId = in.readString();
        this.body = in.readString();
        this.title = in.readString();
        this.operatorName = in.readString();
        this.receiptAmount = in.readInt();
        this.errorCodeDes = in.readString();
        this.fundBills = in.readString();
        this.rate = in.readDouble();
        this.rateFee = in.readDouble();
        this.id = in.readString();
        this.operatorId = in.readString();
        this.tradeType = in.readString();
        this.createDate = in.readString();
        this.mchId = in.readString();
        this.tMchId = in.readString();
        this.ipAddress = in.readString();
        this.updateTime = in.readString();
        this.feeType = in.readString();
        this.tradeState = in.readString();
        this.totalAmount = in.readInt();
        this.tAppId = in.readString();
        this.createTime = in.readString();
        this.tUserId = in.readString();
        this.tLoginId = in.readString();
        this.tOrderId = in.readString();
        this.buyerPayAmount = in.readInt();
        this.payChannel = in.readString();
        this.endTime = in.readString();
        this.nodeId = in.readString();
        this.attach = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}

