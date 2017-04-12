package com.hq.scrati.cache.local;


import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存，不使用锁，最坏的结果是多线程导致重复put和delete，由ConcurrentHashMap保证一致性
 * <p>
 * 不使用线程定期清理，而是在put时查看是否超出设定容量，然后进行清理；在get时，根据标志位，查看是否超时，返回数据
 * 
 * @author zhouyang
 * 
 */
public class LocalCacheDelayExpire<T> {
	/**
	 * 表明key对应的值为空
	 */
	public static final String EMPTY = "_EP";

	/**
	 * 默认本地缓存的时间长度，单位：毫秒
	 */
	private static final Long LOCAL_EXPIRE_TIME = 60 * 1000L;
	
	/**
	 * 默认本地缓存的时间长度，单位：毫秒，一周
	 */
	private static final Long DELAY_EXPIRE_TIME = 7 *60 * 60 * 1000L;

	/**
	 * 超时时间，单位：毫秒，负数表示永不超时
	 */
	private Long expireTime = LOCAL_EXPIRE_TIME;

	/**
	 * 默认本地缓存的最大条数
	 */
	private static final Long MAX_LOCAL_CAPACITY = 1000 * 10000L;

	private Long capacity = MAX_LOCAL_CAPACITY;
	
	private Long delayExpireTime = DELAY_EXPIRE_TIME;

	private Map<String, Cacheable<T>> localCaches = new ConcurrentHashMap<String, Cacheable<T>>();

	public LocalCacheDelayExpire() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param expireTime
	 *            超时时间，单位：毫秒，负数表示永不超时
	 * @param capacity
	 *            本地缓存的最大条数
	 */
	public LocalCacheDelayExpire(Long expireTime, Long capacity) {
		super();
		this.expireTime = expireTime;
		this.capacity = capacity;
	}
	
	/**
	 * 构造函数
	 * 
	 * @param expireTime
	 *            超时时间，单位：毫秒，负数表示永不超时
	 * @param capacity
	 *            本地缓存的最大条数
	 * @param delayExpireTime
	 *            延迟失效时间
	 */
	public LocalCacheDelayExpire(Long expireTime, Long capacity ,Long delayExpireTime) {
		super();
		this.expireTime = expireTime;
		this.capacity = capacity;
		this.delayExpireTime = delayExpireTime;
	}

	public Long getDelayExpireTime() {
		return delayExpireTime;
	}

	public void setDelayExpireTime(Long delayExpireTime) {
		this.delayExpireTime = delayExpireTime;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public Long getCapacity() {
		return capacity;
	}

	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}
	
	/**
	 * 请求本地cache中的失效了超过delayExpireTime毫秒的数据
	 * @throws IllegalAccessException
	 */
	private void clearAndCheck() throws IllegalAccessException {
		if (localCaches.size() >= capacity) {
			for (Entry<String, Cacheable<T>> entry : localCaches.entrySet()) {
				Cacheable<T> cacheable = entry.getValue();
				if (null != cacheable
						&& System.currentTimeMillis() > cacheable
								.getExpiredTimestamp()+delayExpireTime) {
					localCaches.remove(entry.getKey());
				}
			}
		}

		if (localCaches.size() >= capacity) {
			throw new IllegalAccessException("the size reached capacity:"
					+ capacity);
		}
	}
	
	/**
	 * 对本地cache 进行put数据
	 * @param key
	 * @param value
	 * @throws IllegalAccessException
	 */
	public void put(String key, T value) throws IllegalAccessException {
		put(key, value, expireTime);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 *            超时时间，单位：毫秒，负数表示永不超时
	 * @throws IllegalAccessException
	 */
	public void put(String key, T value, Long timeout)
			throws IllegalAccessException {
		clearAndCheck();
		Cacheable<T> cacheable = new Cacheable<T>(value);
		if (0 <= timeout) {	
			cacheable.setExpiredTimestamp(System.currentTimeMillis() + timeout);
		} else {
			cacheable.setExpiredTimestamp(Long.MAX_VALUE);
		}
		localCaches.put(key, cacheable);
	}

	/**
	 * 根据key获取数据，如果已经失效超过delayExpireTime毫秒的，对数据进行移除，如果失效时间未超过delayExpireTime的，则不移除，仅返回null,如果未失效，则直接返回
	 * @param key
	 * @return
	 */
	public T get(String key) {
		Cacheable<T> cacheable = localCaches.get(key);
		if (null != cacheable) {
			Long expiredTime = cacheable.getExpiredTimestamp();
			if (System.currentTimeMillis() > expiredTime+delayExpireTime) {
				localCaches.remove(key);
				return null;
			}else if(System.currentTimeMillis() > expiredTime){
				return null;
			}
			return cacheable.getValue();
		}
		return null;
	}
	
	/**
	 * 根据key获取数据，如果已经失效超过delayExpireTime毫秒的，如果未失效,并且标志位true，则直接返回
	 * @param key
	 * @param delayFalg
	 * @return
	 */
	public T get(String key,boolean delayFalg) {
		Cacheable<T> cacheable = localCaches.get(key);
		if (null != cacheable) {
			Long expiredTime = cacheable.getExpiredTimestamp();
			if (System.currentTimeMillis() > expiredTime+delayExpireTime) {
				localCaches.remove(key);
				return null;
			}else if(System.currentTimeMillis() > expiredTime){
				if(delayFalg){
					return cacheable.getValue();
				}else{
					return null;
				}
			}
			return cacheable.getValue();
		}
		return null;
	}

	public void delete(String key) {
		if (null != key) {
			localCaches.remove(key);
		}
	}

}
