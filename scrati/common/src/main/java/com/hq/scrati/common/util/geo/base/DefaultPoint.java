package com.hq.scrati.common.util.geo.base;

import java.io.Serializable;

public class DefaultPoint implements IPoint, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double lat;
	private Double lon;
	
	public DefaultPoint(Double lon,Double lat){
		this.lon=lon;
		this.lat=lat;
	}
	
	public DefaultPoint(){
	}
	
	@Override
	public Double getLat() {
		// TODO Auto-generated method stub
		return lat;
	}

	@Override
	public Double getLon() {
		// TODO Auto-generated method stub
		return lon;
	}

	@Override
	public void setLat(Double lat) {
		this.lat=lat;
	}

	@Override
	public void setLon(Double lon) {
		this.lon=lon;
	}
	
	public String toString(){
		return "LON:"+lon+",LAT:"+lat;
	}

}
