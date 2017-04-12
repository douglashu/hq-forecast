package com.hq.scrati.common.util.geo.util;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import com.hq.scrati.common.util.geo.base.DefaultPoint;
import com.hq.scrati.common.util.geo.base.IPoint;
import com.hq.scrati.common.util.geo.base.IRail;

/**
 * 地理工具
 * @author lcb
 */
public class GeographyUtils {
	
	
	/**
	 * 是否在点矩形内
	 * @param point
	 * @param pointRect
	 * @return
	 */
	public static boolean isInPointRect(IPoint point,PointRect pointRect){
		return isInPointRect(point.getLon(), point.getLat(), pointRect);
	}
	
	public static boolean isInPointRect(Double lon,Double lat,PointRect pointRect){
		if(lon>=pointRect.getLonSt() && lon<=pointRect.getLonEd() 
				&& lat>=pointRect.getLatSt() && lat<=pointRect.getLatEd()){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 获取点列表的最大矩形
	 * @param pointList
	 */
	public static PointRect getPointListRect(List<IPoint> pointList){
		Double lonSt=null;
		Double lonEd=null;
		Double latSt=null;
		Double latEd=null;
		//循环计算矩形
		for(IPoint p:pointList){
			if(null == lonSt || p.getLon()<lonSt){
				lonSt=p.getLon();
			}
			if(null == lonEd || p.getLon()>lonEd){
				lonEd=p.getLon();
			}
			if(null == latSt || p.getLat()<latSt){
				latSt=p.getLat();
			}
			if(null == latEd || p.getLat()>latEd){
				latEd=p.getLat();
			}
		}
		
		PointRect pointRect=new PointRect();
		pointRect.setLonSt(lonSt);
		pointRect.setLonEd(lonEd);
		pointRect.setLatSt(latSt);
		pointRect.setLatEd(latEd);
		
		return pointRect;
	}
	
	/**
	 * 点矩形
	 * @author Karma
	 */
	public static class PointRect{
		private Double lonSt;
		private Double lonEd;
		private Double latSt;
		private Double latEd;
		
		public Double getLonSt() {
			return lonSt;
		}
		public void setLonSt(Double lonSt) {
			this.lonSt = lonSt;
		}
		public Double getLonEd() {
			return lonEd;
		}
		public void setLonEd(Double lonEd) {
			this.lonEd = lonEd;
		}
		public Double getLatSt() {
			return latSt;
		}
		public void setLatSt(Double latSt) {
			this.latSt = latSt;
		}
		public Double getLatEd() {
			return latEd;
		}
		public void setLatEd(Double latEd) {
			this.latEd = latEd;
		}
		
		public String toString(){
			return this.getLonSt()+"-"+this.getLonEd()+"-"+this.getLatSt()+"-"+this.getLatEd();
		}
	}
	
	
	/**
	 * 判断点是否在本区域内
	 * @param p
	 * @return
	 */
	public static boolean pointIsInArea(IRail rail,IPoint p)
	{
		List<IPoint> pointList=rail.getRailPointList();
		if(pointList==null||pointList.size()==0){
			return false;
		}
		
		int crossTimes=0;
		int areaSidesSize=pointList.size();
		for(int i=0;i<areaSidesSize;i++)
		{
			IPoint p1 = pointList.get(i);
			IPoint p2 = pointList.get(i==areaSidesSize-1?0:i+1);
			if(Line2D.linesIntersect(p.getLon(), p.getLat(), 
					rail.getRightPoint().getLon(), rail.getRightPoint().getLat(), 
					p1.getLon(), p1.getLat(), p2.getLon(), p2.getLat())){
				crossTimes++;
			}
		}		
		return crossTimes%2==1;
	}
	
	/**
	 * 判断点是否在本区域内
	 * @param p
	 * @return
	 */
	public static boolean pointIsInArea(List<IPoint> pointList,IPoint rightPoint,IPoint p)
	{
		int crossTimes=0;
		int areaSidesSize=pointList.size();
		for(int i=0;i<areaSidesSize;i++)
		{
			IPoint p1 = pointList.get(i);
			IPoint p2 = pointList.get(i==areaSidesSize-1?0:i+1);
			if(Line2D.linesIntersect(p.getLon(), p.getLat(), 
					rightPoint.getLon()+1, rightPoint.getLat(), 
					p1.getLon(), p1.getLat(), p2.getLon(), p2.getLat())){
				crossTimes++;
			}
		}		
		return crossTimes%2==1;
	}
	
	
	
	/**
	 * 使用XY坐标 获取最近的距离
	 * @return
	 */
	public static Double getNearestDistance(List list,IPoint point){
		Double minDistance=null;
		for(int i=0;i<list.size();i++){
			IPoint pp=(IPoint)list.get(i);
			Double distance = GeographyUtils.computerDistanceFromPoint(pp, point);
			
			if(null ==minDistance || minDistance>distance){
				minDistance=distance;
			}
			
		}
		return minDistance;
	}
	
	/**
	 * 使用XY坐标 获取最近的距离
	 * @return
	 */
	public static PointDistanceWrapper getNearestPointAndDis(List list,IPoint point){
		Double minDistance=null;
		IPoint outPoint=null;
		int index=0;
		for(int i=0;i<list.size();i++){
			IPoint pp=(IPoint)list.get(i);
			Double distance = GeographyUtils.computerDistanceFromPoint(pp, point);
			
			if(null ==minDistance || minDistance>distance){
				minDistance=distance;
				outPoint=pp;
				index=i;
			}
		}
		
		PointDistanceWrapper pdw=new PointDistanceWrapper();
		pdw.setDis(minDistance);
		pdw.setPoint(outPoint);
		pdw.setIndex(index);
		
		return pdw;
	}
	/**
	 * 点和距离的封装类
	 * @author lcb
	 */
	public static class PointDistanceWrapper{
		private IPoint point;
		private Double dis;
		private int index;
		
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public IPoint getPoint() {
			return point;
		}
		public void setPoint(IPoint point) {
			this.point = point;
		}
		public Double getDis() {
			return dis;
		}
		public void setDis(Double dis) {
			this.dis = dis;
		}
	}
	
	/**
	 * 使用XY坐标 获取最近的距离
	 * @return
	 */
	public static Integer getNearestPointIndex(List list,IPoint point){
		Double minDistance=null;
		Integer index=null;
		for(int i=0;i<list.size();i++){
			IPoint pp=(IPoint)list.get(i);
			Double distance = GeographyUtils.computerDistanceFromPoint(pp, point);
			
			if(null ==minDistance || minDistance>distance){
				minDistance=distance;
				index=i;
			}
			
		}
		return index;
	}
	
	/**
	 * 使用XY坐标 获取最近的距离
	 * @return
	 */
	public static IPoint getNearestPoint(List list,IPoint point){
		if(null == point){
			return null;
		}
		
		Double minDistance=null;
		IPoint outputPoint=null;
		for(int i=0;i<list.size();i++){
			IPoint pp=(IPoint)list.get(i);
			Double distance = GeographyUtils.computerDistanceFromPoint(pp, point);
			
			if(null ==minDistance || minDistance>distance){
				minDistance=distance;
				outputPoint=pp;
			}
			
		}
		return outputPoint;
	}
	
	/**
	 * 使用XY坐标 获取最近的距离
	 * @return
	 */
	public static List<IPoint> getNearestPointList(List list,IPoint point,Integer rad){
		if(null == point){
			return null;
		}
		List<IPoint> outputList=new ArrayList<IPoint>();
		for(int i=0;i<list.size();i++){
			IPoint pp=(IPoint)list.get(i);
			Double distance = GeographyUtils.computerDistanceFromPoint(pp, point);
			
			if(distance<=rad){
				outputList.add(pp);
			}
			
		}
		return outputList;
	}
	
	
	
	/**
	 * 返回和目标点之间的距离
	 * @param position
	 * @return 米
	 */
	public static Double computerDistanceFromPoint(IPoint p1,IPoint p2)
	{
		if(null == p1 || null == p2){
			return null;
		}
		return getDistance(p1.getLat(),p1.getLon(),p2.getLat(),p2.getLon()); 
	}
	
	public static boolean isInArea(IPoint dstPoint,IPoint status,Integer radius){
		return isInArea(dstPoint, status, Double.valueOf(radius));
	}
	
	/**
	 * 判断位置是否在点范围内
	 * @param point
	 * @param p
	 * @return
	 */
	public static boolean isInArea(IPoint dstPoint,IPoint status,Double radius){
		//入参不能为空
		if(null == dstPoint||null == status || null == radius) {
			return false;
		}
		
		try{
			Double dis=GeographyUtils.computerDistanceFromPoint(dstPoint,status);
			if(null == dis){
				return false;
			}else{
				return dis<radius?true:false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 判断位置是否在点范围内
	 * @param point
	 * @param p
	 * @return
	 */
	public static Double isInAreaReutrnDis(IPoint dstPoint,IPoint status,Integer radius){
		//入参不能为空
		if(null == dstPoint||null == status || null == radius) {
			return -1d;
		}
		
		Double dis=GeographyUtils.computerDistanceFromPoint(dstPoint,status);
		if(null == dis){
			return -1d;
		}else{
			return dis<radius?dis:-1d;
		}
	}
	
	/**
	 * 根据传入的经纬度获取之间的距离
	 * @return
	 */
	public static Double getDistance(IPoint p1,IPoint p2)   
	{   
		return GeographyUtils.getDistance(
				Double.valueOf(p1.getLat()),
				Double.valueOf(p1.getLon()),
				Double.valueOf(p2.getLat()),
				Double.valueOf(p2.getLon()));
	} 
	
	
	/**
	 * 根据传入的经纬度获取之间的距离
	 * @return
	 */
	public static Double getDistance(Double lat1, Double lng1, Double lat2, Double lng2)   
	{   
		if(null == lat1 || null == lng1 || null == lat2 || null == lng2){
			return null;
		}
	   double radLat1 = rad(lat1);   
	   double radLat2 = rad(lat2);   
	   double a = radLat1 - radLat2;   
	   double b = rad(lng1) - rad(lng2);   
	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +    
	   Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));   
	   s = s * EARTH_RADIUS;   
	   s = Math.round(s * 10000) / 10000;   
	   return s;   
	} 
	
	public static void main(String[] args) {
		System.out.println(
				getDistance(28.093833,113.006533,28.095167,113.0066)		
		);
		
		
		/*List<IPoint> pointList =new ArrayList<IPoint>();
		DefaultPoint p=new DefaultPoint();
		p.setLon(112.982969d);
		p.setLat(28.193198d);
		pointList.add(p);
		
		p=new DefaultPoint();
		p.setLon(112.986209d);
		p.setLat(28.196243d);
		pointList.add(p);
		
		p=new DefaultPoint();
		p.setLon(112.988784d);
		p.setLat(28.193198d);
		pointList.add(p);*/
		
		List<IPoint> pointList =new ArrayList<IPoint>();
		
		IPoint p1=new DefaultPoint();
		p1.setLon(0.0);
		p1.setLat(0.0);
		pointList.add(p1);
		
		IPoint p2=new DefaultPoint();
		p2.setLon(10.0);
		p2.setLat(10.0);
		pointList.add(p2);
		
		IPoint p3=new DefaultPoint();
		p3.setLon(20.0);
		p3.setLat(0.0);
		pointList.add(p3);
		
		System.out.println(calcParallelLine(pointList,p3,100));
	}
	
	public static IPoint  computerRightPoint(List<IPoint> pointList)
	{
		double _rightPointX=0;
		double _rightPointY=0;
		for(int i=0;i<pointList.size();i++)
		{
			IPoint p=pointList.get(i);
			if(p.getLon()>_rightPointX)
			{
				_rightPointX=p.getLon();
				_rightPointY=p.getLat();
			}
		}
		
		return new DefaultPoint(_rightPointX+1,_rightPointY);
	}
	
	
	private final static  double EARTH_RADIUS = 6378.137*1000;   
	
	private static  double rad(Double d)   
	{   
	   return d * Math.PI / 180.0;   
	}
	
	/**
	 * 平行线
	 * @author lcb
	 */
	public static class ParallelLine{
		//坐端点
		private IPoint pointLeft;
		//右端点
		private IPoint pointRight;
		
		public IPoint getPointLeft() {
			return pointLeft;
		}
		public void setPointLeft(IPoint pointLeft) {
			this.pointLeft = pointLeft;
		}
		public IPoint getPointRight() {
			return pointRight;
		}
		public void setPointRight(IPoint pointRight) {
			this.pointRight = pointRight;
		}
		
		public String toString(){
			return "Left:"+pointLeft+",Right"+pointRight;
		}
	}
	
	
	private static int getReal(int index,int size){
		if(index>size-1){
			return index-size;
		}
		return index;
	}
	
	
	private static IPoint computerRightPointx(List<IPoint> pointList)
	{
		IPoint out=null;
		for(int i=0;i<pointList.size();i++)
		{
			if(null == out){
				out=pointList.get(i);
				continue;
			}
			
			if(pointList.get(i).getLon()>out.getLon())
			{
				out=pointList.get(i);
			}
		}
		return out;
	}
	
	
	
	public static List<IPoint> calcParallelLine(List<IPoint> pointList,IPoint rightPoint,int offset){
		List<IPoint> outputList=new ArrayList<IPoint>(pointList.size());
		for(int i=0;i<pointList.size();i++){
			int index1=getReal(i,pointList.size());
			int index2=getReal(i+1,pointList.size());
			int index3=getReal(i+2,pointList.size());
			
			IPoint p1=pointList.get(index1);
			IPoint p2=pointList.get(index2);
			IPoint p3=pointList.get(index3);
			
			PointOfAngleBisector.SecPoint sec=PointOfAngleBisector.getPointList(p1, p2, p3,2);
			boolean isIn=pointIsInArea(pointList, rightPoint,sec.getOutter());
			if(isIn){
				sec=PointOfAngleBisector.getPointList(p1, p2, p3,offset);
				outputList.add(sec.getInner());
			}else{
				sec=PointOfAngleBisector.getPointList(p1, p2, p3,offset);
				outputList.add(sec.getOutter());
			}
		}
		return outputList;
	}
	
	public static final Double LAT_METER=111199.233;
	/**
	 * 将米转换为纬度数
	 * @param offset
	 * @return
	 */
	public static Double transMeter2Lat(Double offset){
		return offset/LAT_METER;
	}
	/**
	 * 将米转换为经度数
	 * @param offset
	 * @return
	 */
	public static Double transMeter2Lon(double offset,Double lat){
		return offset/(111199.233*Math.cos(lat));
	}
	
	
	
	private static List<IPoint> calcParallelLine(List<IPoint> pointList,int offset){
		List<IPoint> outputList=new ArrayList<IPoint>();
		IPoint right=computerRightPointx(pointList);
		for(int i=0;i<pointList.size();i++){
			ParallelLine line1=new ParallelLine();
			ParallelLine line2=new ParallelLine();
			
			int index1=getReal(i,pointList.size());
			int index2=getReal(i+1,pointList.size());
			int index3=getReal(i+2,pointList.size());
			
			line1.setPointLeft(pointList.get(index1));
			line1.setPointRight(pointList.get(index2));
			line2.setPointLeft(pointList.get(index2));
			line2.setPointRight(pointList.get(index3));
			
			
			
			
			
			ParallelLine testp1=calcParallelLine(line1, 1);
			ParallelLine testp2=calcParallelLine(line2, 1);
			IPoint p=GetIntersection(testp1.getPointLeft(),testp1.getPointRight(),testp2.getPointLeft(),testp2.getPointRight());
			if(pointIsInArea(pointList,right,p)){
				offset=-offset;
			}
			
			ParallelLine p1=calcParallelLine(line1, offset);
			ParallelLine p2=calcParallelLine(line2, offset);
			
			System.out.println(p1);
			System.out.println(p2);
			p=GetIntersection(p1.getPointLeft(),p1.getPointRight(),p2.getPointLeft(),p2.getPointRight());
			outputList.add(p);
			
			
			
			/*p=GetIntersection(p1.getPointLeft(),p1.getPointRight(),p2.getPointLeft(),p2.getPointRight());
			if(pointIsInArea(pointList,right,p)){
				p1=calcParallelLine(line1, -offset);
				p2=calcParallelLine(line2, -offset);
				//p=GetIntersection(a, b, c, d);
				
			}*/
		}
		System.out.println(outputList);
		return outputList;
	}
	
	
	/**
	 * 计算平行线
	 * @param line
	 * @param offset
	 * @return
	 */
	public static ParallelLine calcParallelLine(ParallelLine line,double offset){
		IPoint pointLeft=line.getPointLeft();
		IPoint pointRight=line.getPointRight();
		
		
		//如果平行Y轴
		if(pointLeft.getLon().equals(pointRight.getLon())){
			ParallelLine outputLine=new ParallelLine();
			outputLine.setPointLeft(createPoint(pointLeft.getLon()+transMeter2Lon(offset, pointRight.getLon()), 
					pointLeft.getLat()));
			outputLine.setPointRight(createPoint(pointRight.getLon()+transMeter2Lon(offset, pointRight.getLon()), 
					pointRight.getLat()));
			return outputLine;
		}
		
		//如果平行X轴
		if(pointLeft.getLat().equals(pointRight.getLat())){
			ParallelLine outputLine=new ParallelLine();
			outputLine.setPointLeft(createPoint(pointLeft.getLon(), 
					pointLeft.getLat()+transMeter2Lat(offset)));
			outputLine.setPointRight(createPoint(pointRight.getLon(), 
					pointRight.getLat()+transMeter2Lat(offset)));
			return outputLine;
		}
		
		
		double realLength=transMeter2Lat(getOffsetLength(line,offset));
		ParallelLine outputLine=new ParallelLine();
		outputLine.setPointLeft(createPoint(pointLeft.getLon(), 
				pointLeft.getLat()+realLength));
		outputLine.setPointRight(createPoint(pointRight.getLon(), 
				pointRight.getLat()+realLength));
		return outputLine;
	}
	
	private static IPoint createPoint(final Double x,final Double y){
		DefaultPoint point=new DefaultPoint();
		point.setLon(x);
		point.setLat(y);
		return point;
	}
	
	/**
	 * 计算出需要偏移的距离
	 * @param line
	 * @param offset
	 * @return
	 */
	public static Double getOffsetLength(ParallelLine line,double offset){
		IPoint pointLeft=line.getPointLeft();
		IPoint p1=createPoint(pointLeft.getLon(), 
				pointLeft.getLat()+transMeter2Lat(offset));
		Double angle=computeAngle(p1, pointLeft, line.getPointRight());
		Double yangle=90-angle;
		Double dis=offset/Math.cos(yangle*Math.PI/180);;
		return dis;
	}
	
	/**
	 * 计算夹角
	 * A(a1,b1) B(a2,b2) C(a3,b3)   BA与BC夹角
	 */
	public static Double computeAngle(IPoint p1, IPoint p2,IPoint p3){
		if(null == p1 || null == p2 || null == p3){
			return null;
		}
		return computeAngle(p1.getLon(), p1.getLat(), 
				p2.getLon(), p2.getLat(),
				p3.getLon(), p3.getLat());
	}
	
	/**
	 * 计算夹角
	 * A(a1,b1) B(a2,b2) C(a3,b3)   BA与BC夹角
	 */
	/*public static Double computeAngle(Double a1, Double b1, Double a2, Double b2, Double a3, Double b3)
    {
		if(null == a1 || null == b1 || null == a2 || null ==b2 || null == a3|| null == b3){
			return null;
		}
		
		Double x1 = a2 - a1;
		Double y1 = b2 - b1;
		Double x2 = a3 - a2;
		Double y2 = b3 - b2;
		return (x1 * x2 + y1 * y2) / Math.sqrt((Math.pow(x1, 2) + 
				Math.pow(y1, 2)) * (Math.pow(x2, 2) + Math.pow(y2, 2)));
    }*/
	
	/**
	 * 计算夹角
	 * A(a1,b1) B(a2,b2) C(a3,b3)   BA与BC夹角
	 */
	public static Double computeAngle(Double x1, Double y1, Double x2, Double y2, Double x4, Double y4)
    {
		double ma_x = x1 - x2;  
		double ma_y = y1 - y2;  
		double mb_x = x4 - x2;
		double mb_y = y4 - y2;  
		double v1 = (ma_x * mb_x) + (ma_y * mb_y);
		double ma_val = Math.sqrt(ma_x*ma_x + ma_y*ma_y);
		double mb_val = Math.sqrt(mb_x*mb_x + mb_y*mb_y); 
		double cosM = v1 / (ma_val*mb_val);
		double angleAMB = Math.cos(cosM) * 180 / Math.PI;
		return angleAMB;
    }
	
	
	public static IPoint GetIntersection(IPoint a, IPoint b, IPoint c, IPoint d) {
		IPoint intersection = new DefaultPoint();
	 
	        if (Math.abs(b.getLat() - a.getLat()) + Math.abs(b.getLon() - a.getLon()) + Math.abs(d.getLat() - c.getLat())
	                 + Math.abs(d.getLon() - c.getLon()) == 0) {
	             if ((c.getLon() - a.getLon()) + (c.getLat() - a.getLat()) == 0) {
	                 System.out.println("ABCD是同一个点！");
	             } else {
	                 System.out.println("AB是一个点，CD是一个点，且AC不同！");
	             }
	             return null;
	         }
	 
	        if (Math.abs(b.getLat() - a.getLat()) + Math.abs(b.getLon() - a.getLon()) == 0) {
	             if ((a.getLon() - d.getLon()) * (c.getLat() - d.getLat()) - (a.getLat() - d.getLat()) * (c.getLon() - d.getLon()) == 0) {
	                 System.out.println("A、B是一个点，且在CD线段上！");
	             } else {
	                 System.out.println("A、B是一个点，且不在CD线段上！");
	             }
	             return null;
	         }
	         if (Math.abs(d.getLat() - c.getLat()) + Math.abs(d.getLon() - c.getLon()) == 0) {
	             if ((d.getLon() - b.getLon()) * (a.getLat() - b.getLat()) - (d.getLat() - b.getLat()) * (a.getLon() - b.getLon()) == 0) {
	                 System.out.println("C、D是一个点，且在AB线段上！");
	             } else {
	                 System.out.println("C、D是一个点，且不在AB线段上！");
	             }
	             return null;
	         }
	 
	        if ((b.getLat() - a.getLat()) * (c.getLon() - d.getLon()) - (b.getLon() - a.getLon()) * (c.getLat() - d.getLat()) == 0) {
	             System.out.println("线段平行，无交点！");
	             return null;
	         }
	 
	        intersection.setLon(((b.getLon() - a.getLon()) * (c.getLon() - d.getLon()) * (c.getLat() - a.getLat()) - 
	                c.getLon() * (b.getLon() - a.getLon()) * (c.getLat() - d.getLat()) + a.getLon() * (b.getLat() - a.getLat()) * (c.getLon() - d.getLon())) / 
	                ((b.getLat() - a.getLat()) * (c.getLon() - d.getLon()) - (b.getLon() - a.getLon()) * (c.getLat() - d.getLat())));
	         intersection.setLat(((b.getLat() - a.getLat()) * (c.getLat() - d.getLat()) * (c.getLon() - a.getLon()) - c.getLat()
	                 * (b.getLat() - a.getLat()) * (c.getLon() - d.getLon()) + a.getLat() * (b.getLon() - a.getLon()) * (c.getLat() - d.getLat()))
	                 / ((b.getLon() - a.getLon()) * (c.getLat() - d.getLat()) - (b.getLat() - a.getLat()) * (c.getLon() - d.getLon())));
	 
	        if ((intersection.getLon() - a.getLon()) * (intersection.getLon() - b.getLon()) <= 0
	                 && (intersection.getLon() - c.getLon()) * (intersection.getLon() - d.getLon()) <= 0
	                 && (intersection.getLat() - a.getLat()) * (intersection.getLat() - b.getLat()) <= 0
	                 && (intersection.getLat() - c.getLat()) * (intersection.getLat() - d.getLat()) <= 0) {
	             
	            System.out.println("线段相交于点(" + intersection.getLon() + "," + intersection.getLat() + ")！");
	             return intersection; // '相交
	         } else {
	             System.out.println("线段相交于虚交点(" + intersection.getLon() + "," + intersection.getLat() + ")！");
	             return intersection; // '相交但不在线段上
	         }
	     }
	
	/**
	 * 获取最右边的点
	 * @param pointList
	 * @return
	 */
	public static IPoint getRightPointFromList(List<IPoint> pointList)
	{
		if(pointList==null||pointList.size()==0){
			return null;
		}
		
		IPoint rightPoint=null;
		for(int i=0;i<pointList.size();i++)
		{	
			IPoint inPoint=pointList.get(i);
			
			if(null == rightPoint || inPoint.getLon()>rightPoint.getLon()){
				rightPoint=inPoint;
				continue;
			}
		}
		
		return rightPoint;
	}
}
