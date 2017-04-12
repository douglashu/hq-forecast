package com.hq.scrati.common.util.geo.pointgrid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.hq.scrati.common.util.geo.base.DefaultPoint;
import com.hq.scrati.common.util.geo.base.GeoPoint;
import com.hq.scrati.common.util.geo.base.IPoint;
import com.hq.scrati.common.util.geo.util.GeographyUtils;

/*Point*
 * 网格点集合
 * @author MAC
 */
public class PointGridGather implements IPointAreaGather{
	//网格集合列表
	private List<Double []> doubleArr;
	//点集合列表
	private List<IPoint> cacheList;
	//点阵映射
	private Map<Double [],Object> mapping=new HashMap<Double[],Object>();
	//分隔的网格数量
	private int gridNum=10;
	//网格容纳的最大点数量
	private int pointSize=400;
	//点范围扩展次数
	private int pointAreaExtendNum=3;
	//范围
	private Double [] rect={73.388671,135.351562,18.145851,53.488045};
	//获取经度差平均值
	private double jdWg;
	//获取纬度差平均值
	private double wdWg;
	
	/**
	 * 设置网格范围，数组为经度开始，结束，纬度开始，结束
	 * @param rect
	 */
	public void setRect(Double [] rect) {
		this.rect = rect;
	}
	
	@Override
	public void init() {
		initGrid();
		initPoint();
	}
	
	
	/**
	 * 获取范围内的点阵集合
	 * @param dstPoint
	 * @param distance
	 * @return
	 */
	protected List<IPoint> getAreaPointList(IPoint dstPoint,Double distance){
		List<IPoint> list=new ArrayList<IPoint>();
		for(double i=-jdWg*pointAreaExtendNum;i<=jdWg*pointAreaExtendNum;i+=jdWg){
			for(double j=-wdWg*pointAreaExtendNum;j<=wdWg*pointAreaExtendNum;j+=wdWg){
				double nwJd=(dstPoint.getLon()+i);
				double nwWd=(dstPoint.getLat()+j);
				GeoPoint dp=new GeoPoint(nwJd,nwWd);
				
				/*//如果在1倍的范围内，则不需要计算距离
				if(Math.abs(i/jdWg) == 1 && Math.abs(j/wdWg) == 1){
					list.add(dp);
				}else if(GeographyUtil.computerDistanceFromPoint(dp, dstPoint)<=distance){
					list.add(dp);
				}*/
				
				list.add(dp);
			}
		}
		return list;
	}
	private static Comparator<PointAreaWrapper> comparator=new Comparator<PointAreaWrapper>(){
		@Override
		public int compare(PointAreaWrapper o1, PointAreaWrapper o2) {
			return (int)(Double.valueOf(o1.getDistance())-Double.valueOf(o2.getDistance()));
		}
	};
	/**
	 * 是否位于点整范围内
	 * @param dstPoint
	 * @param distance
	 * @return
	 */
	public List<PointAreaWrapper> isInArea(IPoint dstPoint,Double distance){
		if(null == dstPoint){
			return null;
		}
		List<PointAreaWrapper> outputList=new ArrayList<PointAreaWrapper>();
		//获取扩展的点集合
		List<IPoint> exetendList=getAreaPointList(dstPoint, distance);
		for(Double [] rect:mapping.keySet()){
			for(IPoint extendP:exetendList){
				if(isInRect(rect,extendP)){
					Object value=(Object)mapping.get(rect);
					if(value instanceof List){
						List<IPoint> list=(List<IPoint>)value;
						for(IPoint p:list){
							Double dis= GeographyUtils.computerDistanceFromPoint(dstPoint, p);
							if(null!=dis && dis<=distance){
								PointAreaWrapper paw=new PointAreaWrapper();
								paw.setPoint(p);
								paw.setDistance(dis);
								outputList.add(paw);
							}
						}
					}else{
						PointGridGather pgg=(PointGridGather)value;
						outputList.addAll(pgg.isInArea(dstPoint, distance));
					}
				}
			}
		}
		Collections.sort(outputList, comparator);
		return outputList;
	}
	
	private Double getGridWidth(){
		Double jd1=rect[0];
		Double jd2=rect[0]+jdWg;
		Double wd1=rect[2];
		Double wd2=rect[2];
		return GeographyUtils.computerDistanceFromPoint(new GeoPoint(jd1,wd1), new GeoPoint(jd2,wd2));
	}
	
