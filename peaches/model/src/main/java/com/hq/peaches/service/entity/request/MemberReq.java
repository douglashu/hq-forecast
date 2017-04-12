package com.hq.peaches.service.entity.request;

import java.io.Serializable;

/**
 * @包名称：com.hq.peaches.service.request
 * @创建人：yyx
 * @创建时间：16/12/1 下午9:55
 */
public class MemberReq implements Serializable{

    private static final long serialVersionUID = 2815096185960862856L;
    private String id;

    private String name;

    private String sex;

    private String phone;

    private String cardNo;

    private String mmsgOpenid;

    private String aliOpenid;

    private String pmmsgOpenid;

    private String paliOpenid;

    private String black;

    private String status;

    private String bind;

    private String mchId;

    private Integer visit_num;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMmsgOpenid() {
        return mmsgOpenid;
    }

    public void setMmsgOpenid(String mmsgOpenid) {
        this.mmsgOpenid = mmsgOpenid;
    }

    public String getAliOpenid() {
        return aliOpenid;
    }

    public void setAliOpenid(String aliOpenid) {
        this.aliOpenid = aliOpenid;
    }

    public String getPmmsgOpenid() {
        return pmmsgOpenid;
    }

    public void setPmmsgOpenid(String pmmsgOpenid) {
        this.pmmsgOpenid = pmmsgOpenid;
    }

    public String getPaliOpenid() {
        return paliOpenid;
    }

    public void setPaliOpenid(String paliOpenid) {
        this.paliOpenid = paliOpenid;
    }

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBind() {
        return bind;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public Integer getVisit_num() {
        return visit_num;
    }

    public void setVisit_num(Integer visit_num) {
        this.visit_num = visit_num;
    }
}
