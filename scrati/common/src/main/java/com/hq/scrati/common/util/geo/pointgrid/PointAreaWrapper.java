package com.hq.scrati.common.util.geo.pointgrid;

import com.hq.scrati.common.util.geo.base.IPoint;

/**
 * 点范围封装类
 * @author MAC
 *
 */
public class PointAreaWrapper {
	//结果点
	private IPoint point;
	//距离
	private Double distance;
	
	public IPoint getPoint() {
		return point;
	}
	public void setPoint(IPoint point) {
		this.point = point;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
}
