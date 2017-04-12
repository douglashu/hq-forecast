package com.hq.app.olaf.ui.bean.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.hq.app.olaf.ui.base.HqPayApplication;
import com.hq.app.olaf.util.SharedPrefUtil;


/**
 * Created by huwentao on 16-5-10.
 */
public class Operators implements Parcelable {
    private final static String TAG = "LOGIN_USER_OPERATORS";

    private String auditState         ;//
    private String usrNo;//": 110000001733,
    private String operatorNo;//": "1100000004",
    private String adminFlag;//": "0",
    private String permissions;//": 0,
    private String funcList;//": "",
    private String portraitUrl;//": "http://www.baidu.com",     // 此字段不一定返回

    private String id;//商户号  ": "888022000020001",
    private String operatorName;//操作员名字
    private String operatorId;//操作员ID      ": "9550000000006",
    private String name;//商户名  ": "三月二十九",
    private String tempname;//未通过时商户名  ": "三月二十九",
    private String shortName;//商户简称      ": "SYESJ",
    private String state;//进件状态,完成基本信息[1],待审核[2],审核通过[3],审核驳回[4]  ": 3,
    private String descs;//进件状态描述  ": "审核已通过",
    private String runningNo;//流水号
    private String createTime;//创建时间
    private boolean defaults;//是否默认  ": true
    private String userRoleId;//角色ID


    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }

    public String getUsrNo() {
        return usrNo;
    }

    public void setUsrNo(String usrNo) {
        this.usrNo = usrNo;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public String getAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(String adminFlag) {
        this.adminFlag = adminFlag;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getFuncList() {
        return funcList;
    }

    public void setFuncList(String funcList) {
        this.funcList = funcList;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public boolean isDefaults() {
        return defaults;
    }

    public void setDefaults(boolean defaults) {
        this.defaults = defaults;
    }

    public String getTempname() {
        return tempname;
    }

    public void setTempname(String tempname) {
        this.tempname = tempname;
    }

    public String getRunningNo() {
        return runningNo;
    }

    public void setRunningNo(String runningNo) {
        this.runningNo = runningNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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
    public static Operators load() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        return prefUtil.loadObj(Operators.class);
    }
    /**
     *
     *
     * @return
     */
    public static void clear() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        prefUtil.clearObj(Operators.class);
    }
    public Operators() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.auditState);
        dest.writeString(this.usrNo);
        dest.writeString(this.operatorNo);
        dest.writeString(this.adminFlag);
        dest.writeString(this.permissions);
        dest.writeString(this.funcList);
        dest.writeString(this.portraitUrl);
        dest.writeString(this.id);
        dest.writeString(this.operatorName);
        dest.writeString(this.operatorId);
        dest.writeString(this.name);
        dest.writeString(this.tempname);
        dest.writeString(this.shortName);
        dest.writeString(this.state);
        dest.writeString(this.descs);
        dest.writeString(this.runningNo);
        dest.writeString(this.createTime);
        dest.writeByte(this.defaults ? (byte) 1 : (byte) 0);
    }

    protected Operators(Parcel in) {
        this.auditState = in.readString();
        this.usrNo = in.readString();
        this.operatorNo = in.readString();
        this.adminFlag = in.readString();
        this.permissions = in.readString();
        this.funcList = in.readString();
        this.portraitUrl = in.readString();
        this.id = in.readString();
        this.operatorName = in.readString();
        this.operatorId = in.readString();
        this.name = in.readString();
        this.tempname = in.readString();
        this.shortName = in.readString();
        this.state = in.readString();
        this.descs = in.readString();
        this.runningNo = in.readString();
        this.createTime = in.readString();
        this.defaults = in.readByte() != 0;
    }

    public static final Creator<Operators> CREATOR = new Creator<Operators>() {
        @Override
        public Operators createFromParcel(Parcel source) {
            return new Operators(source);
        }

        @Override
        public Operators[] newArray(int size) {
            return new Operators[size];
        }
    };
}
