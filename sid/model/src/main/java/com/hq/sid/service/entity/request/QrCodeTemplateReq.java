package com.hq.sid.service.entity.request;

/**
 * @包名称：com.hq.sid.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/13 下午10:09
 */
public class QrCodeTemplateReq {

    private Integer id;

    private String name;

    private String image;

    private Integer offsetx;

    private Integer offsety;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getOffsetx() {
        return offsetx;
    }

    public void setOffsetx(Integer offsetx) {
        this.offsetx = offsetx;
    }

    public Integer getOffsety() {
        return offsety;
    }

    public void setOffsety(Integer offsety) {
        this.offsety = offsety;
    }
}
