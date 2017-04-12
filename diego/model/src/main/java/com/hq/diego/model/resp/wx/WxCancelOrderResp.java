package com.hq.diego.model.resp.wx;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 12/12/2016.
 */
@XStreamAlias(value = "xml")
public class WxCancelOrderResp extends WxCommonResp {
    private String recall;

    public String getRecall() {
        return recall;
    }

    public void setRecall(String recall) {
        this.recall = recall;
    }
}
