package com.hq.scrati.cache.redis;

import org.springframework.data.redis.core.RedisTemplate;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * redis缓存接口
 * 
 * @author Zale
 */
public interface RedisCacheDao {

	<T> void save(final String key, final T obj);
	<T> void save(final String key, final T obj, Long expires);
	<T> void delete(final String key, final Class<T> type);
	<T> T read(String key, Class<T> type);

	<T> void pushSetItem(final String key, final T obj);
	<T> void pushSetItem(final String key, final T obj, Long expires);
	<T> void removeSetItem(final String key, final T obj);
	<T> Set<T> readSet(String key, Class<T> type);

	<T> void pushLisItem(String key, T obj);
	<T> void pushListItem(String key, T obj, Long expires);
	<T> void removeListItem(String key, T obj);
	<T> List<T> readList(String key, Class<T> type);

	Long incr(final String key);
	Long incrBy(final String key, final Long integer);

	RedisTemplate<Serializable, Serializable> getRedisTemplate();

}
