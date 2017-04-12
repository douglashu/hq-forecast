package com.hq.scrati.common.util.enu;

/**
 * 基础的枚举
 * @author Karma
 */
public interface BaseEnum<E extends Enum<E>> {
	/**
	 * 获取描述
	 * @return
	 */
    String getDesp();
    /**
     * 获取值
     * @return
     */
	Object getValue();
	/**
	 * 获取具体细节
	 * @return
	 */
	String getDetail();
	/**
	 * 设置描述
	 * @param desp
	 */
	void setDesp(String desp);
	/**
	 * 设置值
	 * @param value
	 */
	void setValue(Object value);
	/**
	 * 设置具体细节
	 * @param detail
	 */
	void setDetail(String detail);
}
