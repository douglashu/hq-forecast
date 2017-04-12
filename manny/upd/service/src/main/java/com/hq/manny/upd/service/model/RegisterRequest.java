package com.hq.manny.upd.service.model;

/**
 * Created by Zale on 2016/11/23.
 */
public class RegisterRequest {
    private String code;
    private String version;
    private String type;
    private String port;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
