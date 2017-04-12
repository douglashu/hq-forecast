package com.hq.scrati.common.util.geo.base;

import java.util.Date;

/**
 * 物体的运动状态
 * 包含运动物体的最基本的信息
 * 用于构建一个单纯的会运动物体的模型
 * @author lcb
 */
public interface IStatus extends IPoint{
	
	/**
	 * 获取源经度
	 */
	Double getLonOrgin();
	/**
	 * 获取源维度
	 */
	Double getLatOrgin();
	
	/**
	 * 设置源经度
	 */
	void setLonOrgin(Double lon);
	/**
	 * 设置源纬度
	 */
	void setLatOrgin(Double lat);
	
	/**
	 * 位置是否锁星
	 * @return
	 */
	String getGPSLock();
	
	/**
	 * 设置位置是否锁星
	 * @return
	 */
	void setGPSLock(String gpsLock);
	/**
	 * 获取速度
	 */
	String getSpeed();
	/**
	 * 获取方向
	 */
	String getDirection();
	/**
	 * 设置方向
	 */
	void setDirection(String direction);
	/**
	 * 获取状态时间字符串
	 * @return
	 */
	String getStatusDateStr();
	
	/**
	 * 获取对应的运动对象模型ID
	 * @return
	 */
	String getObjectModelId();
	/**
	 * 获取状态类型
	 * @return
	 */
	int getStatusType();
	
	/**
	 * 设置状态类型
	 * @return
	 */
	void setStatusType(int statusType);
	
	/**
	 * 获取当前状态的描述
	 * @return
	 */
	String getPosDescrip();
	/**
	 * 设置当前状态的描述
	 * @return
	 */
	void setPosDescrip(String posDescrip);
	/**
	 * 获取状态时间
	 * @return
	 */
	Date getStatusDate();
	/**
	 * 获取接收时间
	 * @return
	 */
	Date getRecvTime();
	/**
	 * 设置接收时间
	 * @param recvTime
	 */
	void setRecvTime(Date recvTime); 
}
