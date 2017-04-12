package com.hq.flood.demo.rabbitmq;

public class MessageEntity {
	
	private String exchangeName;
	
	private String queueName;
	
	private Object bodyObjcet;

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public Object getBodyObjcet() {
		return bodyObjcet;
	}

	public void setBodyObjcet(Object bodyObjcet) {
		this.bodyObjcet = bodyObjcet;
	}
	

}
