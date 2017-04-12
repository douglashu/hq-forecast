package com.hq.buck.model.inapp.view;

import com.hq.buck.model.inapp.InApp;
import org.springframework.util.CollectionUtils;
import java.util.List;

/**
 * Created by zhaoyang on 29/12/2016.
 */
public class InAppCatView {

    private String id;
    private String title;
    private String introduce;
    private List<InApp> inApps;

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(getInApps());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public List<InApp> getInApps() {
        return inApps;
    }

    public void setInApps(List<InApp> inApps) {
        this.inApps = inApps;
    }

}
