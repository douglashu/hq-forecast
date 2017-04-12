package com.hq.flood.service.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @包名称：com.hq.flood.service.response
 * @创建人：yyx
 * @创建时间：16/12/15 下午7:10
 */
public class ProdRsp implements Serializable{

    private static final long serialVersionUID = -8978963994592685565L;
    /**
     * 产品代码
     */
    private String prdCode;
    /**
     * 产品类型
     */
    private String prdType;
    /**
     * 产品名称
     */
    private String prdName;
    /**
     * 创建时间
     */
    private String gmtCreate;
    /**
     * 创建人
     */
    private String createUserId;
    /**
     * 最后修改时间
     */
    private String gmtModified;
    /**
     * 最后修改人
     */
    private String modifiedUserId;
    /**
     * 状态：1.正常使用中\n                        2.临时停用\n                        3.注销
     */
    private String status;
    /**
     * 备注
     */
    private String memo;

    private BigDecimal profitPoint;

    private String isStat;

    public String getPrdCode() {
        return prdCode;
    }

    public void setPrdCode(String prdCode) {
        this.prdCode = prdCode;
    }

    public String getPrdType() {
        return prdType;
    }

    public void setPrdType(String prdType) {
        this.prdType = prdType;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public BigDecimal getProfitPoint() {
        return profitPoint;
    }

    public void setProfitPoint(BigDecimal profitPoint) {
        this.profitPoint = profitPoint;
    }

    public String getIsStat() {
        return isStat;
    }

    public void setIsStat(String isStat) {
        this.isStat = isStat;
    }
}
