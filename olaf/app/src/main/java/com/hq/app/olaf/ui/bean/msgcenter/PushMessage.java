package com.hq.app.olaf.ui.bean.msgcenter;

/**
 * Created by huwentao on 16/11/12.
 */

public class PushMessage {
    private String failures           ;// "0",
    private String state              ;// "1",
    private long sendTime           ;// 1478829601000,
    private String contentType        ;// "TEXT",
    private String type               ;// "1",
    private String messageType        ;// "Upgrade",
    private String content            ;// "您的套餐剩余时长761天，套餐内可用额度398元，建议您及时更换套餐或者提前续费。",
    private String id                 ;// "90eb80fa-cc71-4515-9330-d274f7a8f8cd",
    private long timestamp          ;// 1478829600000,
    private String title              ;// "费率套餐提醒",
    private String userId             ;// "95500001412"

    public String getFailures() {
        return failures;
    }

    public void setFailures(String failures) {
        this.failures = failures;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
