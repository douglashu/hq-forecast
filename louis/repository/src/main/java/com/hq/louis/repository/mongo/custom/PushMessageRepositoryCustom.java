package com.hq.louis.repository.mongo.custom;

import com.hq.louis.repository.model.PushMessage;
import java.util.List;

/**
 * Created by zhaoyang on 07/02/2017.
 */
public interface PushMessageRepositoryCustom {

    void markMessagesAsSuccessfully(String msgId, String thirdMsgId, String thirdTaskId);

    void markMessagesAsFail(String msgId, String errorCode, String errorMsg);

    void closeExpiredMessages();

    List<PushMessage> findAndPendingNextScheduledMessages(Integer limit);

}
