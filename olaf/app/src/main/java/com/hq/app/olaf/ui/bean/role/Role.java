package com.hq.app.olaf.ui.bean.role;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jyl on 2017/2/19.
 */

public class Role implements Parcelable {

    private  String roleId;
    private  String introduce;
    private  String roleName;
    private  int roleType; // 1 老板 2  店长 3 店员
    private  boolean checked;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.roleId);
        dest.writeString(this.introduce);
        dest.writeString(this.roleName);
        dest.writeInt(this.roleType);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    public Role() {
    }

    protected Role(Parcel in) {
        this.roleId = in.readString();
        this.introduce = in.readString();
        this.roleName = in.readString();
        this.roleType = in.readInt();
        this.checked = in.readByte() != 0;
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
