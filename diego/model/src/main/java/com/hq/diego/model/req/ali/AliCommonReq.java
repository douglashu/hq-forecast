package com.hq.diego.model.req.ali;


/**
 * Created by zhaoyang on 26/11/2016.
 */
public class AliCommonReq {
    // 支付宝分配给开发者的应用ID
    private String app_id;
    // 商户请求参数的签名串
    private String sign;
    // 接口名称
    private String method;
    // 仅支持JSON
    private String format;
    // utf-8,gbk,gb2312
    private String charset;
    // 商户生成签名字符串所使用的签名算法类型，目前支持RSA
    private String sign_type;
    // 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
    private String timestamp;
    // 调用的接口版本，固定为：1.0
    private String version;
    // 非必填, 回调地址
    private String notify_url;
    // 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
    private String seller_id;
    // 非必填, 详见应用授权概述 [https://doc.open.alipay.com/doc2/detail.htm?treeId=216&articleId=105193&docType=1]
    private String app_auth_token;
    // 请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档
    private String biz_content;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getApp_auth_token() {
        return app_auth_token;
    }

    public void setApp_auth_token(String app_auth_token) {
        this.app_auth_token = app_auth_token;
    }

    public String getBiz_content() {
        return biz_content;
    }

    public void setBiz_content(String biz_content) {
        this.biz_content = biz_content;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }
}
