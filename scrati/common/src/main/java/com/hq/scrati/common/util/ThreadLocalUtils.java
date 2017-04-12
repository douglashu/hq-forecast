package com.hq.scrati.common.util;

import java.util.HashMap;
import java.util.Map;


/**
 * Elz线程池管理工具
 * @author lcb
 */
public class ThreadLocalUtils{
	public static ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();
	/**
	 * 获取当前线程存放的对象
	 * @return 当前线程存放的对象
	 */
	private static Object get() {
        return threadLocal.get();
    }

	/**
	 * 设置当前线程存放的对象
	 * @param value 当前线程存放的对象
	 */
	private static void put(Object value) {
        threadLocal.set(value);
    }


	/**
	 * 放入map数据
	 * @param key 关键字
	 * @param value 值
	 */
	public static void put(Object key,Object value){
		Map<Object, Object> map = getMap();
		map.put(key, value);
	}
	
	/**
	 * 获取值
	 * @param key 关键字
	 * @return 值
	 */
	public static Object get(Object key){
		Map<Object, Object> map = getMap();
		return map.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<Object, Object> getMap() {
		Map<Object,Object> map=(Map<Object,Object>)get();
		if(null == map){
			map=new HashMap<Object,Object>();
			put(map);
		}
		return map;
	}
	
	
	/**
	 * 清除数据
	 */
	public static void clearData() {
		Map<Object,Object> map=getMap();
		map.clear();
	}
}
