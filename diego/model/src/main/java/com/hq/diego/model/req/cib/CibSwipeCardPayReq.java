package com.hq.diego.model.req.cib;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 02/03/2017.
 */
@XStreamAlias(value = "xml")
public class CibSwipeCardPayReq extends CibCommonReq {

    // 是否原生态, 非必填, 可选 [0,1], 默认为 0
    private String is_raw;
    // 商户订单号
    private String out_trade_no;
    // 设备号, 非必填
    private String device_info;
    // 商品描述
    private String body;
    // 微信用户openid
    private String sub_openid;
    // 附加信息, 非必填
    private String attach;
    // 总金额，以分为单位
    private Integer total_fee;
    // 订单生成的机器 IP
    private String mch_create_ip;
    // 扫码支付授权码， 设备读取用户展示的条码或者二维码信息
    private String auth_code;
    // 订单生成时间，非必填, 格式为yyyymmddhhmmss
    private String time_start;
    // 订单失效时间，非必填, 格式为yyyymmddhhmmss
    private String time_expire;
    // 操作员, 非必填
    private String op_user_id;
    // 门店编号, 非必填
    private String op_shop_id;
    // 设备编号, 非必填
    private String op_device_id;
    // 商品标记, 非必填
    private String goods_tag;

    public String getIs_raw() {
        return is_raw;
    }

    public void setIs_raw(String is_raw) {
        this.is_raw = is_raw;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSub_openid() {
        return sub_openid;
    }

    public void setSub_openid(String sub_openid) {
        this.sub_openid = sub_openid;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public String getMch_create_ip() {
        return mch_create_ip;
    }

    public void setMch_create_ip(String mch_create_ip) {
        this.mch_create_ip = mch_create_ip;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getOp_user_id() {
        return op_user_id;
    }

    public void setOp_user_id(String op_user_id) {
        this.op_user_id = op_user_id;
    }

    public String getOp_shop_id() {
        return op_shop_id;
    }

    public void setOp_shop_id(String op_shop_id) {
        this.op_shop_id = op_shop_id;
    }

    public String getOp_device_id() {
        return op_device_id;
    }

    public void setOp_device_id(String op_device_id) {
        this.op_device_id = op_device_id;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }
}
