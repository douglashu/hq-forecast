package com.hq.louis.service.core;

import com.hq.louis.model.req.Message;
import com.hq.louis.model.PushResult;
import com.hq.louis.model.platform.AppPushInfo;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public interface PushService {
    PushResult push(Message message, AppPushInfo appPushInfo);
}
