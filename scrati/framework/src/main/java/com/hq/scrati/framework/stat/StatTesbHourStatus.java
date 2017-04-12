package com.hq.scrati.framework.stat;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.hq.scrati.cache.redis.RedisCacheDao;
import org.springframework.stereotype.Component;

@Component
public class StatTesbHourStatus {
	
	@Resource(name="JSONRedisCache")
	private RedisCacheDao jsonRedisCache;
	
	/** metrics的统计数据, key为服务名称+"_"+接口版本
	 * 如 "ServiceName_1.0.0"
	 * 秒级统计数据
	 */
	protected static Map<String, StatisticItem> tesbHourStatusMap = new ConcurrentHashMap<String, StatisticItem>();
	
	/** metrics的统计分服务请求方统计数据, key为服务名称+"_"+接口版本+appid
	 * 如 "ServiceName_1.0.0"
	 * 秒级统计数据
	 */
	protected static Map<String, StatisticItem> tesbAppHourStatusMap = new ConcurrentHashMap<String, StatisticItem>();
	/**
	 * 根据key得到对应的统计项
	 * @param serviceId
	 * @return
	 */
	public static StatisticItem getStatisticItem(String metricKey) {
		if (!tesbHourStatusMap.containsKey(metricKey)||tesbHourStatusMap.get(metricKey)==null){
			tesbHourStatusMap.put(metricKey, new StatisticItem());
		}
		return tesbHourStatusMap.get(metricKey);
	}

	
	public void  setStatisticItem(String serviceCode,String version) throws UnsupportedEncodingException{
		/*
		 *获取指标 
		 */
		String metricKey=serviceCode+"_"+version;
		StatisticItem statisticItem=getStatisticItem(metricKey);
		statisticItem.addRequestNum(1);
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
//		synchronized(statisticItem){
			statisticItem.setServiceCode(serviceCode);
			statisticItem.setVersion(version);
			 // 处理数据 
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
	}
	
	
	public void clear(){
		Set<String> keys=tesbHourStatusMap.keySet();
		for(String key:keys){
			tesbHourStatusMap.put(key, new StatisticItem());
		}
	}
	
	public static Map<String, StatisticItem> getMetricStatistics() {
		return tesbHourStatusMap;
	}
	
	public static Map<String, StatisticItem> getAppMetricStatistics() {
		return tesbAppHourStatusMap;
	}

}
