package com.hq.louis.repository.mongo.custom;

import com.hq.louis.model.constant.PushState;
import com.hq.louis.repository.model.PushMessage;
import com.hq.scrati.cache.lock.RedisDistributedLock;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by zhaoyang on 07/02/2017.
 */
public class PushMessageRepositoryCustomImpl implements PushMessageRepositoryCustom {

    private static final Logger logger = Logger.getLogger(PushMessageRepositoryCustomImpl.class);
    private static final Integer RESEND_LOCK_MAX_SECONDS = 5 * 60;

    private static final String MESSAGE_SCHEDULE_LOCK_KEY = "MESSAGE_SCHEDULE";
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void markMessagesAsSuccessfully(String msgId, String thirdMsgId, String thirdTaskId) {
        DateTime now = DateTime.now();
        Query query = Query.query(Criteria.where("id").is(msgId));
        Update update = new Update();
        update.set("state", PushState.Success);
        update.set("deliveredTime", now.getMillis());
        if(!StringUtils.isEmpty(thirdMsgId)) {
            update.set("thirdMsgId", thirdMsgId);
        }
        if(!StringUtils.isEmpty(thirdTaskId)) {
            update.set("thirdTaskId", thirdTaskId);
        }
        this.mongoTemplate.updateFirst(query, update, PushMessage.class);
    }

    @Override
    public void closeExpiredMessages() {
        DateTime now = DateTime.now();
        Query query = Query.query(Criteria.where("state")
                .is(PushState.Fail).and("expireTime").lte(now.getMillis()));
        Update update = new Update();
        update.set("state", PushState.EXPIRED);
        this.mongoTemplate.updateMulti(query, update, PushMessage.class);
    }

    @Override
    public void markMessagesAsFail(String msgId, String errorCode, String errorMsg) {
        Query query = Query.query(Criteria.where("id").is(msgId));
        PushMessage entity = this.mongoTemplate.findOne(query, PushMessage.class);
        if(entity == null) {
            logger.warn("<<<<<< Push Message(" + msgId + ") Not Found");
            return;
        }
        if(PushState.Success == entity.getState()) {
            logger.warn("<<<<<< Push Message(" + msgId + ") Repeated");
            return;
        }
        Update update = new Update();
        PushState state;
        if(entity.getFailTimes() >= entity.getMessage().getMaxRetryTimes()) {
            state = PushState.Abandon;
        } else {
            state = PushState.Fail;
            Integer afterMinutes = getNextScheduleAfterMinutes(entity.getFailTimes());
            if(afterMinutes != null) {
                update.set("nextScheduleTime", DateTime.now().plusMinutes(afterMinutes).getMillis());
            }
        }
        update.set("state", state);
        update.set("failTimes", (entity.getFailTimes() + 1));
        if(!StringUtils.isEmpty(errorCode)) {
            update.set("errorCode", errorCode);
        }
        if(!StringUtils.isEmpty(errorMsg)) {
            update.set("errorMsg", errorMsg);
        }
        this.mongoTemplate.updateFirst(query, update, PushMessage.class);
    }

    @Override
    public List<PushMessage> findAndPendingNextScheduledMessages(Integer limit) {
        RedisDistributedLock lock =
                new RedisDistributedLock(this.redisTemplate, MESSAGE_SCHEDULE_LOCK_KEY);
        if (lock.tryLock(RESEND_LOCK_MAX_SECONDS)) {
            try {
                DateTime now = DateTime.now();
                String resendBatchNo = String.valueOf(System.nanoTime());
                Query query = Query.query(Criteria
                        .where("state").is(PushState.Fail)
                        .and("expireTime").gt(now.getMillis())
                        .and("nextScheduleTime").lte(now.getMillis()))
                        .with(new Sort(new Sort.Order(Sort.Direction.ASC, "lastPendingTime")
                                , new Sort.Order(Sort.Direction.ASC, "failTimes")))
                        .limit(limit);
                Update update = new Update();
                update.set("state", PushState.Pending);
                update.set("lastPendingTime", now.getMillis());
                update.set("resendBatchNo", resendBatchNo);
                this.mongoTemplate.updateMulti(query, update, PushMessage.class);
                query = Query.query(Criteria.where("resendBatchNo").is(resendBatchNo));
                return this.mongoTemplate.find(query, PushMessage.class);
            } catch (Throwable th) {
                logger.warn("<<<<<< Schedule Push Message Resend Task Fail", th);
            } finally {
                lock.unlock();
            }
        }
        return null;
    }

    private int[] SCHEDULE_MINUTE_DURATIONS = new int[] {
            1, 2, 5, 10, 15, 30, 60, 120, 360, 720
    };

    private Integer getNextScheduleAfterMinutes(Integer failTimes) {
        if (failTimes == null || failTimes < 0 ||
                failTimes >= SCHEDULE_MINUTE_DURATIONS.length) return null;
        return SCHEDULE_MINUTE_DURATIONS[failTimes];
    }

}
