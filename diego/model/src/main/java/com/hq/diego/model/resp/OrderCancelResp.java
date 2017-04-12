package com.hq.diego.model.resp;

/**
 * Created by zhaoyang on 07/12/2016.
 */
public class OrderCancelResp extends OrderCommonResp {

    private String id;
    private String result;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
