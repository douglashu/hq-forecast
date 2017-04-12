package com.hq.app.olaf.ui.bean.oper;

import android.os.Parcel;
import android.os.Parcelable;

import com.hq.app.olaf.ui.bean.role.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwentao on 16/6/23.
 */
public class Operator implements Parcelable {

    private String crtTime;//      ": "1487173086000",
    private boolean needChangePwd;
    private int failCnt;
    private String loginTime;//      ": "1487173086000",
    private String mobilePhone    ;//      ": "18697964649",
    private Integer crtOperId;
    private String operAlias;
    private Integer beloneCoreId;
    private String operName;
    private Integer id        ;//      ": "1467618871033630",
    private String status      ;// ": "00",
    private String refundAuth    ;// 00 ：不允许退款",01 ："只可退自己的",02 ："退全部"
    private List<Role> role =new ArrayList<Role>();


    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public boolean isNeedChangePwd() {
        return needChangePwd;
    }

    public void setNeedChangePwd(boolean needChangePwd) {
        this.needChangePwd = needChangePwd;
    }

    public int getFailCnt() {
        return failCnt;
    }

    public void setFailCnt(int failCnt) {
        this.failCnt = failCnt;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Integer getCrtOperId() {
        return crtOperId;
    }

    public void setCrtOperId(Integer crtOperId) {
        this.crtOperId = crtOperId;
    }

    public String getOperAlias() {
        return operAlias;
    }

    public void setOperAlias(String operAlias) {
        this.operAlias = operAlias;
    }

    public Integer getBeloneCoreId() {
        return beloneCoreId;
    }

    public void setBeloneCoreId(Integer beloneCoreId) {
        this.beloneCoreId = beloneCoreId;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefundAuth() {
        return refundAuth;
    }

    public void setRefundAuth(String refundAuth) {
        this.refundAuth = refundAuth;
    }

    public List<Role> getRole() {

        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.crtTime);
        dest.writeByte(this.needChangePwd ? (byte) 1 : (byte) 0);
        dest.writeInt(this.failCnt);
        dest.writeString(this.loginTime);
        dest.writeString(this.mobilePhone);
        dest.writeValue(this.crtOperId);
        dest.writeString(this.operAlias);
        dest.writeValue(this.beloneCoreId);
        dest.writeString(this.operName);
        dest.writeValue(this.id);
        dest.writeString(this.status);
        dest.writeString(this.refundAuth);
        dest.writeTypedList(this.role);
    }

    public Operator() {
    }

    protected Operator(Parcel in) {
        this.crtTime = in.readString();
        this.needChangePwd = in.readByte() != 0;
        this.failCnt = in.readInt();
        this.loginTime = in.readString();
        this.mobilePhone = in.readString();
        this.crtOperId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.operAlias = in.readString();
        this.beloneCoreId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.operName = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = in.readString();
        this.refundAuth = in.readString();
        this.role = in.createTypedArrayList(Role.CREATOR);
    }

    public static final Creator<Operator> CREATOR = new Creator<Operator>() {
        @Override
        public Operator createFromParcel(Parcel source) {
            return new Operator(source);
        }

        @Override
        public Operator[] newArray(int size) {
            return new Operator[size];
        }
    };
}
