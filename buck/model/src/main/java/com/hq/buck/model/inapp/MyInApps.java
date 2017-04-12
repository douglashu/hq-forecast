package com.hq.buck.model.inapp;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;
import java.util.List;

/**
 * Created by zhaoyang on 03/01/2017.
 */
@Document(collection = "MyInApps")
public class MyInApps {

    @Id
    private String id;
    private String appId;
    private String userId;
    private List<String> inAppIds;
    private List<String> excludesInAppIds;

    public boolean isExcludesInApp(String inAppId) {
        if(CollectionUtils.isEmpty(getExcludesInAppIds())) return false;
        for(String exId: this.excludesInAppIds) {
            if(inAppId.equals(exId)) return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(this.inAppIds);
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getInAppIds() {
        return inAppIds;
    }

    public void setInAppIds(List<String> inAppIds) {
        this.inAppIds = inAppIds;
    }

    public List<String> getExcludesInAppIds() {
        return excludesInAppIds;
    }

    public void setExcludesInAppIds(List<String> excludesInAppIds) {
        this.excludesInAppIds = excludesInAppIds;
    }
}
