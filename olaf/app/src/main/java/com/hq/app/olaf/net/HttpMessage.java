package com.hq.app.olaf.net;

/**
 * Created by huwentao on 16/8/15.
 */
public class HttpMessage<T> {
    private String requestId;
    private String msg;
    private String key;
    private String signature;
    private T ext;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public T getExt() {
        return ext;
    }

    public void setExt(T ext) {
        this.ext = ext;
    }

    @Override
    public String toString() {
        return "HttpMessage{" +
                "requestId='" + requestId + '\'' +
                ", msg='" + msg + '\'' +
                ", key='" + key + '\'' +
                ", signature='" + signature + '\'' +
                ", ext=" + ext +
                '}';
    }
}
