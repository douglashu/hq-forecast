package com.hq.diego.model.resp.tfb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 15/03/2017.
 */
@XStreamAlias(value = "data")
public class TfbQueryDataResp {
    private TfbQueryDataRecordResp record;

    public TfbQueryDataRecordResp getRecord() {
        return record;
    }

    public void setRecord(TfbQueryDataRecordResp record) {
        this.record = record;
    }
}
