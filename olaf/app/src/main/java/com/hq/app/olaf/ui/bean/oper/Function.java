package com.hq.app.olaf.ui.bean.oper;

import android.os.Parcel;
import android.os.Parcelable;

import com.hq.component.db.annotation.Column;
import com.hq.component.db.annotation.Table;

/**
 * Created by huwentao on 16/6/23.
 */
@Table(name = "tb_oper_func")
public class Function implements Parcelable {
    @Column(name = "_id", autoGen = true, isId = true) private int fId;//STRING	Y	功能编号
    @Column(name = "code") private String id;//STRING	Y	功能编号
    @Column(name = "name") private String name;//STRING	Y	功能名称
    @Column(name = "description") private String remark;//STRING	Y	功能描述
    @Column(name = "value") private String value;//LONG	Y	功能值


    private boolean isChecked = false;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public Function setChecked(boolean checked) {
        isChecked = checked;
        return null;
    }

    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Function() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.fId);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.remark);
        dest.writeString(this.value);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected Function(Parcel in) {
        this.fId = in.readInt();
        this.id = in.readString();
        this.name = in.readString();
        this.remark = in.readString();
        this.value = in.readString();
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<Function> CREATOR = new Creator<Function>() {
        @Override
        public Function createFromParcel(Parcel source) {
            return new Function(source);
        }

        @Override
        public Function[] newArray(int size) {
            return new Function[size];
        }
    };
}
