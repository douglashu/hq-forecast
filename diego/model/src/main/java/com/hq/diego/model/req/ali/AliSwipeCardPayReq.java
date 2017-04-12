package com.hq.diego.model.req.ali;

/**
 * Created by zhaoyang on 26/11/2016.
 */
public class AliSwipeCardPayReq extends AliPayCommonReq {
    // 支付场景 条码支付，取值：bar_code 声波支付，取值：wave_code
    private String scene;
    // 支付授权码
    private String auth_code;

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }
}
