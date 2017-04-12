package com.hq.buck.model.inapp.view;

import com.hq.buck.model.inapp.InApp;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhaoyang on 29/12/2016.
 */
public class InAppsView {

    private List<InApp> inApps;

    public InAppsView() {
        this.inApps = new ArrayList<>();
    }

    public InAppsView(List<InApp> inApps) {
        this();
        if(inApps != null && inApps.size() > 0) {
            this.inApps.addAll(inApps);
        }
    }

    public InApp getInAppById(String id) {
        if(StringUtils.isEmpty(id)) return null;
        for(InApp inApp: this.inApps) {
            if(id.equals(inApp.getId())) return inApp;
        }
        return null;
    }

    public Set<String> getFixedInAppIds() {
        Set<String> fixedInAppIds = new HashSet<>();
        for(InApp inApp: this.inApps) {
            if(inApp.getFixed()) {
                fixedInAppIds.add(inApp.getId());
            }
        }
        return fixedInAppIds;
    }

    public boolean isEmpty() {
        return this.inApps == null || this.inApps.size() == 0;
    }

    public List<InApp> getInApps() {
        return inApps;
    }

    public void setInApps(List<InApp> inApps) {
        this.inApps = inApps;
    }

}
