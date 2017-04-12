package com.hq.louis.model.apns;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public class APNsAlert {

    private String title;
    private String subtitle;
    private String body;

    public APNsAlert() {
    }

    public APNsAlert(String body) {
        this.body = body;
    }

    public APNsAlert(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
