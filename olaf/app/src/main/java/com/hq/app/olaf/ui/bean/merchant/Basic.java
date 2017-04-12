package com.hq.app.olaf.ui.bean.merchant;


import android.os.Parcel;
import android.os.Parcelable;

import com.hq.app.olaf.ui.base.HqPayApplication;
import com.hq.app.olaf.ui.bean.BaseEntity;
import com.hq.app.olaf.util.SharedPrefUtil;

/**
 * Created by liaob on 2016/4/26.
 */
public class Basic extends BaseEntity<Basic> implements Parcelable {
    private final static String TAG = "LOGIN_MERCHANT_BASIC_OPERATORS";
    private String name;//商户名称(与营业执照上名称一致)
    private String shortName;//商户简称
    private String mcc;//商户行业类别代码
    private String mccName;//商户行业类别描述
    private String provCode;//省份代码
    private String provName;
    private String cityCode;//城市代码
    private String cityName;//城市代码
    private String bizAddr;//营业地址
    private String bizCat;//
    private String industryCat;
    private String contactPhone;//商户联系方式
    private String bizLicenseID;//营业执照号
    private String bizLicEndDate;//营业执照到期日期
    private String taxRegID;//税务登记号
    private String orgID;//组织机构代码号
    private String capital;//注册资本
    private String corporate;//法人姓名
    private String corporateID;//法人身份证号
    private String corpIDEndDate;//法人身份证到期日期
    private String type;//商户类型(连锁关系),总店[0],分店[1],加盟店[2]
    private String masterID;//总店商户号(连锁关系为分店或加盟店才可填写)
    private String masterName;//总店全称(连锁关系为分店或加盟店才可填写)
    private String adminName;//管理员姓名
    private String adminID;//管理员身份证号
    private String attr;//商户性质
    private String attrName;
    private String usrOprMbl;//管理员手机号
    private String state;//0:正常 1:销户 3:失效 4:黑名单 5:冻结
    private String clrMerc;//清算商户号
    private String busLtcImg;//营业执照副本扫描件
    private String doorHeadImg;//店铺门头照
    private String accManagerPhone;//客户经理联系电话
    private String cashierImg;//收银台照
    private String inStoreImg;//店内照
    private String auditTrails;//拒绝原因
    private String threePartsBillImg;//三联单


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
    public static Basic load() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        return prefUtil.loadObj(Basic.class);
    }
    /**
     *
     *
     * @return
     */
    public static void clear() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        prefUtil.clearObj(Merchant.class);
    }
    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getIndustryCat() {
        return industryCat;
    }

    public void setIndustryCat(String industryCat) {
        this.industryCat = industryCat;
    }

    public String getMasterID() {
        return masterID;
    }

    public void setMasterID(String masterID) {
        this.masterID = masterID;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getBizCat() {
        return bizCat;
    }

    public void setBizCat(String bizCat) {
        this.bizCat = bizCat;
    }

    public String getClrMerc() {
        return clrMerc;
    }

    public void setClrMerc(String clrMerc) {
        this.clrMerc = clrMerc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUsrOprMbl() {
        return usrOprMbl;
    }

    public void setUsrOprMbl(String usrOprMbl) {
        this.usrOprMbl = usrOprMbl;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getBizLicEndDate() {
        return bizLicEndDate;
    }

    public void setBizLicEndDate(String bizLicEndDate) {
        this.bizLicEndDate = bizLicEndDate;
    }

    public String getCorpIDEndDate() {
        return corpIDEndDate;
    }

    public void setCorpIDEndDate(String corpIDEndDate) {
        this.corpIDEndDate = corpIDEndDate;
    }

    public String getMccName() {
        return mccName;
    }

    public void setMccName(String mccName) {
        this.mccName = mccName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getProvCode() {
        return provCode;
    }

    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    public String getThreePartsBillImg() {
        return threePartsBillImg;
    }

    public void setThreePartsBillImg(String threePartsBillImg) {
        this.threePartsBillImg = threePartsBillImg;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getBizAddr() {
        return bizAddr;
    }

    public void setBizAddr(String bizAddr) {
        this.bizAddr = bizAddr;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }


    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getAuditTrails() {
        return auditTrails;
    }

    public void setAuditTrails(String auditTrails) {
        this.auditTrails = auditTrails;
    }

    public String getCorporate() {
        return corporate;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getBizLicenseID() {
        return bizLicenseID;
    }

    public void setBizLicenseID(String bizLicenseID) {
        this.bizLicenseID = bizLicenseID;
    }

    public String getTaxRegID() {
        return taxRegID;
    }

    public void setTaxRegID(String taxRegID) {
        this.taxRegID = taxRegID;
    }

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getCorporateID() {
        return corporateID;
    }

    public void setCorporateID(String corporateID) {
        this.corporateID = corporateID;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getBusLtcImg() {
        return busLtcImg;
    }

    public void setBusLtcImg(String busLtcImg) {
        this.busLtcImg = busLtcImg;
    }

    public String getDoorHeadImg() {
        return doorHeadImg;
    }

    public void setDoorHeadImg(String doorHeadImg) {
        this.doorHeadImg = doorHeadImg;
    }

    public String getAccManagerPhone() {
        return accManagerPhone;
    }

    public void setAccManagerPhone(String accManagerPhone) {
        this.accManagerPhone = accManagerPhone;
    }

    public String getCashierImg() {
        return cashierImg;
    }

    public void setCashierImg(String cashierImg) {
        this.cashierImg = cashierImg;
    }

    public String getInStoreImg() {
        return inStoreImg;
    }

    public void setInStoreImg(String inStoreImg) {
        this.inStoreImg = inStoreImg;
    }

    public Basic() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.shortName);
        dest.writeString(this.mcc);
        dest.writeString(this.mccName);
        dest.writeString(this.provCode);
        dest.writeString(this.provName);
        dest.writeString(this.cityCode);
        dest.writeString(this.cityName);
        dest.writeString(this.bizAddr);
        dest.writeString(this.bizCat);
        dest.writeString(this.industryCat);
        dest.writeString(this.contactPhone);
        dest.writeString(this.bizLicenseID);
        dest.writeString(this.bizLicEndDate);
        dest.writeString(this.taxRegID);
        dest.writeString(this.orgID);
        dest.writeString(this.capital);
        dest.writeString(this.corporate);
        dest.writeString(this.corporateID);
        dest.writeString(this.corpIDEndDate);
        dest.writeString(this.type);
        dest.writeString(this.masterID);
        dest.writeString(this.masterName);
        dest.writeString(this.adminName);
        dest.writeString(this.adminID);
        dest.writeString(this.attr);
        dest.writeString(this.attrName);
        dest.writeString(this.usrOprMbl);
        dest.writeString(this.state);
        dest.writeString(this.clrMerc);
        dest.writeString(this.busLtcImg);
        dest.writeString(this.doorHeadImg);
        dest.writeString(this.accManagerPhone);
    }

    protected Basic(Parcel in) {
        this.name = in.readString();
        this.shortName = in.readString();
        this.mcc = in.readString();
        this.mccName = in.readString();
        this.provCode = in.readString();
        this.provName = in.readString();
        this.cityCode = in.readString();
        this.cityName = in.readString();
        this.bizAddr = in.readString();
        this.bizCat = in.readString();
        this.industryCat = in.readString();
        this.contactPhone = in.readString();
        this.bizLicenseID = in.readString();
        this.bizLicEndDate = in.readString();
        this.taxRegID = in.readString();
        this.orgID = in.readString();
        this.capital = in.readString();
        this.corporate = in.readString();
        this.corporateID = in.readString();
        this.corpIDEndDate = in.readString();
        this.type = in.readString();
        this.masterID = in.readString();
        this.masterName = in.readString();
        this.adminName = in.readString();
        this.adminID = in.readString();
        this.attr = in.readString();
        this.attrName = in.readString();
        this.usrOprMbl = in.readString();
        this.state = in.readString();
        this.clrMerc = in.readString();
        this.busLtcImg = in.readString();
        this.doorHeadImg = in.readString();
        this.accManagerPhone = in.readString();
    }

    public static final Creator<Basic> CREATOR = new Creator<Basic>() {
        @Override
        public Basic createFromParcel(Parcel source) {
            return new Basic(source);
        }

        @Override
        public Basic[] newArray(int size) {
            return new Basic[size];
        }
    };
}
