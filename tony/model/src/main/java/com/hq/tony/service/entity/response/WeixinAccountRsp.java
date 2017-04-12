package com.hq.tony.service.entity.response;

import java.io.Serializable;
import java.util.Date;

/**
 * @包名称：com.hq.tony.service.entity.response
 * @创建人：yyx
 * @创建时间：17/3/4 下午2:55
 */
public class WeixinAccountRsp implements Serializable {

    private String id;

    private String accountname;

    private String accounttoken;

    private String accountnumber;

    private String accounttype;

    private String accountemail;

    private String accountdesc;

    private String accountaccesstoken;

    private String accountappid;

    private String accountappsecret;

    private Date addtoekntime;

    private String username;

    private String weixinAccountid;

    private String apiticket;

    private Date apiticketttime;

    private String jsapiticket;

    private Date jsapitickettime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getAccounttoken() {
        return accounttoken;
    }

    public void setAccounttoken(String accounttoken) {
        this.accounttoken = accounttoken;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getAccountemail() {
        return accountemail;
    }

    public void setAccountemail(String accountemail) {
        this.accountemail = accountemail;
    }

    public String getAccountdesc() {
        return accountdesc;
    }

    public void setAccountdesc(String accountdesc) {
        this.accountdesc = accountdesc;
    }

    public String getAccountaccesstoken() {
        return accountaccesstoken;
    }

    public void setAccountaccesstoken(String accountaccesstoken) {
        this.accountaccesstoken = accountaccesstoken;
    }

    public String getAccountappid() {
        return accountappid;
    }

    public void setAccountappid(String accountappid) {
        this.accountappid = accountappid;
    }

    public String getAccountappsecret() {
        return accountappsecret;
    }

    public void setAccountappsecret(String accountappsecret) {
        this.accountappsecret = accountappsecret;
    }

    public Date getAddtoekntime() {
        return addtoekntime;
    }

    public void setAddtoekntime(Date addtoekntime) {
        this.addtoekntime = addtoekntime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWeixinAccountid() {
        return weixinAccountid;
    }

    public void setWeixinAccountid(String weixinAccountid) {
        this.weixinAccountid = weixinAccountid;
    }

    public String getApiticket() {
        return apiticket;
    }

    public void setApiticket(String apiticket) {
        this.apiticket = apiticket;
    }

    public Date getApiticketttime() {
        return apiticketttime;
    }

    public void setApiticketttime(Date apiticketttime) {
        this.apiticketttime = apiticketttime;
    }

    public String getJsapiticket() {
        return jsapiticket;
    }

    public void setJsapiticket(String jsapiticket) {
        this.jsapiticket = jsapiticket;
    }

    public Date getJsapitickettime() {
        return jsapitickettime;
    }

    public void setJsapitickettime(Date jsapitickettime) {
        this.jsapitickettime = jsapitickettime;
    }
}
