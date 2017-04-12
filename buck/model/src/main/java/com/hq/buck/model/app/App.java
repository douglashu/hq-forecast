package com.hq.buck.model.app;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

/**
 * Created by zhaoyang on 18/12/2016.
 */
@Document(collection = "App")
public class App {

    @Id
    private String id;

    // 应用名称
    private String name;
    // 应用类别
    private String category;
    // 二级类别
    private String secondCategory;
    // 一句话广告宣传语
    private String kip;
    // 文字介绍
    private String introduce;

    // 小图标 URL
    private String iconS;
    // 中等图标 URL
    private String iconM;
    // 大图标 URL
    private String iconL;
    // 介绍图片 URLs
    private List<String> thumbs;

    private Long createTime;
    private Long updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(String secondCategory) {
        this.secondCategory = secondCategory;
    }

    public String getKip() {
        return kip;
    }

    public void setKip(String kip) {
        this.kip = kip;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getIconS() {
        return iconS;
    }

    public void setIconS(String iconS) {
        this.iconS = iconS;
    }

    public String getIconM() {
        return iconM;
    }

    public void setIconM(String iconM) {
        this.iconM = iconM;
    }

    public String getIconL() {
        return iconL;
    }

    public void setIconL(String iconL) {
        this.iconL = iconL;
    }

    public List<String> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<String> thumbs) {
        this.thumbs = thumbs;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
