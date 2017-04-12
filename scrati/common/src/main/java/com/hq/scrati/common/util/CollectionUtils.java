package com.hq.scrati.common.util;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class CollectionUtils {

	public static boolean isEmpty(Collection<?> dataSource){
		return null == dataSource || dataSource.isEmpty();
	}
	
	public static boolean isNotEmpty(Collection<?> dataSource){
		return !isEmpty(dataSource);
	}
	
	public static <T> T findFirst(List<T> dataSource){
		return  CollectionUtils.isNotEmpty(dataSource) ? dataSource.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T,E> void copyCollections(List<T> dataSource, List<E> targetSource, Class<?> targetClazz){
		for (T obj : dataSource) {
			try {
				E rsp = (E) targetClazz.newInstance();
				BeanUtils.copyProperties(obj, rsp);
				targetSource.add(rsp);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
