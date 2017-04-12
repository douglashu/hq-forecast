package com.hq.sid.service.entity.response;

import java.io.Serializable;
import java.util.Date;

/**
 * @包名称：com.hq.sid.service.entity.response
 * @创建人：yyx
 * @创建时间：17/1/25 下午4:18
 */
public class QrCodeRsp implements Serializable {

    private static final long serialVersionUID = -6281561180369631595L;

    private Integer id;
    private Integer coreId;
    private String type;
    private String name;
    private String url;
    private Integer templateId;
    private String state;
    private Date createTime;
    private Date updateTime;
    private String remark;
    private String introduce;
    private String composeUrl;
    private Integer width;
    private Integer height;

    private String mchId;
    private String mchName;
    private String mchShortName;
    private String storeName;
    private String logo;

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchName() {
        return mchName;
    }

    public void setMchName(String mchName) {
        this.mchName = mchName;
    }

    public String getMchShortName() {
        return mchShortName;
    }

    public void setMchShortName(String mchShortName) {
        this.mchShortName = mchShortName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCoreId() {
        return coreId;
    }

    public void setCoreId(Integer coreId) {
        this.coreId = coreId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getComposeUrl() {
        return composeUrl;
    }

    public void setComposeUrl(String composeUrl) {
        this.composeUrl = composeUrl;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
