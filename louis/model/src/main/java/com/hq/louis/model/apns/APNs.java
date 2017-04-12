package com.hq.louis.model.apns;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public class APNs {

    private APNsAlert alert;
    private Integer badge;
    private String sound;
    private String category;

    public APNsAlert getAlert() {
        return alert;
    }

    public void setAlert(APNsAlert alert) {
        this.alert = alert;
    }

    public Integer getBadge() {
        return badge;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
