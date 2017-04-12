package com.hq.louis.model.umeng;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public class UMengMsgSendPolicy {

    /*
       定时发送时间，若不填写表示立即发送。
       定时发送时间不能小于当前时间
       格式: "YYYY-MM-DD HH:mm:ss"。
       注意, start_time只对任务生效。
    */
    private String start_time;

    /*
        消息过期时间,其值不可小于发送时间或者
        start_time(如果填写了的话),
        如果不填写此参数，默认为3天后过期。格式同start_time
     */
    private String expire_time;

    /*
        开发者对消息的唯一标识，服务器会根据这个标识避免重复发送
        out_biz_no只对任务生效
     */
    private String out_biz_no;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }

    public String getOut_biz_no() {
        return out_biz_no;
    }

    public void setOut_biz_no(String out_biz_no) {
        this.out_biz_no = out_biz_no;
    }
}
