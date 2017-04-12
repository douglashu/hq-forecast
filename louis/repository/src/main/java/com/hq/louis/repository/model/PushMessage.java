package com.hq.louis.repository.model;

import com.hq.louis.model.req.Message;
import com.hq.louis.model.constant.PushState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by zhaoyang on 21/12/2016.
 */
@Document(collection = "PushMessage")
public class PushMessage {

    private Message message;

    @Id
    private String id;
    private String appId;
    private String taskId;
    private String platform;
    private PushState state;
    private Integer failTimes;
    private Long expireTime;
    private Long lastPendingTime;
    private Long nextScheduleTime;
    private Long deliveredTime;
    private Long createTime;

    private String errorCode;
    private String errorMsg;
    // 第三方推送平台消息ID
    private String thirdMsgId;
    // 第三方推送平台任务ID
    private String thirdTaskId;

    // 服务器节点ID
    private String nodeId;
    private String resendBatchNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public PushState getState() {
        return state;
    }

    public void setState(PushState state) {
        this.state = state;
    }

    public Integer getFailTimes() {
        return failTimes;
    }

    public void setFailTimes(Integer failTimes) {
        this.failTimes = failTimes;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Long getLastPendingTime() {
        return lastPendingTime;
    }

    public void setLastPendingTime(Long lastPendingTime) {
        this.lastPendingTime = lastPendingTime;
    }

    public Long getNextScheduleTime() {
        return nextScheduleTime;
    }

    public void setNextScheduleTime(Long nextScheduleTime) {
        this.nextScheduleTime = nextScheduleTime;
    }

    public Long getDeliveredTime() {
        return deliveredTime;
    }

    public void setDeliveredTime(Long deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getThirdMsgId() {
        return thirdMsgId;
    }

    public void setThirdMsgId(String thirdMsgId) {
        this.thirdMsgId = thirdMsgId;
    }

    public String getThirdTaskId() {
        return thirdTaskId;
    }

    public void setThirdTaskId(String thirdTaskId) {
        this.thirdTaskId = thirdTaskId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getResendBatchNo() {
        return resendBatchNo;
    }

    public void setResendBatchNo(String resendBatchNo) {
        this.resendBatchNo = resendBatchNo;
    }
}
