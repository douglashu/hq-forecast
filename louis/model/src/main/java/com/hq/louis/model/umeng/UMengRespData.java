package com.hq.louis.model.umeng;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public class UMengRespData {
    private String msg_id;
    private String task_id;
    private String error_code;
    private String thirdparty_id;

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getThirdparty_id() {
        return thirdparty_id;
    }

    public void setThirdparty_id(String thirdparty_id) {
        this.thirdparty_id = thirdparty_id;
    }
}
