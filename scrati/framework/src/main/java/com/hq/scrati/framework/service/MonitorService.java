package com.hq.scrati.framework.service;

public interface MonitorService {

	/**
	 * 统计服务接口的请求数和请求流量，在Service请求的开始调用该方法
	 * @param serviceCode  Service的名称
	 * @param version  Service的版本
	 */
	public void prepareMetricDatum(String serviceCode,String version);
	
	/**
	 * 统计服务接口的请求数和请求流量，在Service请求的开始调用该方法
	 * @param serviceCode  Service的名称
	 * @param version  Service的版本
	 */
	public void slaMetricDatum(String serviceCode,String version);
	/**
	 * 统计服务接口的 结果、响应流量、时延
	 * @param serviceCode  Service的名称
	 * @param version  Service的版本
	 * @param userId	   调用者的ID
	 * @param flags    接口调用的结果  true表示成功 、false表示失败
	 * @param responseSize  响应实体   
	 * @param latency  处理时延  单位  Milliseconds
	 */
	public void resultMetricDatum(String serviceCode, String version,boolean flags,long latency,Object response);

}
