package com.hq.diego.model.resp.wx;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 25/11/2016.
 */
@XStreamAlias(value = "xml")
public class WxUnifiedOrderResp extends WxCommonResp {

    // 预支付交易会话标识
    private String prepay_id;
    // trade_type为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付
    private String code_url;
    private String trade_type;

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }
}
