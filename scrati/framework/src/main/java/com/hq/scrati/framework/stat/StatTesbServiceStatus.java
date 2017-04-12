package com.hq.scrati.framework.stat;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class StatTesbServiceStatus {
	
	/** metrics的统计数据, key为服务名称+"_"+接口版本
	 * 如 "ServiceName_1.0.0"
	 * 秒级统计数据
	 */
	protected static Map<String, StatisticItem> tesbServiceStatusMap = new ConcurrentHashMap<String, StatisticItem>();
	/**
	 * 根据key得到对应的统计项
	 * @param serviceId
	 * @return
	 */
	public static StatisticItem getStatisticItem(String metricKey) {
		if (!tesbServiceStatusMap.containsKey(metricKey)||tesbServiceStatusMap.get(metricKey)==null){
			tesbServiceStatusMap.put(metricKey, new StatisticItem());
		}
		return tesbServiceStatusMap.get(metricKey);
	}

	

	
	public void  setStatisticItem(String serviceCode,String version) throws UnsupportedEncodingException{
		/*
		 *获取指标 
		 */
		String metricKey=serviceCode+"_"+version;
		StatisticItem statisticItem=getStatisticItem(metricKey);
		statisticItem.addRequestNum(1);
		return;
	}
	
	public void  setSLAStatisticItem(String serviceCode,String version) throws UnsupportedEncodingException{
		/*
		 *获取指标 
		 */
		String metricKey=serviceCode+"_"+version;
		StatisticItem statisticItem=getStatisticItem(metricKey);
		statisticItem.addSlaOutNum(1);
		return;
	}
	
	public void setStatisticItem(String serviceCode,String version,boolean flags,Long latency,Long responseSize){
		/*
		 *获取指标 
		 */
		String metricKey=serviceCode+"_"+version;
		StatisticItem statisticItem=getStatisticItem(metricKey);
		if(flags){
			statisticItem.addSucessNum(1);
		}else{
			statisticItem.addLoseNum(1);
		}
		if(statisticItem.getTotalLatency()==0){
			statisticItem.setMinLatency(latency);
		}
		statisticItem.addTotalLatency(latency);
		
		if(latency<statisticItem.getMinLatency()){
			statisticItem.setMinLatency(latency);
		}else if(latency>statisticItem.getMaxLatency()){
			statisticItem.setMaxLatency(latency);
		}
		statisticItem.setServiceCode(serviceCode);
		statisticItem.setVersion(version);
	}
	
	
	public void clear(){
		Set<String> keys=tesbServiceStatusMap.keySet();
		for(String key:keys){
			tesbServiceStatusMap.put(key, new StatisticItem());
		}
	}
	
	public static Map<String, StatisticItem> getMetricStatistics() {
		return tesbServiceStatusMap;
	}

}
