package com.hq.scrati.cache.local;


import java.io.Serializable;

/**
 * @author zhouyang
 *
 */
public class Cacheable<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long expiredTimestamp;

	private T value;

	public Cacheable(T value) {
		super();
		this.value = value;
	}

	public Long getExpiredTimestamp() {
		return expiredTimestamp;
	}

	public void setExpiredTimestamp(Long expiredTimestamp) {
		this.expiredTimestamp = expiredTimestamp;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
