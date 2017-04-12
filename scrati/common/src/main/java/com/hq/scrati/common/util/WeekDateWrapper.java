package com.hq.scrati.common.util;

import java.util.Date;

/**
 * 周日期包装类
 * @author Karma
 */
public class WeekDateWrapper {
	//开始日期
	private Date stDate;
	//结束日期
	private Date edDate;
	
	//周一
	private Date monday;
	//周二
	private Date tuesday;
	//周三
	private Date wednesday;
	//周四
	private Date thursday;
	//周五
	private Date friday;
	//周六
	private Date saturday;
	//周日
	private Date sunday;
	
	
	public Date getStDate() {
		return stDate;
	}
	public void setStDate(Date stDate) {
		this.stDate = stDate;
	}
	public Date getEdDate() {
		return edDate;
	}
	public void setEdDate(Date edDate) {
		this.edDate = edDate;
	}
	public Date getMonday() {
		return monday;
	}
	public void setMonday(Date monday) {
		this.monday = monday;
	}
	public Date getTuesday() {
		return tuesday;
	}
	public void setTuesday(Date tuesday) {
		this.tuesday = tuesday;
	}
	public Date getWednesday() {
		return wednesday;
	}
	public void setWednesday(Date wednesday) {
		this.wednesday = wednesday;
	}
	public Date getThursday() {
		return thursday;
	}
	public void setThursday(Date thursday) {
		this.thursday = thursday;
	}
	public Date getFriday() {
		return friday;
	}
	public void setFriday(Date friday) {
		this.friday = friday;
	}
	public Date getSaturday() {
		return saturday;
	}
	public void setSaturday(Date saturday) {
		this.saturday = saturday;
	}
	public Date getSunday() {
		return sunday;
	}
	public void setSunday(Date sunday) {
		this.sunday = sunday;
	}
}
