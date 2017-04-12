package com.hq.scrati.common.util.geo.base;

import java.io.Serializable;


/**
 * 点信息接口
 * @author lcb
 */
public interface IPoint extends Serializable{
	/**
	 * 获取经度
	 */
	Double getLon();
	/**
	 * 获取维度
	 */
	Double getLat();
	
	/**
	 * 设置经度
	 */
	void setLon(Double lon);
	/**
	 * 设置纬度
	 */
	void setLat(Double lat);
}
