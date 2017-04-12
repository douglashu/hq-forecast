package com.hq.buck.model.inapp.view;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyang on 30/12/2016.
 */
public class InAppCatsView {

    private List<InAppCatView> inAppCatViews;

    public InAppCatsView() {
        this.inAppCatViews = new ArrayList<>();
    }

    public boolean isEmpty() {
        return this.inAppCatViews == null || this.inAppCatViews.size() == 0;
    }

    public List<InAppCatView> getInAppCatViews() {
        return inAppCatViews;
    }

    public void setInAppCatViews(List<InAppCatView> inAppCatViews) {
        this.inAppCatViews = inAppCatViews;
    }

}
