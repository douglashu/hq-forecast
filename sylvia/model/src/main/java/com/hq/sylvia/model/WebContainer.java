package com.hq.sylvia.model;

/**
 * Created by zhaoyang on 16/01/2017.
 */
public class WebContainer {

    private boolean wechat;
    private boolean alipay;
    private String container;

    public boolean isWechat() {
        return wechat;
    }

    public void setWechat(boolean wechat) {
        this.wechat = wechat;
    }

    public boolean isAlipay() {
        return alipay;
    }

    public void setAlipay(boolean alipay) {
        this.alipay = alipay;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

}
