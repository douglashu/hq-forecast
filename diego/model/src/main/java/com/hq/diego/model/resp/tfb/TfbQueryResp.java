package com.hq.diego.model.resp.tfb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 15/03/2017.
 */
@XStreamAlias(value = "root")
public class TfbQueryResp extends TfbCommonResp {

    private String spid;
    private TfbQueryDataResp data;

    public TfbQueryDataResp getData() {
        return data;
    }

    public void setData(TfbQueryDataResp data) {
        this.data = data;
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }
}
