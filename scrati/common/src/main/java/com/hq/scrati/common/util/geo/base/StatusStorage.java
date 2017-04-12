package com.hq.scrati.common.util.geo.base;

import java.util.List;

/**
 * 状态存储
 * 用于存储接收到的状态
 * @author lcb
 */
public interface StatusStorage {
	/**
	 * 获取上一次的运动状态
	 * @return
	 */
	IStatus getLastStatusModel();
	/**
	 * 设置上一次的运动状态
	 * @param status
	 */
	void setLastStatusModel(IStatus status);
	
	/**
	 * 获取当前的运动状态
	 * @return
	 */
	IStatus getNowStatusModel();
	
	/**
	 * 设置当前的运动状态
	 * @return
	 */
	void setNowStatusModel(IStatus status);
	
	/**
	 * 获取状态列表
	 * @return
	 */
	List<IStatus> getStatusList();
	
	/**
	 * 设置状态列表
	 * @return
	 */
	void setStatusList(List<IStatus> statusList);
}
