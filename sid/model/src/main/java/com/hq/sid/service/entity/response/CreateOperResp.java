package com.hq.sid.service.entity.response;

/**
 * Created by Zale on 2016/12/12.
 */
public class CreateOperResp {
    private int operId;

    public int getOperId() {

        return operId;
    }

    @Override
    public String toString() {
        return "CreateOperResp{" +
                "operId='" + operId + '\'' +
                '}';
    }

    public void setOperId(int operId) {

        this.operId = operId;
    }
}