	private Double getGridHeight(){
		Double jd1=rect[0];
		Double jd2=rect[0];
		Double wd1=rect[2];
		Double wd2=rect[2]+wdWg;
		return GeographyUtils.computerDistanceFromPoint(new GeoPoint(jd1,wd1), new GeoPoint(jd2,wd2));
	}
	
	
	/**
	 * 初始化网格
	 */
	protected void initGrid(){
		//获取经度差
		double jdDis=rect[1]-rect[0];
		//获取纬度差
		double wdDis=rect[3]-rect[2];
		//获取经度差平均值
		jdWg=jdDis/gridNum;
		//获取纬度差平均值
		wdWg=wdDis/gridNum;
		
		//double width=getGridWidth();
		//double height=getGridHeight();
		
		
		doubleArr=new ArrayList<Double[]>();
		for(double i=rect[0];i<=rect[1];i+=jdWg){
			for(double j=rect[2];j<=rect[3];j+=wdWg){
				
				Double jd1=i;
				Double jd2=i+jdWg;
				Double wd1=j;
				Double wd2=j+wdWg;
				
				Double [] arr=new Double[4];
				arr[0]=jd1;
				arr[1]=jd2;
				arr[2]=wd1;
				arr[3]=wd2;
				doubleArr.add(arr);
				//System.out.println(":Rect:"+jd1+" "+jd2+" "+wd1+" "+wd2+":"+(jd1.equals(jd2)));
			}
		}
	}
	
	
	
	protected void initPoint(){
		Map<Double [],Object> mapping=new HashMap<Double[], Object>();
		calcPoint(cacheList,mapping);
		this.mapping=mapping;
		
		List<Double []> needSplitList=new ArrayList<Double []>();
		for(Double [] key:mapping.keySet()){
			List value=(List)mapping.get(key);
			if(value.size()>pointSize){
				needSplitList.add(key);
			}
			//System.out.println(Arrays.toString(key)+">>"+value.size());
		}
		
		for(Double [] key:needSplitList){
			PointGridGather pgg=new PointGridGather();
			pgg.setRect(key);
			List<IPoint> cacheList=new ArrayList<IPoint>(1);
			pgg.setCacheList(cacheList);
			pgg.init();
			mapping.put(key, pgg);
		}
	}
	
	protected boolean isInRect(Double [] rect,IPoint point){
		return point.getLon()>=rect[0] && point.getLon()<=rect[1] 
		 && point.getLat()>=rect[2]&&point.getLat()<=rect[3];
	}
	
	protected void calcPoint(List<IPoint> list,Map<Double [],Object> mapping){
		int size=0;
		if(null!=list){
			size=list.size();
		}
		
		//System.out.println(list.size());
		int nuUse=0;
		int use=0;
		for(IPoint point:list){
			try{
				if(null == point.getLon() || null == point.getLat()
						||0 == point.getLon() || 0 == point.getLat() ){
					nuUse++;
					continue;
				}
			}catch (Exception e) {
				e.printStackTrace();
				nuUse++;
				continue;
			}
			
			Boolean find=false;
			for(Double [] rect:doubleArr){
				
				if(isInRect(rect, point)){
					List<IPoint> pList=(List)mapping.get(rect);
					if(null == pList){
						pList=new ArrayList<IPoint>();
						mapping.put(rect, pList);
					}
					pList.add(point);
					find=true;
					break;
				}
				
			}
			
			if(!find){
				System.out.println(point.getLon()+":"+point.getLat()+"没找到网格");
			}else{
				use++;
			}
			
		}
	}
	
	public void setCacheList(List<IPoint> cacheList) {
		this.cacheList = cacheList;
	}

	public void setPointSize(int pointSize) {
		this.pointSize = pointSize;
	}

	public static void main(String[] args) {
		PointGridGather a=new PointGridGather();
		a.setRect(new Double[]{23.0,100.0,10.0,100.0});
		List<IPoint> list=new ArrayList<IPoint>();
		
		
		list.add(new DefaultPoint(80.0, 20.0));
		list.add(new DefaultPoint(90.0, 30.0));
		a.setCacheList(list);
		a.init();
		List<PointAreaWrapper> pawList=a.isInArea(new DefaultPoint(81.0, 20.0), 1000000.0);
//		ReflectionUtils(pawList);
	}
}
