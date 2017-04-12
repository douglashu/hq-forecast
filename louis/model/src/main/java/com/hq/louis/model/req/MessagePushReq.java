package com.hq.louis.model.req;

import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 07/02/2017.
 */
public class MessagePushReq extends HqRequest {

    private PushMessage message;

    public PushMessage getMessage() {
        return message;
    }

    public void setMessage(PushMessage message) {
        this.message = message;
    }
}
