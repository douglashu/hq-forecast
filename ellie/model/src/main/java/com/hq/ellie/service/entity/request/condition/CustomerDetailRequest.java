package com.hq.ellie.service.entity.request.condition;

import java.io.Serializable;

/** 
 * @author Yan  
 * @date 2016年12月13日  
 * @version V1.0  
 */
public class CustomerDetailRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1071167217109396049L;

	private String mchId;
	
	private String openid;
	
	private Integer plat_type;

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getPlat_type() {
		return plat_type;
	}

	public void setPlat_type(Integer plat_type) {
		this.plat_type = plat_type;
	}
}
