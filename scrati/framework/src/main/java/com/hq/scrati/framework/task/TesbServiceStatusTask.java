package com.hq.scrati.framework.task;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.hq.esc.entity.generate.TesbMonitorSecond;
import com.hq.scrati.framework.rabbitmq.MessageEntity;
import com.hq.scrati.framework.rabbitmq.productor.MessageProducer;
import com.hq.scrati.framework.stat.StatisticItem;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hq.scrati.framework.stat.StatTesbServiceStatus;

@Component  
public class TesbServiceStatusTask {
	
	public static final Logger logger = Logger.getLogger(MessageProducer.class);
	
	@Resource(name="JSONRedisCache")
	private RedisCacheDao jsonRedisCache;
	
	@Autowired
	private MessageProducer messageProducer;

	@Scheduled(cron="0/15 * * * * ? ")
	public void taskCycle(){
		
		Map<String, StatisticItem> tesbServiceStatusMap = StatTesbServiceStatus.getMetricStatistics();
		Set<String> keys=tesbServiceStatusMap.keySet();
		//遍历statisticmap得到调用者对应的 StatisticItem
		for(String key: keys){
			StatisticItem statisticItem=tesbServiceStatusMap.get(key);
			TesbMonitorSecond tesbServiceStatus = null;
//			synchronized(statisticItem){
				if(statisticItem!=null){
					if(statisticItem.getSucessNum()+statisticItem.getLoseNum()!=0&&statisticItem.getRequestNum()!=0){
						tesbServiceStatus = new TesbMonitorSecond();
						try {
							tesbServiceStatus.setNodeName(InetAddress.getLocalHost().getHostAddress());
						} catch (UnknownHostException e) {
							logger.info("",e);
						}
						tesbServiceStatus.setLoseNum(statisticItem.getLoseNum());
						tesbServiceStatus.setServiceCode(statisticItem.getServiceCode());
						tesbServiceStatus.setServiceVersion(statisticItem.getVersion());
						tesbServiceStatus.setLoseRate(BigDecimal.valueOf(statisticItem.getLoseNum()).divide(
								BigDecimal.valueOf(statisticItem.getRequestNum()),5,BigDecimal.ROUND_HALF_UP));
						if(statisticItem.getRequestNum()-statisticItem.getSucessNum()-statisticItem.getLoseNum()-statisticItem.getSlaOutNum()<=0){
							tesbServiceStatus.setProcessNum(0L);
						}else{
							tesbServiceStatus.setProcessNum(statisticItem.getRequestNum()-statisticItem.getSucessNum()-statisticItem.getLoseNum()-statisticItem.getSlaOutNum());
						}
						
						tesbServiceStatus.setRequestNum(statisticItem.getRequestNum());
						tesbServiceStatus.setAvgLatency(BigDecimal.valueOf(statisticItem.getTotalLatency()).divide(
								BigDecimal.valueOf(statisticItem.getSucessNum()+statisticItem.getLoseNum()),0,BigDecimal.ROUND_HALF_UP).longValue());
						tesbServiceStatus.setMaxLatency(statisticItem.getMaxLatency());
						tesbServiceStatus.setMinLatency(statisticItem.getMinLatency());
						tesbServiceStatus.setSucessNum(statisticItem.getSucessNum());
						tesbServiceStatus.setRecordTime((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
						tesbServiceStatusMap.put(key,new StatisticItem());
						MessageEntity message = new MessageEntity();
						message.setExchangeName("amqpTemplate_second");
						message.setQueueName("queue_monitorSecond");
						message.setBodyObjcet(tesbServiceStatus);
						messageProducer.sendMessage(message);
					}
					
				}
				
		}  
    } 
}
