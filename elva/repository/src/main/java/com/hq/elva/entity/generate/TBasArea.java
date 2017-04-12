package com.hq.elva.entity.generate;

import java.io.Serializable;

public class TBasArea implements Serializable {
    private String areaCode;

    private String areaName;

    private String upAreaCode;

    private Integer areaLevel;

    private String areaPinyin;

    private String areaShortName;

    private String cenLongitude;

    private String cenLatitude;

    private static final long serialVersionUID = 1L;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUpAreaCode() {
        return upAreaCode;
    }

    public void setUpAreaCode(String upAreaCode) {
        this.upAreaCode = upAreaCode;
    }

    public Integer getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getAreaPinyin() {
        return areaPinyin;
    }

    public void setAreaPinyin(String areaPinyin) {
        this.areaPinyin = areaPinyin;
    }

    public String getAreaShortName() {
        return areaShortName;
    }

    public void setAreaShortName(String areaShortName) {
        this.areaShortName = areaShortName;
    }

    public String getCenLongitude() {
        return cenLongitude;
    }

    public void setCenLongitude(String cenLongitude) {
        this.cenLongitude = cenLongitude;
    }

    public String getCenLatitude() {
        return cenLatitude;
    }

    public void setCenLatitude(String cenLatitude) {
        this.cenLatitude = cenLatitude;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", areaCode=").append(areaCode);
        sb.append(", areaName=").append(areaName);
        sb.append(", upAreaCode=").append(upAreaCode);
        sb.append(", areaLevel=").append(areaLevel);
        sb.append(", areaPinyin=").append(areaPinyin);
        sb.append(", areaShortName=").append(areaShortName);
        sb.append(", cenLongitude=").append(cenLongitude);
        sb.append(", cenLatitude=").append(cenLatitude);
        sb.append("]");
        return sb.toString();
    }
}