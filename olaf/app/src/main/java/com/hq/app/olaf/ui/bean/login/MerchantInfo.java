package com.hq.app.olaf.ui.bean.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.hq.app.olaf.ui.base.HqPayApplication;
import com.hq.app.olaf.util.SharedPrefUtil;

/**
 * Created by Administrator on 2017/1/21.
 */

public class MerchantInfo implements Parcelable{
    private final static String TAG = "LOGIN_MerchantInfo";
    private  int coreId;
    private  String mchId;
    private  String mchName;
    private  String mchShortName;
    private  String storeName;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.coreId);
        dest.writeString(this.mchId);
        dest.writeString(this.mchName);
        dest.writeString(this.mchShortName);
        dest.writeString(this.storeName);
    }


    /**
     * 保存到SharePreference
     */
    public void save() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        prefUtil.save(this);
    }

    /**
     * 取出保存的数据
     *
     * @return
     */
    public static MerchantInfo load() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        return prefUtil.loadObj(MerchantInfo.class);
    }

    /**
     *
     *
     * @return
     */
    public static void clear() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        prefUtil.clearObj(MerchantInfo.class);
    }

    public MerchantInfo() {
    }

    protected MerchantInfo(Parcel in) {
        this.coreId = in.readInt();
        this.mchId = in.readString();
        this.mchName = in.readString();
        this.mchShortName = in.readString();
        this.storeName = in.readString();
    }

    public static final Creator<MerchantInfo> CREATOR = new Creator<MerchantInfo>() {
        @Override
        public MerchantInfo createFromParcel(Parcel source) {
            return new MerchantInfo(source);
        }

        @Override
        public MerchantInfo[] newArray(int size) {
            return new MerchantInfo[size];
        }
    };

    public int getCoreId() {
        return coreId;
    }

    public void setCoreId(int coreId) {
        this.coreId = coreId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchName() {
        return mchName;
    }

    public void setMchName(String mchName) {
        this.mchName = mchName;
    }

    public String getMchShortName() {
        return mchShortName;
    }

    public void setMchShortName(String mchShortName) {
        this.mchShortName = mchShortName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
