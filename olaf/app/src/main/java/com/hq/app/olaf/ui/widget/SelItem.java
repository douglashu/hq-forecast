package com.hq.app.olaf.ui.widget;


import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by huwentao on 16-4-28.
 */
public class SelItem implements Serializable{

    private String key;
    private String value;
    private JSONObject jsonObject;

    public SelItem() {
    }

    public SelItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public String toString() {
        return "SelItem{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", jsonObject=" + jsonObject +
                '}';
    }
}
