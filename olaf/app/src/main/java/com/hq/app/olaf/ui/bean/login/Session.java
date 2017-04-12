package com.hq.app.olaf.ui.bean.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.hq.app.olaf.ui.base.HqPayApplication;
import com.hq.app.olaf.util.SharedPrefUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jyl on 17-01-20.
 */
public class Session implements Parcelable {
    private final static String TAG = "LOGIN_USER_SETTLE";
    private  String activeMch;
    private  String appId;
    private  String deviceName;
    private  String osPlatform;

    private String mobile;//账号ID
    private String authToken;//访问令牌
    private String createTime;//登录时间
    private String expiryTime;//访问令牌失效时间
    private  String userId;
    private  String userName;

    private  String ownerCoreId;

    private  List<MerchantInfo> mchs;

    private  Role role;

    /**
     * 保存到SharePreference
     */
    public void save() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        prefUtil.save(this);

        if (mchs != null && mchs.size() > 0) {
            for (MerchantInfo merchantInfo : mchs) {
                merchantInfo.save();
            }
        }
        if (role != null) {
            role.save();
        }
    }

    /**
     * 取出保存的数据
     *
     * @return
     */
    public static Session load() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        Session session = prefUtil.loadObj(Session.class);
        if (session != null) {
            List<MerchantInfo> merchantInfos = new ArrayList<>();
            MerchantInfo merchantInfo = MerchantInfo.load();
            if (merchantInfo != null) {
                merchantInfos.add(merchantInfo);
            }
            session.setMchs(merchantInfos);
            session.setRole(Role.load());
        }
        return session;
    }

    /**
     *
     */
    public static void clear() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        prefUtil.clearObj(Session.class);
        MerchantInfo.clear();
        Role.clear();
        HqPayApplication.getAppContext().clearCache();
    }

    public Session() {
    }


    /**
     * 判断SESSION是否超时
     *
     * @return
     */
    @Deprecated
    public static boolean isSessionTimeout2() {
        Session session = Session.load();
        if (session != null) {
            long now = new Date().getTime();
            long time = 0;// session.getExpiryTime() - now;
            Logger.d("剩余有效时间==>%s分钟,是否超时:%s", time / 1000 / 60 / 60, time < 5 * 1000 * 60 ? "超时" : "未超时");
            if (time < 5 * 1000 * 60) {
                //剩余有效时间小于5分钟,则自动超时要求重新登录
                Session.clear();
                return true;//超时
            } else {
                return false;//未超时
            }
        } else {
            return true;//超时
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.activeMch);
        dest.writeString(this.appId);
        dest.writeString(this.deviceName);
        dest.writeString(this.osPlatform);
        dest.writeString(this.mobile);
        dest.writeString(this.authToken);
        dest.writeString(this.createTime);
        dest.writeString(this.expiryTime);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.ownerCoreId);
        dest.writeParcelable(this.role, flags);
        dest.writeTypedList(this.mchs);
    }

    protected Session(Parcel in) {
        this.activeMch = in.readString();
        this.appId = in.readString();
        this.deviceName = in.readString();
        this.osPlatform = in.readString();
        this.mobile = in.readString();
        this.authToken = in.readString();
        this.createTime = in.readString();
        this.expiryTime = in.readString();
        this.userId = in.readString();
        this.userName = in.readString();
        this.ownerCoreId = in.readString();
        this.role = in.readParcelable(Role.class.getClassLoader());
        this.mchs = in.createTypedArrayList(MerchantInfo.CREATOR);
    }

    public static final Creator<Session> CREATOR = new Creator<Session>() {
        @Override
        public Session createFromParcel(Parcel source) {
            return new Session(source);
        }

        @Override
        public Session[] newArray(int size) {
            return new Session[size];
        }
    };


    public String getActiveMch() {
        return activeMch;
    }

    public void setActiveMch(String activeMch) {
        this.activeMch = activeMch;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public static Creator<Session> getCREATOR() {
        return CREATOR;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOsPlatform() {
        return osPlatform;
    }

    public void setOsPlatform(String osPlatform) {
        this.osPlatform = osPlatform;
    }

    public static String getTAG() {
        return TAG;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRole() {
        if(role==null){
            role=new Role();
        }
        return role;
    }

    public Session setRole(Role role) {
        this.role = role;
        return this;
    }

    public String getOwnerCoreId() {
        return ownerCoreId;
    }

    public void setOwnerCoreId(String ownerCoreId) {
        this.ownerCoreId = ownerCoreId;
    }

    public List<MerchantInfo> getMchs() {
        return mchs;
    }

    public void setMchs(List<MerchantInfo> mchs) {
        this.mchs = mchs;
    }
}
