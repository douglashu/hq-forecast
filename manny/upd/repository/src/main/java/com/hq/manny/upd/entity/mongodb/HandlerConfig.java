package com.hq.manny.upd.entity.mongodb;

import org.springframework.data.annotation.Id;

import java.util.Set;

/**
 * Created by Zale on 2016/11/23.
 */
public class HandlerConfig {
    @Id
    private String id;
    private String code;
    private String type;
    private String version;
    private Set<String> handlers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Set<String> getHandlers() {
        return handlers;
    }

    public void setHandlers(Set<String> handlers) {
        this.handlers = handlers;
    }
}
