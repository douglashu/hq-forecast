package com.hq.elva.entity.generate;

import java.io.Serializable;

public class TBasAlimcc implements Serializable {
    private String mccCd;

    private String pMccCd;

    private String busCls;

    private String busDesc;

    private static final long serialVersionUID = 1L;

    public String getMccCd() {
        return mccCd;
    }

    public void setMccCd(String mccCd) {
        this.mccCd = mccCd;
    }

    public String getpMccCd() {
        return pMccCd;
    }

    public void setpMccCd(String pMccCd) {
        this.pMccCd = pMccCd;
    }

    public String getBusCls() {
        return busCls;
    }

    public void setBusCls(String busCls) {
        this.busCls = busCls;
    }

    public String getBusDesc() {
        return busDesc;
    }

    public void setBusDesc(String busDesc) {
        this.busDesc = busDesc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", mccCd=").append(mccCd);
        sb.append(", pMccCd=").append(pMccCd);
        sb.append(", busCls=").append(busCls);
        sb.append(", busDesc=").append(busDesc);
        sb.append("]");
        return sb.toString();
    }
}