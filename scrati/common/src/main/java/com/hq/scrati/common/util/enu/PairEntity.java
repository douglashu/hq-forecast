package com.hq.scrati.common.util.enu;

/**
 * 键值对实体类
 * @author Karma
 *
 */
public class PairEntity {
	private Object key;
	private Object value;
	public PairEntity(Object key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}
	public Object getKey() {
		return key;
	}
	public void setKey(Object key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
