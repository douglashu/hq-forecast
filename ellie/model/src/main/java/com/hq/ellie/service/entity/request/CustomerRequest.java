package com.hq.ellie.service.entity.request;

import java.io.Serializable;

/** 
 * @author Yan  
 * @date 2016年12月13日  
 * @version V1.0  
 */
public class CustomerRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2644386380881255748L;
	private String openid;
	
	private Integer plat_type;
	
	private String mchId;
	
	private String nickname;

	private String profile_photo;

	private String sex;

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getProfile_photo() {
		return profile_photo;
	}

	public void setProfile_photo(String profile_photo) {
		this.profile_photo = profile_photo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getPlat_type() {
		return plat_type;
	}

	public void setPlat_type(Integer plat_type) {
		this.plat_type = plat_type;
	}
}
