package com.hq.app.olaf.ui.bean.app;

import com.hq.app.olaf.ui.bean.BaseEntity;

import java.util.List;

/**
 * Created by liaob on 2016/4/26.
 */
public class AppsData extends BaseEntity<AppsData> {
    private String hashcode;
    private List<Apps> apps;

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    public List<Apps> getApps() {
        return apps;
    }

    public void setApps(List<Apps> apps) {
        this.apps = apps;
    }
}
