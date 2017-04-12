package com.hq.app.olaf.ui.bean.merchant;


import android.os.Parcel;
import android.os.Parcelable;

import com.hq.app.olaf.ui.base.HqPayApplication;
import com.hq.app.olaf.ui.bean.BaseEntity;
import com.hq.app.olaf.util.SharedPrefUtil;


/**
 * Created by liaob on 2016/4/26.
 */
public class Statements extends BaseEntity<Statements> implements Parcelable {
    private final static String TAG = "LOGIN_MERCHANT_STATEMENTS_OPERATORS";
    private String bankID;//银行编号
    private String provCode;//银行分支省份Code
    private String cityCode;//银行分支城市Code
    private String subbranchNo;//银行分支机构号
    private String bankAccType;//结算账户类型(对公 [0], 对私[1])
    private String bankAcc;//资金结算账户
    private String bankAccName;//银行开户户名
    private String paymentPassword;//支付密码(必须调用密码键盘加密)
    private String bankName;//": "中国工商银行",
    private String cityName;//": "长沙市",
    private String provName;//": "湖南",
    private String subbranchName;//    ": "中国工商银行长沙市马王堆支行",
    private String usrOprMbl;//管理员手机号
    private String usrOprEmail;//管理员邮箱
    private String crpAboveImg;//对私时传身份证+银行卡图片正面 ， 对公时法人身份证图片正面
    private String crpBelowImg;//对私时传身份证+银行卡图片反面 ，对公时法人身份证反面
    private String accOpeningImg;//对公时开户许可证
    private String wxpayFlg = "Y";//是否开通微信支付:Y 是，N:否
    private String alipayFlg = "Y";//是否开通支付宝支付:Y 是，N:否
    private String adminID;//管理员身份证号


    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
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
    public static Statements load() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        return prefUtil.loadObj(Statements.class);
    }

    /**
     * @return
     */
    public static void clear() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        prefUtil.clearObj(Merchant.class);
    }

    public String getUsrOprEmail() {
        return usrOprEmail;
    }

    public void setUsrOprEmail(String usrOprEmail) {
        this.usrOprEmail = usrOprEmail;
    }

    public String getCrpAboveImg() {
        return crpAboveImg;
    }

    public void setCrpAboveImg(String crpAboveImg) {
        this.crpAboveImg = crpAboveImg;
    }

    public String getCrpBelowImg() {
        return crpBelowImg;
    }

    public void setCrpBelowImg(String crpBelowImg) {
        this.crpBelowImg = crpBelowImg;
    }

    public String getAccOpeningImg() {
        return accOpeningImg;
    }

    public void setAccOpeningImg(String accOpeningImg) {
        this.accOpeningImg = accOpeningImg;
    }

    public String getWxpayFlg() {
        return wxpayFlg;
    }

    public void setWxpayFlg(String wxpayFlg) {
        this.wxpayFlg = wxpayFlg;
    }

    public String getAlipayFlg() {
        return alipayFlg;
    }

    public void setAlipayFlg(String alipayFlg) {
        this.alipayFlg = alipayFlg;
    }

    public String getUsrOprMbl() {
        return usrOprMbl;
    }

    public void setUsrOprMbl(String usrOprMbl) {
        this.usrOprMbl = usrOprMbl;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getSubbranchName() {
        return subbranchName;
    }

    public void setSubbranchName(String subbranchName) {
        this.subbranchName = subbranchName;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getProvCode() {
        return provCode;
    }

    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getSubbranchNo() {
        return subbranchNo;
    }

    public void setSubbranchNo(String subbranchNo) {
        this.subbranchNo = subbranchNo;
    }

    public String getBankAccType() {
        return bankAccType;
    }

    public void setBankAccType(String bankAccType) {
        this.bankAccType = bankAccType;
    }

    public String getBankAcc() {
        return bankAcc;
    }

    public void setBankAcc(String bankAcc) {
        this.bankAcc = bankAcc;
    }

    public String getBankAccName() {
        return bankAccName;
    }

    public void setBankAccName(String bankAccName) {
        this.bankAccName = bankAccName;
    }

    public String getPaymentPassword() {
        return paymentPassword;
    }

    public void setPaymentPassword(String paymentPassword) {
        this.paymentPassword = paymentPassword;
    }


    public Statements() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bankID);
        dest.writeString(this.provCode);
        dest.writeString(this.cityCode);
        dest.writeString(this.subbranchNo);
        dest.writeString(this.bankAccType);
        dest.writeString(this.bankAcc);
        dest.writeString(this.bankAccName);
        dest.writeString(this.paymentPassword);
        dest.writeString(this.bankName);
        dest.writeString(this.cityName);
        dest.writeString(this.provName);
        dest.writeString(this.subbranchName);
        dest.writeString(this.usrOprMbl);
        dest.writeString(this.usrOprEmail);
        dest.writeString(this.crpAboveImg);
        dest.writeString(this.crpBelowImg);
        dest.writeString(this.accOpeningImg);
        dest.writeString(this.wxpayFlg);
        dest.writeString(this.alipayFlg);
        dest.writeString(this.adminID);
    }

    protected Statements(Parcel in) {
        this.bankID = in.readString();
        this.provCode = in.readString();
        this.cityCode = in.readString();
        this.subbranchNo = in.readString();
        this.bankAccType = in.readString();
        this.bankAcc = in.readString();
        this.bankAccName = in.readString();
        this.paymentPassword = in.readString();
        this.bankName = in.readString();
        this.cityName = in.readString();
        this.provName = in.readString();
        this.subbranchName = in.readString();
        this.usrOprMbl = in.readString();
        this.usrOprEmail = in.readString();
        this.crpAboveImg = in.readString();
        this.crpBelowImg = in.readString();
        this.accOpeningImg = in.readString();
        this.wxpayFlg = in.readString();
        this.alipayFlg = in.readString();
        this.adminID = in.readString();
    }

    public static final Creator<Statements> CREATOR = new Creator<Statements>() {
        @Override
        public Statements createFromParcel(Parcel source) {
            return new Statements(source);
        }

        @Override
        public Statements[] newArray(int size) {
            return new Statements[size];
        }
    };
}
