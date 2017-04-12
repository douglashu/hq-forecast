package com.hq.louis.service.core;

import com.hq.louis.model.platform.AppPushInfo;
import com.hq.louis.repository.model.PushMessage;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public class PushContext {

    private AppPushInfo appPushInfo;
    public List<PushMessage> pushMessages;

    public PushContext(PushMessage pushMessage, AppPushInfo appPushInfo) {
        this.pushMessages = Arrays.asList(pushMessage);
        this.appPushInfo = appPushInfo;
    }

    public PushContext(List<PushMessage> pushMessages, AppPushInfo appPushInfo) {
        this.pushMessages = pushMessages;
        this.appPushInfo = appPushInfo;
    }

    public AppPushInfo getAppPushInfo() {
        return appPushInfo;
    }

    public void setAppPushInfo(AppPushInfo appPushInfo) {
        this.appPushInfo = appPushInfo;
    }

    public List<PushMessage> getPushMessages() {
        return pushMessages;
    }

    public void setPushMessages(List<PushMessage> pushMessages) {
        this.pushMessages = pushMessages;
    }

}
