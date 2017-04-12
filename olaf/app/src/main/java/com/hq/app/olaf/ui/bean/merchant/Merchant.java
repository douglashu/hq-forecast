package com.hq.app.olaf.ui.bean.merchant;

import android.os.Parcel;
import android.os.Parcelable;

import com.hq.app.olaf.ui.base.HqPayApplication;
import com.hq.app.olaf.ui.bean.BaseEntity;
import com.hq.app.olaf.util.SharedPrefUtil;

/**
 * Created by huwentao on 16-4-25.
 */
public class Merchant extends BaseEntity<Merchant> implements Parcelable {
    private final static String TAG = "LOGIN_MERCHANT_OPERATORS";
    private String id;
    private String state;
    private Basic basic;//商户基础信息
    private Statements statements;//商户结算信息
    private String runningNo;
//    private RateInfo rateInfo;//商户费率信息

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public Statements getStatements() {
        return statements;
    }

    public void setStatements(Statements statements) {
        this.statements = statements;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * 保存到SharePreference
     */
    public void save() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        prefUtil.save(this);
        if (basic != null)
            basic.save();
        if (statements != null)
            statements.save();
    }

    /**
     * @return
     */
    public static void clear() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        prefUtil.clearObj(Merchant.class);
        Basic.clear();
        Statements.clear();
    }

    /**
     * 取出保存的数据
     *
     * @return
     */
    public static Merchant load() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        Merchant merchant = prefUtil.loadObj(Merchant.class);
        if (merchant != null) {
            merchant.setBasic(Basic.load());
            merchant.setStatements(Statements.load());
        }
        return merchant;
    }

    public Merchant() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.basic, flags);
        dest.writeParcelable(this.statements, flags);
    }

    protected Merchant(Parcel in) {
        this.id = in.readString();
        this.basic = in.readParcelable(Basic.class.getClassLoader());
        this.statements = in.readParcelable(Statements.class.getClassLoader());
    }

    public static final Creator<Merchant> CREATOR = new Creator<Merchant>() {
        @Override
        public Merchant createFromParcel(Parcel source) {
            return new Merchant(source);
        }

        @Override
        public Merchant[] newArray(int size) {
            return new Merchant[size];
        }
    };

    public String getRunningNo() {
        return runningNo;
    }

    public void setRunningNo(String runningNo) {
        this.runningNo = runningNo;
    }
}
