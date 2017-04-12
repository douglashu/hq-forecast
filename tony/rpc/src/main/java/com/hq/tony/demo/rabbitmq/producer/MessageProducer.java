package com.hq.tony.demo.rabbitmq.producer;

import java.util.HashMap;

import javax.annotation.Resource;

import com.hq.tony.demo.rabbitmq.MessageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 功能概要：消息产生,提交到队列中去
 * 
 * @author linbingwen
 * @since  2016年1月15日 
 */
//@Service
public class MessageProducer {
	
	private Logger logger = LoggerFactory.getLogger(MessageProducer.class);

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
			logger.info("",e);
		}
	}
	
	public static void main(String args[]){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring-mvc.xml"});
		context.start();
		MessageProducer producer = (MessageProducer)context.getBean("messageProducer");
		int a = Integer.MAX_VALUE;
       while (a > 0) {
    	   MessageEntity entity = new MessageEntity();
    	   entity.setBodyObjcet("Hello, I am amq sender num :" + a--);
    	   entity.setQueueName("queue_demo");
    	   entity.setExchangeName("amqpTemplate");
    	   producer.sendMessage(entity);
    	   System.out.println("========================");
    	   try {
    		   //暂停一下，好让消息消费者去取消息打印出来
               Thread.sleep(100);
           } catch (InterruptedException e) {
               e.printStackTrace(); 
           }
    
	   }
	}
	
	
}
