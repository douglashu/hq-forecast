package com.hq.ellie.service.entity.request.condition.pagination;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Yan
 * @date 2016年12月13日
 * @version V1.0
 */
public class CustomerPaginationRequest extends BasePaginationRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3174463820306884962L;

	private String openid;
	
	private String mchId;
	
	private Integer plat_type;
	
	private Date beginLastVisitTime;
	
	private Date endLastvisitTime;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public Integer getPlat_type() {
		return plat_type;
	}

	public void setPlat_type(Integer plat_type) {
		this.plat_type = plat_type;
	}

	public Date getBeginLastVisitTime() {
		return beginLastVisitTime;
	}

	public void setBeginLastVisitTime(Date beginLastVisitTime) {
		this.beginLastVisitTime = beginLastVisitTime;
	}

	public Date getEndLastvisitTime() {
		return endLastvisitTime;
	}

	public void setEndLastvisitTime(Date endLastvisitTime) {
		this.endLastvisitTime = endLastvisitTime;
	}
}
