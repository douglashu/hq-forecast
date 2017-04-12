package com.hq.buck.model.inapp.view;

import com.hq.buck.model.inapp.InApp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyang on 28/12/2016.
 */
public class MyInAppsView {

    private List<InApp> inApps;

    public MyInAppsView() {
        this.inApps = new ArrayList<>();
    }

    public List<InApp> getInApps() {
        return inApps;
    }

    public void setInApps(List<InApp> inApps) {
        this.inApps = inApps;
    }
}
