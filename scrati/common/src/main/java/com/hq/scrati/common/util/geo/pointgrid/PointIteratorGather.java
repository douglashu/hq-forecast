package com.hq.scrati.common.util.geo.pointgrid;

import java.util.ArrayList;
import java.util.List;

import com.hq.scrati.common.util.geo.base.GeoPoint;
import com.hq.scrati.common.util.geo.base.IPoint;
import com.hq.scrati.common.util.geo.util.GeographyUtils;

/**
 * 循环判断的点集合
 * @author MAC
 */
public class PointIteratorGather implements IPointAreaGather{
	private List<IPoint> cacheList;
	@Override
	public void init() {
		
	}
	
	@Override
	public void setCacheList(List<IPoint> cacheList) {
		this.cacheList=cacheList;
	}
	
	@Override
	public List<PointAreaWrapper> isInArea(IPoint dstPoint, Double distance) {
		List<PointAreaWrapper> output=new ArrayList<PointAreaWrapper>();
		for(IPoint p:cacheList){
			Double dis=GeographyUtils.computerDistanceFromPoint(dstPoint, p);
			if(null!=dis && dis<=distance){
				PointAreaWrapper paw=new PointAreaWrapper();
				paw.setPoint(p);
				paw.setDistance(dis);
				output.add(paw);
			}
		}
		return output;
	}
	
	/**
	 * 获取范围内的点阵集合
	 * @param dstPoint
	 * @param distance
	 * @return
	 */
	protected static List<IPoint> getAreaPointList(IPoint dstPoint,Double distance){
		double jdWg=0.2;
		double wdWg=0.3;
		List<IPoint> list=new ArrayList<IPoint>();
		int repeat=5;
		for(double i=-jdWg*repeat;i<=jdWg*repeat;i+=jdWg){
			for(double j=-wdWg*repeat;j<=wdWg*repeat;j+=wdWg){
				double nwJd=(dstPoint.getLon()+i);
				double nwWd=(dstPoint.getLat()+j);
				GeoPoint dp=new GeoPoint(nwJd,nwWd);
				Double dis=GeographyUtils.computerDistanceFromPoint(dp, dstPoint);
				if(dis<=distance){
					list.add(dp);
				}
			}
		}
		return list;
	}
	
}
