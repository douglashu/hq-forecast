package com.hq.crash.model.operator;

/**
 * Created by zhaoyang on 14/02/2017.
 */
public class Operator {
    private Integer id;
    private String name;
    private String mobile;
    private Integer roleType;
    private String refund;

    public String getRefund() {
        return refund;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

}
