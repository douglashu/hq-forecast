package com.hq.scrati.common.util.geo.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

import com.hq.scrati.common.util.geo.base.DefaultPoint;
import com.hq.scrati.common.util.geo.base.IPoint;

/**
 *  获取角平分线上的两个点
 *  调用 public static Point getPointList (Point p1,Point p2,Point p3，double d)方法,
 *  顺序传人三个顶点，获取 以p2为顶点的角平分线上 到 p2的距离 为d 的点，有两个
 * @author wang
 *
 */
public class PointOfAngleBisector{
	private static final int DEF_DIV_SCALE = 10;//double值的小数位
	public static final Double LAT_METER=111199.233;
	/**
	 * 参数：三角形三顶点,需求点到 p2 的距离 d
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param d
	 * @return
	 */
	public static SecPoint getPointList(IPoint p1,IPoint p2,IPoint p3,double d){
		IPoint p0 = getInnerPoint(p1, p2, p3);//获取三角形角平分线交点
		IPoint po1 = getDegree2Meter(p0);//将交点坐标由度转为米
		IPoint po2 = getDegree2Meter(p2);//将p2点坐标由度转为米
		SecPoint sp = getSecPoint(po1, po2, d);
//		SecPoint sp=new SecPoint();
//		sp.setInner(p0);
//		sp.setOutter(getPointOfSymmetry(p0, p2));
		return sp;
	}
	
	public static void main(String[] args) {
		IPoint p1=new DefaultPoint();
//		p1.setLon(0.0);
//		p1.setLat(0.0);
		p1.setLon(113.13115007276136);
		p1.setLat(27.83171618933533);
		
		IPoint p2=new DefaultPoint();
//		p2.setLon(10.0);
//		p2.setLat(10.0);
		p2.setLon(113.13758737439818);
		p2.setLat(27.832114674227938);
		
		IPoint p3=new DefaultPoint();
//		p3.setLon(20.0);
//		p3.setLat(0.0);
		p3.setLon(113.13226587171175);
		p3.setLat(27.828509281456732);
		
		SecPoint sec=getPointList(p1, p2, p3,100);
		System.out.println(sec.getInner().getLon());
		System.out.println(sec.getInner().getLat());
		
		System.out.println(sec.getOutter().getLon());
		System.out.println(sec.getOutter().getLat());
	}
	
	/**
	 * 内外交点
	 * @author lcb
	 */
	public static class SecPoint{
		private IPoint inner;
		private IPoint outter;
		public IPoint getInner() {
			return inner;
		}
		public void setInner(IPoint inner) {
			this.inner = inner;
		}
		public IPoint getOutter() {
			return outter;
		}
		public void setOutter(IPoint outter) {
			this.outter = outter;
		}
	}
	/**
	 * 将点的坐标由度转为米
	 * @param p
	 * @return
	 */
	private static IPoint getDegree2Meter(IPoint p){
		IPoint point = new DefaultPoint();
		double y = transLat2Meter(p.getLat());
		double x = transLon2Meter(p.getLon(), p.getLat());
		point.setLon(x);
		point.setLat(y);
		return point;
	}
	
    /**
	 * 获取三角形角平分线交点
	 * @param pointList
	 * @return
	 */
	private static IPoint getInnerPoint(IPoint p1,IPoint p2,IPoint p3){
		IPoint point = new DefaultPoint();
		//第一个与第二个点距离
		double d1 = GeographyUtils.computerDistanceFromPoint(p1,p2);
		//第一个与第三个点距离
		double d2 = GeographyUtils.computerDistanceFromPoint(p1,p3);
		//第二个与第三个点距离
		double d3 = GeographyUtils.computerDistanceFromPoint(p2,p3);
		//三点构成三角形的周长
		double grith = d1+d2+d3;
		//交点横坐标的分子
		double xNumerator = d1*p3.getLon()+d2*p2.getLon()+d3*p1.getLon();
		//交点纵坐标的分子
		double yNumerator = d1*p3.getLat()+d2*p2.getLat()+d3*p1.getLat();
		//交点横坐标
		double xAngleBisector = getDivQuotient(xNumerator,grith);
		//交点纵坐标
		double yAngleBisector = getDivQuotient(yNumerator,grith);
		//设置交点的横纵坐标
		point.setLon(xAngleBisector);
		point.setLat(yAngleBisector);
		return point;
	}
	
