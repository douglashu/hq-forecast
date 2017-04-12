package com.hq.diego.model.req.wx;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 10/5/16.
 */
@XStreamAlias(value = "xml")
public class WxCommonReq {

    /*
        应用ID: [Y] [32]

        微信开放平台审核通过的应用APPID 或 公众号ID
     */
    private String appid;


    /*
        子商户公众账号ID: [N] [32]
     */
    private String sub_appid;

    /*
        商户号: [Y] [32]

        微信支付分配的商户号
     */
    private String mch_id;

    /*
        子商户号: [N] [32]

        微信支付分配的子商户号，受理机构必须传入
     */
    private String sub_mch_id;

    /*
       设备号: [N] [32]

       终端设备号(门店号或收银设备ID)，默认请传"WEB"
    */
    private String device_info;

    /*
        随机字符串: [Y] [32]

        随机字符串，不长于32位
     */
    private String nonce_str;

    /*
        签名: [Y] [32]
     */
    private String sign;


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
    }

    public String getSub_appid() {
        return sub_appid;
    }

    public void setSub_appid(String sub_appid) {
        this.sub_appid = sub_appid;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }
}
