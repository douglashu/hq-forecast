package com.hq.scrati.common.util.geo.pointgrid;


/**
 * 点集合加载线程
 * @author MAC
 *
 */
public class PointAreaGatherLoader{
	private IPointAreaGather pointAreaGather;
	public void setPointAreaGather(IPointAreaGather pointAreaGather) {
		this.pointAreaGather = pointAreaGather;
	}
	
	protected void handle() throws Exception {
		pointAreaGather.init();
	}

}
