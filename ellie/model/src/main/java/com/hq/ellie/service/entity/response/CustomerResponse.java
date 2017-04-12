package com.hq.ellie.service.entity.response;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Yan
 * @date 2016年12月13日
 * @version V1.0
 */
public class CustomerResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7754437990862437937L;

	private String openid;

	private String plat_type;

	private String mchId;

	private String nickname;

	private String profile_photo;

	private String sex;

	private Integer visit_num;

	private Date lastvisit_time;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPlat_type() {
		return plat_type;
	}

	public void setPlat_type(String plat_type) {
		this.plat_type = plat_type;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
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

	public Integer getVisit_num() {
		return visit_num;
	}

	public void setVisit_num(Integer visit_num) {
		this.visit_num = visit_num;
	}

	public Date getLastvisit_time() {
		return lastvisit_time;
	}

	public void setLastvisit_time(Date lastvisit_time) {
		this.lastvisit_time = lastvisit_time;
	}

	@Override
	public String toString() {
		return "CustomerResponse [openid=" + openid + ", plat_type=" + plat_type + ", mchId=" + mchId + ", nickname="
				+ nickname + ", profile_photo=" + profile_photo + ", sex=" + sex + ", visit_num=" + visit_num
				+ ", lastvisit_time=" + lastvisit_time + "]";
	}
}
