package com.hq.scrati.common.log;

/**
 * Created by Zale on 2016/11/18.
 */
public class LogModel {
    private String id;
    private String message;
    private Throwable t;


    private LogModel(String id, String message,Throwable t) {
        this.id = id;
        this.message = message;
        this.t = t;
    }
    public static LogModel build(String id){
        return build(id, null);
    }
    public static LogModel build(String id,String message){
        return build(id,message,null);
    }
    public static LogModel build(String id,String message,Throwable t){
        return new LogModel(id,message,t);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
