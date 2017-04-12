package com.hq.diego.gateway.service.redpacket;

import com.hq.scrati.common.util.UrlParamUtil;
import com.hq.scrati.common.util.signature.MD5Util;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by young on 1/27/16.
 */
public class SendRedPacketReq {

    // 随机字符串, 可自己生成, 不能大于32位
    private String nonce_str;
    // 微信分配的商户号
    private String mch_id;
    // 商户订单号 (mch_id + yyyymmdd + 10位一天内不能重复的数字)
    private String mch_billno;
    // 微信公众号appId
    private String wxappid;
    // 商户名称 (红包发送者名称)
    private String send_name;
    // 接受红包的用户在wxappId下的openId
    private String re_openid;
    // 付款金额 (单位分)
    private int total_amount;
    // 红包发放总人数
    private int total_num;
    // 红包祝福语 (最多128字)
    private String wishing;
    // 调用接口的机器Ip地址
    private String client_ip;
    // 活动名称 (最多32字)
    private String act_name;
    // 备注 (256字)
    private String remark;
    // 签名, 请调用签名算法生成(sign参数本身不参与签名)
    private String sign;

    public SendRedPacketReq() {
        this.nonce_str = UUID.randomUUID().toString().replace("-", "");
    }

    public void genSendSign(String key) {
        Map<String, Object> params = new HashMap<>();
        params.put("nonce_str", getNonce_str());
        params.put("mch_id", getMch_id());
        params.put("mch_billno", getMch_billno());
        params.put("wxappid", getWxappid());
        params.put("send_name", getSend_name());
        params.put("re_openid", getRe_openid());
        params.put("total_amount", getTotal_amount());
        params.put("total_num", getTotal_num());
        params.put("wishing", getWishing());
        params.put("client_ip", getClient_ip());
        params.put("act_name", getAct_name());
        params.put("remark", getRemark());
        setSign(getSign(params, key));
    }

    public void genQuerySign(String key) {
        Map<String, Object> params = new HashMap<>();
        params.put("nonce_str", getNonce_str());
        params.put("mch_id", getMch_id());
        params.put("mch_billno", getMch_billno());
        params.put("appid", getWxappid());
        params.put("bill_type", "MCHT");
        setSign(getSign(params, key));
    }

    private String getSign(Map<String, Object> params, String key) {
        if(params == null || params.isEmpty()) {
            throw new RuntimeException("Sign params can not be null.");
        }
        if(key == null || "".equals(key.trim())) {
            throw new RuntimeException("Sign key can not be null");
        }
        String signStr = UrlParamUtil.createLinkString(params);
        signStr += ("&key=" + key);
        return MD5Util.MD5Encode(signStr, "UTF-8");
    }

    public String toSendXmlString() {
        if(getSign() == null || getSign().trim().length() != 32) {
            throw new RuntimeException("请先生成签名.");
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<xml>");
        builder.append("<sign><![CDATA[" + getSign() + "]]></sign>");
        builder.append("<mch_billno><![CDATA[" + getMch_billno() + "]]></mch_billno>");
        builder.append("<mch_id><![CDATA[" + getMch_id() + "]]></mch_id>");
        builder.append("<wxappid><![CDATA[" + getWxappid() + "]]></wxappid>");
        builder.append("<send_name><![CDATA[" + getSend_name() + "]]></send_name>");
        builder.append("<re_openid><![CDATA[" + getRe_openid() + "]]></re_openid>");
        builder.append("<total_amount><![CDATA[" + getTotal_amount() + "]]></total_amount>");
        builder.append("<total_num><![CDATA[" + getTotal_num() + "]]></total_num>");
        builder.append("<wishing><![CDATA[" + getWishing() + "]]></wishing>");
        builder.append("<client_ip><![CDATA[" + getClient_ip() + "]]></client_ip>");
        builder.append("<act_name><![CDATA[" + getAct_name() + "]]></act_name>");
        builder.append("<remark><![CDATA[" + getRemark() + "]]></remark>");
        builder.append("<nonce_str><![CDATA[" + getNonce_str() + "]]></nonce_str>");
        builder.append("</xml>");
        return builder.toString();
    }

    public String toQueryXmlString() {
        if(getSign() == null || getSign().trim().length() != 32) {
            throw new RuntimeException("请先生成签名.");
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<xml>");
        builder.append("<sign><![CDATA[" + getSign() + "]]></sign>");
        builder.append("<mch_billno><![CDATA[" + getMch_billno() + "]]></mch_billno>");
        builder.append("<mch_id><![CDATA[" + getMch_id() + "]]></mch_id>");
        builder.append("<appid><![CDATA[" + getWxappid() + "]]></appid>");
        builder.append("<bill_type><![CDATA[MCHT]]></bill_type>");
        builder.append("<nonce_str><![CDATA[" + getNonce_str() + "]]></nonce_str>");
        builder.append("</xml>");
        return builder.toString();
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

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getMch_billno() {
        return mch_billno;
    }

    public void setMch_billno(String mch_billno) {
        this.mch_billno = mch_billno;
    }

    public String getWxappid() {
        return wxappid;
    }

    public void setWxappid(String wxappid) {
        this.wxappid = wxappid;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getRe_openid() {
        return re_openid;
    }

    public void setRe_openid(String re_openid) {
        this.re_openid = re_openid;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getAct_name() {
        return act_name;
    }

    public void setAct_name(String act_name) {
        this.act_name = act_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
