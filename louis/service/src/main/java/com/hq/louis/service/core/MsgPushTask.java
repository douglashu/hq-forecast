package com.hq.louis.service.core;

import com.hq.louis.model.PushResult;
import com.hq.louis.repository.model.PushMessage;
import com.hq.louis.repository.mongo.PushMessageRepository;
import org.springframework.util.CollectionUtils;
import java.util.concurrent.Callable;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public class MsgPushTask implements Callable<PushContext> {

    private String id;
    private PushContext context;
    private PushService pushExecutor;
    private PushMessageRepository repository;

    public MsgPushTask(String id, PushContext context
            , PushService pushExecutor, PushMessageRepository repository) {
        this.id = id;
        this.context = context;
        this.pushExecutor = pushExecutor;
        this.repository = repository;
    }

    @Override
    public PushContext call() throws Exception {
        if(this.context == null ||
                CollectionUtils.isEmpty(this.context.getPushMessages())) {
            return context;
        }
        for(int i=0; i<context.getPushMessages().size(); i++) {
            PushMessage entity = context.getPushMessages().get(i);
            PushResult result = this.pushExecutor.push(entity.getMessage(), context.getAppPushInfo());
            // update to mongodb
            if(result.getSuccess()) {
                this.repository.markMessagesAsSuccessfully(
                        entity.getId(), result.getMsgId(), result.getTaskId());
            } else {
                this.repository.markMessagesAsFail(
                        entity.getId(), result.getErrCode(), result.getErrMsg());
            }
        }
        return context;
    }

    public PushContext getContext() {
        return context;
    }

    public String getId() {
        return id;
    }
}
