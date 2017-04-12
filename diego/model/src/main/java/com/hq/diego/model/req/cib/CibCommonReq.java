package com.hq.diego.model.req.cib;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 02/03/2017.
 */
@XStreamAlias(value = "xml")
public class CibCommonReq {

    // 接口类型, 如 pay.weixin.jspay
    private String service;
    // 版本号, 非必填, 默认为 2.0
    private String version;
    // 字符集, 非必填, 默认为 UTF-8
    private String charset;
    // 签名类型, 非必填, 默认为 MD5
    private String sign_type;
    // 商户号, 由威富通分配
    private String mch_id;
    // 大商户编号, 如果不为空，则用大商户密钥进行签名
    private String groupno;
    // 随机字符串
    private String nonce_str;
    // 签名
    private String sign;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
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

    public String getGroupno() {
        return groupno;
    }

    public void setGroupno(String groupno) {
        this.groupno = groupno;
    }
}