	/**
	 * 返回v1除以v2 的double结果
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double getDivQuotient(Double v1,Double v2){
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());

		return b1.divide(b2,DEF_DIV_SCALE,BigDecimal.ROUND_HALF_UP).doubleValue();

	}
	/**
	 * double 值取一定精度返回
	 * @param d  需要处理的double值
	 * @param precision  保留的小数位数
	 * @return
	 */
	public double getPrecision(double d,int precision){
		NumberFormat formate = NumberFormat.getNumberInstance();
	    formate.setMaximumFractionDigits(precision);
	    String m = formate.format(d);
	    
	    return Double.valueOf(m);
	}
	
	/**
	 * 已知横坐标、斜率及线上一点 p2 ,求纵坐标
	 * @param p2
	 * @param x
	 * @param k
	 * @return
	 */
	public static double getVertical(IPoint p2,double x,double k){
		double y = k*(x-p2.getLon())+p2.getLat();
	    return y;
	}
	/**
	 * 获取斜率
	 * @param po1
	 * @param po2
	 * @return
	 */
	public static double getSlope(IPoint po1,IPoint po2){
		double k = (po2.getLat()-po1.getLat())/(po2.getLon()-po1.getLon());
		return k;
	}
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
	     return offset/(111199.233*Math.cos(lat*Math.PI/180));
	}
	/**
	 * 将纬度转换为米
	 * @param offset
	 * @return
	 */
	public static double transLat2Meter(double lat){
		return LAT_METER*lat;
	}
	/**
	 * 将经度转换为米
	 * @param offset
	 * @param lat
	 * @return
	 */
	public static double transLon2Meter(double lon,double lat){
		return (111199.233*Math.cos(lat*Math.PI/180))*lon;
	}
	/**
	 * 通过线上两点 p、p2 及 点p2到线上点C(未知点,有两个)的 距离d 求点C 的坐标
	 * @param p
	 * @param p2
	 * @param d
	 * @return
	 */
	private static SecPoint getSecPoint(IPoint p,IPoint p2,double d){
		IPoint point1 = new DefaultPoint();
		IPoint point2 = new DefaultPoint();
		SecPoint sp=new SecPoint();
		
		//如果斜率存在
		if((p.getLon()-p2.getLon())!=0){
		    double k = getSlope(p, p2);
//		    double x = getHorizontal(k,d,p2.getLon());
		    double d1 = Math.sqrt(k*k+1);
		    //获取第一个点的横纵坐标
		    double x1 = d/d1+p2.getLon();
		    double y1 = getVertical(p2, x1,k);
		    point1 = getMeter2Degree(x1, y1);
		    //获取第二个点的横纵坐标
		    double x2 = p2.getLon()-d/d1;
		    double y2 = getVertical(p2, x1,k);
		    point2  = getMeter2Degree(x2,y2);
		}else{
			point1 = getMeter2Degree(p2.getLon(), p2.getLat()+d);
		    point2 = getMeter2Degree(p2.getLon(), p2.getLat()-d);
		}
		//p到点point1 的距离
		double disP_point1 = GeographyUtils.computerDistanceFromPoint(p,point1);
		//p到点point2 的距离
		double disP_point2 = GeographyUtils.computerDistanceFromPoint(p,point2);
		if(disP_point1 < disP_point2){
			sp.setInner(point1);
		    sp.setOutter(point2);
		}else{
			sp.setInner(point2);
		    sp.setOutter(point1);
		}
		return sp;
		
	}
	/**
	 * 将点坐标由 米转为度
	 * @param x
	 * @param y
	 * @return
	 */
	private static IPoint getMeter2Degree(double x,double y){
		IPoint point = new DefaultPoint();
		double lat = transMeter2Lat(y);
		double lon = transMeter2Lon(x, lat);
		point.setLon(lon);
		point.setLat(lat);
		return point;
	}

}