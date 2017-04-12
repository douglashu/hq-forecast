package com.hq.app.olaf.ui.bean.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.hq.app.olaf.ui.base.HqPayApplication;
import com.hq.app.olaf.util.SharedPrefUtil;

/**
 * Created by Administrator on 2017/1/21.
 */

public class Role  implements Parcelable{
    private final static String TAG = "LOGIN_USER_ROLE";

    private  String  roleId;
    private  String roleName;


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
    public static Role load() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        return prefUtil.loadObj(Role.class);
    }

    /**
     *
     *
     * @return
     */
    public static void clear() {
        SharedPrefUtil prefUtil = SharedPrefUtil.getInstance().init(HqPayApplication.getAppContext(), TAG);
        prefUtil.clearObj(Role.class);
    }

    public Role() {
    }



    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.roleId);
        dest.writeString(this.roleName);
    }

    protected Role(Parcel in) {
        this.roleId = in.readString();
        this.roleName = in.readString();
    }

    public static final Creator<Role> CREATOR = new Creator<Role>() {
        @Override
        public Role createFromParcel(Parcel source) {
            return new Role(source);
        }

        @Override
        public Role[] newArray(int size) {
            return new Role[size];
        }
    };
}
