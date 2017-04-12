package com.hq.louis.model.umeng;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public class UMengMsgBody {

    // 通知栏提示文字
    private String ticker;

    // 通知栏标题
    private String title;

    // 通知文字描述
    private String text;

    /*
        状态栏图标ID, R.drawable.[smallIcon],
        如果没有, 默认使用应用图标。
        图片要求为24*24dp的图标,或24*24px放在drawable-mdpi下。
        注意四周各留1个dp的空白像素
     */
    private String icon;

    /*
        通知栏拉开后左侧图标ID, R.drawable.[largeIcon].
        图片要求为64*64dp的图标,
        可设计一张64*64px放在drawable-mdpi下,
        注意图片四周留空，不至于显示太拥挤
     */
    private String largeIcon;

    /*
        通知栏大图标的URL链接。该字段的优先级大于largeIcon。
        该字段要求以http或者https开头。
     */
    private String img;

    /*
        默认为0，用于标识该通知采用的样式。使用该参数时,
        开发者必须在SDK里面实现自定义通知栏样式
     */
    private String sound;

    // 默认为0，用于标识该通知采用的样式。使用该参数时,
    // 开发者必须在SDK里面实现自定义通知栏样式
    private String builder_id;

    // 是否震动
    private String play_vibrate;

    // 是否闪灯
    private String play_lights;

    // 是否发声
    private String play_sound;

    /*
        点击"通知"的后续行为，默认为打开app
        "go_app": 打开应用
        "go_url": 跳转到URL
        "go_activity": 打开特定的activity
        "go_custom": 用户自定义内容
     */
    private String after_open;

    // 当"after_open"为"go_url"时，必填
    private String url;

    // 当"after_open"为"go_activity"时，必填
    private String activity;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLargeIcon() {
        return largeIcon;
    }

    public void setLargeIcon(String largeIcon) {
        this.largeIcon = largeIcon;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getBuilder_id() {
        return builder_id;
    }

    public void setBuilder_id(String builder_id) {
        this.builder_id = builder_id;
    }

    public String getPlay_vibrate() {
        return play_vibrate;
    }

    public void setPlay_vibrate(String play_vibrate) {
        this.play_vibrate = play_vibrate;
    }

    public String getPlay_lights() {
        return play_lights;
    }

    public void setPlay_lights(String play_lights) {
        this.play_lights = play_lights;
    }

    public String getPlay_sound() {
        return play_sound;
    }

    public void setPlay_sound(String play_sound) {
        this.play_sound = play_sound;
    }

    public String getAfter_open() {
        return after_open;
    }

    public void setAfter_open(String after_open) {
        this.after_open = after_open;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
