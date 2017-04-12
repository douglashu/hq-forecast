package com.hq.scrati.framework.stat;

import java.util.concurrent.atomic.AtomicLong;

public class StatisticItem {
	
	private String timeStamp;
	
    private String serviceCode;
    
    private String version;
	
	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 总时延数，单位为毫秒
	 * 平均消息时延=totalLatency/(successNum+loseNum)
	 */
	public AtomicLong totalLatency = new AtomicLong(0);
	
	/**
	 * 周期内最大时延，单位为毫秒
	 */
	public AtomicLong maxLatency = new AtomicLong(0);

	/**
	 * 周期内最小时延，单位为毫秒
	 */
	public AtomicLong minLatency = new AtomicLong(0);
	
	/**
	 * 请求总数
	 */
	public AtomicLong requestNum = new AtomicLong(0);
	
	/**
	 * 请求总数
	 */
	public AtomicLong slaOutNum = new AtomicLong(0);
	
	/**
	 * 成功总数
	 */
	public AtomicLong sucessNum = new AtomicLong(0);
	
	/**
	 * 失败总数
	 */
	public AtomicLong loseNum = new AtomicLong(0);
	
	public long getSlaOutNum() {
		return slaOutNum.longValue();
	}

	public void setSlaOutNum(AtomicLong slaOutNum) {
		this.slaOutNum = slaOutNum;
	}
	
	public long addSlaOutNum(int slaOutNum) {
		return this.slaOutNum.addAndGet(slaOutNum);
	}

	public long addTotalLatency(long latency) {
		return this.totalLatency.addAndGet(latency);
	}
	
	public long addRequestNum(int requestNum) {
		return this.requestNum.addAndGet(requestNum);
	}
	
	public long addSucessNum(int sucessNum) {
		return this.sucessNum.addAndGet(sucessNum);
	}
	
	public long addLoseNum(int loseNum) {
		return this.loseNum.addAndGet(loseNum);
	}
	
	public long getTotalLatency() {
		return totalLatency.get();
	}

	public void setTotalLatency(AtomicLong totalLatency) {
		this.totalLatency = totalLatency;
	}

	public long getMaxLatency() {
		return maxLatency.get();
	}

	public void setMaxLatency(long maxLatency) {
		this.maxLatency.set(maxLatency);;
	}

	public long getMinLatency() {
		return minLatency.get();
	}

	public void setMinLatency(long minLatency) {
		this.minLatency.set(minLatency);
	}

	public long getRequestNum() {
		return requestNum.get();
	}

	public void setRequestNum(AtomicLong requestNum) {
		this.requestNum = requestNum;
	}

	public long getSucessNum() {
		return sucessNum.get();
	}

	public void setSucessNum(AtomicLong sucessNum) {
		this.sucessNum = sucessNum;
	}

	public long getLoseNum() {
		return loseNum.get();
	}

	public void setLoseNum(AtomicLong loseNum) {
		this.loseNum = loseNum;
	}


}
