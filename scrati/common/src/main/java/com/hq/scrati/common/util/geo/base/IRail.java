package com.hq.scrati.common.util.geo.base;

import java.io.Serializable;
import java.util.List;

/**
 * 围栏接口
 * @author lcb
 */
public interface IRail extends Serializable{
	//唯一关键字
	String KEY_UNI_RAIL_ID="railId";
	//关联ID
	String KEY_AOC_RAIL_AOC_ID="railAocId";
	/**
	 * 获取围栏ID
	 * @return
	 */
	String getRailId();
	
	/**
	 * 获取围栏名称
	 * @return
	 */
	String getRailName();
	
	/**
	 * 获取围栏关联ID
	 * @return
	 */
	String getRailAocId();
	
	/**
	 * 获取围栏点列表
	 * @return
	 */
	List<IPoint> getRailPointList();
	/**
	 * 设置围栏点列表
	 * @return
	 */
	void setRailPointList(List<IPoint> list);
	/**
	 * 获取最右边的点
	 * @return
	 */
	IPoint getRightPoint();
	/**
	 * 设置最右边的点
	 * @return
	 */
	void setRightPoint(IPoint rightPoint);
}
