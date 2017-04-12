package com.hq.scrati.framework.rabbitmq.productor;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import com.hq.scrati.common.log.Logger;
import com.hq.scrati.framework.rabbitmq.MessageEntity;


/**
 * 功能概要：消息产生,提交到队列中去
 * 
 * @author linbingwen
 * @since  2016年1月15日 
 */
@Service
public class MessageProducer {
	
	public static final Logger logger = Logger.getLogger(MessageProducer.class);

	@Resource
	private HashMap<String, AmqpTemplate> amqpTemplateMap;
	/**
	 * rabbitMQ的生产者
	 */
	public void sendMessage(MessageEntity message){
		AmqpTemplate amqpTemplate = (AmqpTemplate)amqpTemplateMap.get(message.getExchangeName());
		try{
			amqpTemplate.convertAndSend(message.getQueueName(),message.getBodyObjcet());
		}catch (Exception e){
			logger.info(" ",e);
		}
	}
	
}
