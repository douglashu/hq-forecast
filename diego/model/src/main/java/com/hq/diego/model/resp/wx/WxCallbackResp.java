package com.hq.diego.model.resp.wx;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 31/01/2017.
 */
@XStreamAlias(value = "xml")
public class WxCallbackResp {

    private String return_code;
    private String return_msg;

    public WxCallbackResp() {
    }

    public WxCallbackResp(String return_code, String return_msg) {
        this.return_code = return_code;
        this.return_msg = return_msg;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

}
