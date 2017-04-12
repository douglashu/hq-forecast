package com.hq.louis.model.umeng;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public class UMengMsg {

    // 应用唯一标识
    private String appkey;

    // 时间戳, 10 或者 13位,
    private String timestamp;

    // 推送类型
    private String type;

    // 设备唯一标识, 多个以英文逗号分隔, 不超过500个
    private String device_tokens;

    // 当type=customizedcast时，必填，alias的类型
    private String alias_type;

    // 当type=customizedcast时, 开发者填写自己的alias, 不超过50个
    private String alias;

    /*
        当type=filecast时，file内容为多条device_token (以回车符分隔)
        当type=customizedcast时，file内容为多条alias (以回车符分隔)
        使用文件播前需要先调用文件上传接口获取file_id
     */
    private String file_id;

    // 消息内容(Android最大为1840B)
    private UMengMsgPayload payload;

    // 发送策略
    private UMengMsgSendPolicy policy;

    // 正式/测试模式。测试模式下，只会将消息发给测试设备
    private String production_mode;

    // 发送消息描述，建议填写
    private String description;

    // 开发者自定义消息标识ID, 开发者可以为同一批发送的多条消息
    private String thirdparty_id;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevice_tokens() {
        return device_tokens;
    }

    public void setDevice_tokens(String device_tokens) {
        this.device_tokens = device_tokens;
    }

    public String getAlias_type() {
        return alias_type;
    }

    public void setAlias_type(String alias_type) {
        this.alias_type = alias_type;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public UMengMsgPayload getPayload() {
        return payload;
    }

    public void setPayload(UMengMsgPayload payload) {
        this.payload = payload;
    }

    public UMengMsgSendPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(UMengMsgSendPolicy policy) {
        this.policy = policy;
    }

    public String getProduction_mode() {
        return production_mode;
    }

    public void setProduction_mode(String production_mode) {
        this.production_mode = production_mode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThirdparty_id() {
        return thirdparty_id;
    }

    public void setThirdparty_id(String thirdparty_id) {
        this.thirdparty_id = thirdparty_id;
    }

}
