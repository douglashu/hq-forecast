package com.hq.scrati.common.util.geo.pointgrid;

import java.util.List;

import com.hq.scrati.common.util.geo.base.IPoint;

/**
 * 点集合范围判断
 * @author MAC
 */
public interface IPointAreaGather {
	
	/**
	 * 初始化
	 */
	void init();
	
	/**
	 * 设置点集合
	 * @param cacheList
	 */
	void setCacheList(List<IPoint> cacheList);
	
	/**
	 * 是否位于点整范围内
	 * @param dstPoint
	 * @param distance
	 * @return
	 */
	List<PointAreaWrapper> isInArea(IPoint dstPoint,Double distance);
}
