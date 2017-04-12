package com.hq.scrati.framework.task;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.hq.esc.entity.generate.TesbMonitorHour;
import com.hq.scrati.framework.rabbitmq.productor.MessageProducer;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hq.scrati.framework.rabbitmq.MessageEntity;
import com.hq.scrati.framework.stat.StatTesbHourStatus;
import com.hq.scrati.framework.stat.StatisticItem;

@Component  
public class TesbHourStatusTask {
	
	public static final Logger logger = Logger.getLogger(MessageProducer.class);
	
	@Resource(name="JSONRedisCache")
	private RedisCacheDao jsonRedisCache;
	
	@Autowired
	private MessageProducer messageProducer;

	@Scheduled(cron="0 0 0/1 * * ? ")
	public void taskCycle(){
		//
		Map<String, StatisticItem> tesbAppServiceStatusMap = StatTesbHourStatus.getAppMetricStatistics();
		Set<String> appKeys=tesbAppServiceStatusMap.keySet();
		Calendar cal = Calendar.getInstance ();
        cal.set(Calendar.HOUR , Calendar.HOUR -1 );
        String lastHour = (new SimpleDateFormat("yyyyMMddHH")).format(cal.getTime());
		for(String key: appKeys){
			jsonRedisCache.delete("requestNum_"+key+"_"+lastHour, String.class);
			jsonRedisCache.delete("requestSize_"+key+"_"+lastHour, String.class);
		}
		Map<String, StatisticItem> tesbServiceStatusMap = StatTesbHourStatus.getMetricStatistics();
		Set<String> keys=tesbServiceStatusMap.keySet();
		//遍历statisticmap得到调用者对应的 StatisticItem
		for(String key: keys){
			StatisticItem statisticItem=tesbServiceStatusMap.get(key);
			if(statisticItem!=null){
				if(statisticItem.getSucessNum()+statisticItem.getLoseNum()!=0){
					TesbMonitorHour tesbHourStatus = new TesbMonitorHour();
					try {
						tesbHourStatus.setNodeName(InetAddress.getLocalHost().getHostAddress());
					} catch (UnknownHostException e) {
						logger.info("",e);
					}
					tesbHourStatus.setServiceCode(statisticItem.getServiceCode());
					tesbHourStatus.setServiceVersion(statisticItem.getVersion());
					tesbHourStatus.setLoseNum(statisticItem.getLoseNum());
					tesbHourStatus.setLoseRate(BigDecimal.valueOf(statisticItem.getLoseNum()).divide(
							BigDecimal.valueOf(statisticItem.getRequestNum()),5,BigDecimal.ROUND_HALF_UP));
					if(statisticItem.getRequestNum()-statisticItem.getSucessNum()-statisticItem.getLoseNum()-statisticItem.getSlaOutNum()<=0){
						tesbHourStatus.setProcessNum(0L);
					}else{
						tesbHourStatus.setProcessNum(statisticItem.getRequestNum()-statisticItem.getSucessNum()-statisticItem.getLoseNum()-statisticItem.getSlaOutNum());
					}
					tesbHourStatus.setRequestNum(statisticItem.getRequestNum());
					tesbHourStatus.setAvgLatency(BigDecimal.valueOf(statisticItem.getTotalLatency()).divide(
							BigDecimal.valueOf(statisticItem.getSucessNum()+statisticItem.getLoseNum()),0,BigDecimal.ROUND_HALF_UP).longValue());
					tesbHourStatus.setMaxLatency(statisticItem.getMaxLatency());
					tesbHourStatus.setMinLatency(statisticItem.getMinLatency());
					tesbHourStatus.setRecordTime((new SimpleDateFormat("yyyyMMddHH")).format(new Date()));
					tesbHourStatus.setSucessNum(statisticItem.getSucessNum());
					tesbServiceStatusMap.put(key,new StatisticItem());
					MessageEntity message = new MessageEntity();
					message.setExchangeName("amqpTemplate_hour");
					message.setQueueName("queue_monitorHour");
					message.setBodyObjcet(tesbHourStatus);
					messageProducer.sendMessage(message);
				}
			}
		}  
    } 
}
