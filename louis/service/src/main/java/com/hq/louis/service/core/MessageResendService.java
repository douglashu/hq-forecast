package com.hq.louis.service.core;

import com.hq.louis.model.platform.AppPushInfo;
import com.hq.louis.repository.model.PushMessage;
import com.hq.louis.repository.mongo.PushMessageRepository;
import com.hq.roger.core.handler.IJobHandler;
import com.hq.roger.core.handler.annotation.JobHander;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyang on 21/12/2016.
 */
@JobHander(value="messageResendJobHandler")
@Service
public class MessageResendService extends IJobHandler {

    private static final Logger logger = Logger.getLogger(MessageResendService.class);

    private static final Integer RESEND_MESSAGE_MAX_COUNT_ONCE = 50;

    @Autowired
    private PushMessageRepository repository;

    @Autowired
    private AppPushInfoService appPushInfoService;

    @Autowired
    private MessageTransferStation station;

    public void schedule() {
        try {
            scheduleInternal();
        } catch (Throwable th) {
            logger.warn("<<<<<< Schedule Push Message Resend Task Fail", th);
        }
    }

    private void scheduleInternal() {
        logger.info("<<<<<< Message Resend Work Thread Started");
        // Close expired messages
        this.repository.closeExpiredMessages();
        //
        List<PushMessage> entities = this.repository
                .findAndPendingNextScheduledMessages(RESEND_MESSAGE_MAX_COUNT_ONCE);
        logger.info("<<<<<< Total " + entities.size() + " messages need to scheduling");
        //
        while (entities != null && entities.size() > 0) {
            Map<String, List<PushMessage>> group = new HashMap<>();
            for (PushMessage entity : entities) {
                if (!group.containsKey(entity.getAppId())) {
                    group.put(entity.getAppId(), new ArrayList<>());
                }
                group.get(entity.getAppId()).add(entity);
            }
            if (group.size() > 0) {
                for (String key : group.keySet()) {
                    List<PushMessage> groupedPushMessages = group.get(key);
                    AppPushInfo pushInfo = this.appPushInfoService.getAppPushInfo(key);
                    if(pushInfo == null) continue;
                    this.station.reQueueMsgPushTask(groupedPushMessages, pushInfo);
                }
            }
            entities = this.repository.findAndPendingNextScheduledMessages(RESEND_MESSAGE_MAX_COUNT_ONCE);
            logger.info("<<<<<< Next Total " + entities.size() + " messages need to resend");
        }
    }

    @Override
    public void execute(String... params) throws Exception {
        schedule();
    }
}
