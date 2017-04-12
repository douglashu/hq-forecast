package com.hq.app.olaf.ui.bean.login;


import com.hq.app.olaf.ui.bean.BaseEntity;

/**
 * Created by liaob on 2016/4/26.
 */
public class NetReturn extends BaseEntity<NetReturn> {
    private String key;//成功代码【success】
    private String msg;//

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
