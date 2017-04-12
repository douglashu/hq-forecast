package com.hq.diego.model.req.tfb;

/**
 * Created by zhaoyang on 13/03/2017.
 */
public class TfbCommonReq {

    private String sign_type;
    private String ver;
    private String input_charset;
    private String sign;
    private Integer sign_key_index;
    private String reqUrl;

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getInput_charset() {
        return input_charset;
    }

    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getSign_key_index() {
        return sign_key_index;
    }

    public void setSign_key_index(Integer sign_key_index) {
        this.sign_key_index = sign_key_index;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }
}
