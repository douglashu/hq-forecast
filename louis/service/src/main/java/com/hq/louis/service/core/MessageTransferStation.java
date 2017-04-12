package com.hq.louis.service.core;

import com.hq.louis.model.req.Message;
import com.hq.louis.model.constant.PushConstant;
import com.hq.louis.model.constant.PushState;
import com.hq.louis.model.platform.AppPushInfo;
import com.hq.louis.repository.model.PushMessage;
import com.hq.louis.repository.mongo.PushMessageRepository;
import com.hq.scrati.common.constants.OSPlatform;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhaoyang on 21/12/2016.
 */
@Service
public class MessageTransferStation {

    private static final Logger logger = Logger.getLogger(MessageTransferStation.class);

    public static final Integer MAX_MSG_SIZE = 50;

    public static final Integer DEFAULT_MSG_EXPIRY_IN_MINUTES = 30;

    public static final Integer DEFAULT_MAX_RETRY_TIMES = 3;

    private ExecutorService executorService;

    @Value("${node.id}")
    private String nodeId;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PushMessageRepository repository;

    public MsgPushTask queueMsgPushTask(List<Message> messages, AppPushInfo appPushInfo) {
        if (CollectionUtils.isEmpty(messages)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "消息列表不能为空[messages]");
        }
        if (messages.size() > MAX_MSG_SIZE) {
            throw new CommonException(CommonErrCode.BUSINESS
                    , ("消息超过最大条数限制[current=" + messages.size() + ", max=" + MAX_MSG_SIZE + "]"));
        }

        // Find the push service
        Object obj = this.applicationContext.getBean(appPushInfo.getPlatform() + "PushService");
        if(obj == null || !(obj instanceof PushService)) {
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR
                    , ("推送服务不可用[" + appPushInfo.getPlatform() + "PushService]"));
        }
        PushService pushService = (PushService) obj;

        // Persist push messages
        String taskId = UUID.randomUUID().toString().replaceAll("-", "");
        DateTime timestamp = DateTime.now();
        List<PushMessage> pushMessages = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            filter(messages.get(i), timestamp);
            PushMessage entity = new PushMessage();
            entity.setAppId(appPushInfo.getAppId());
            entity.setTaskId(taskId);
            entity.setPlatform(appPushInfo.getPlatform());
            entity.setFailTimes(0);
            entity.setState(PushState.Pending);
            entity.setMessage(messages.get(i));
            entity.setCreateTime(timestamp.getMillis());
            entity.setLastPendingTime(timestamp.getMillis());
            entity.setExpireTime(messages.get(i).getExpiryTime());
            entity.setNodeId(this.nodeId);
            pushMessages.add(entity);
        }
        try {
            this.repository.save(pushMessages);
        } catch (Throwable th) {
            logger.warn("<<<<<< Save Push Messages To Mongodb Fail", th);
            throw new CommonException(CommonErrCode.DB_ERROR, th);
        }

        // Push message to task queue
        PushContext context = new PushContext(pushMessages, appPushInfo);
        MsgPushTask task = new MsgPushTask(taskId, context, pushService, this.repository);
        this.executorService.submit(task);
        return task;
    }

    public void reQueueMsgPushTask(
            List<PushMessage> pushMessages, AppPushInfo pushInfo) {
        // Find the push service
        Object obj = this.applicationContext.getBean(pushInfo.getPlatform() + "PushService");
        if(obj == null || !(obj instanceof PushService)) {

        }
        PushService pushService = (PushService) obj;
        PushContext context = new PushContext(pushMessages, pushInfo);
        MsgPushTask task = new MsgPushTask(null, context, pushService, this.repository);
        this.executorService.submit(task);
    }

    private void filter(Message message, DateTime timestamp) {
        if(message.getTimestamp() == null) {
            message.setTimestamp(timestamp.getMillis());
        }
        if(StringUtils.isEmpty(message.getType()) || (
                !PushConstant.Type.SINGLE.equalsIgnoreCase(message.getType()) &&
                !PushConstant.Type.MULTI.equalsIgnoreCase(message.getType()))) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "推送类型错误[type]");
        }
        if(PushConstant.Type.SINGLE.equalsIgnoreCase(message.getType())
                || PushConstant.Type.MULTI.equalsIgnoreCase(message.getType())) {
            if (StringUtils.isEmpty(message.getDevices())) {
                throw new CommonException(CommonErrCode.ARGS_INVALID, "目标设备错误[devices]");
            }
        }
        OSPlatform osPlatform = OSPlatform.fromString(message.getOsPlatform());
        if(osPlatform == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "设备系统平台错误[osplatform]");
        }
        if(StringUtils.isEmpty(message.getTitle())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "消息标题不能为空[title]");
        }
        if(StringUtils.isEmpty(message.getContent())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "消息内容不能为空[content]");
        }
        if(OSPlatform.Android.equals(osPlatform)) {
            if (StringUtils.isEmpty(message.getTicker())) {
                throw new CommonException(CommonErrCode.ARGS_INVALID, "通知栏提示文字不能为空[ticker]");
            }
        }
        if(message.getSendTime() != null) {
            // 设置了定时发送时间, 必须大于当前时间
        }
        if(message.getExpiryTime() == null) {
            message.setExpiryTime(timestamp.plusMinutes(DEFAULT_MSG_EXPIRY_IN_MINUTES).getMillis());
        }
        if(message.getMaxRetryTimes() == null) {
            message.setMaxRetryTimes(DEFAULT_MAX_RETRY_TIMES);
        }
    }

    @PostConstruct
    public void init() {
        destroy();
        this.executorService = Executors.newCachedThreadPool();
    }

    @PreDestroy
    public void destroy() {
        try {
            if (this.executorService == null) return;
            if (this.executorService.isShutdown()) return;
            this.executorService.shutdownNow();
            logger.warn("<<<<<< Shutdown Msg Transfer Station Success");
        } catch (Throwable th) {
            logger.warn("<<<<<< Shutdown Msg Transfer Station Fail", th);
        }
    }
}
