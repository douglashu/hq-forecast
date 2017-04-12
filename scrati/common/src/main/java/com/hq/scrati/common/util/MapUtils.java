package com.hq.scrati.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.hq.scrati.common.util.ReflectionUtils.IFieldsIteratorCallback;

/**
 * Map工具
 *
 */
public class MapUtils {
	
	
	/**
	 * 从=号，号分割的字符串获取map
	 * 例如: 111=222,33=44
	 * @param value 
	 * @return
	 */
	public static Map<String,String> covertString2Map(String value){
		Map<String,String> map=null;
		if(ValidateUtils.isStrEmpty(value)){
			map=new HashMap<String, String>(0);
			return map;
		}
		
		String [] sValue=value.split(",");
		map=new HashMap<String, String>(sValue.length);
		
		for(String s:sValue){
			String [] v=s.split("=");
			if(v.length!=2){
				throw new RuntimeException("Map格式错误:"+value);
			}
			map.put(v[0], v[1]);
		}
		return map;
	}
	
	/**
	 * 升序排列Map
	 * @param map
	 * @return
	 */
	public static Map sortMapAsc(Map map) {
		return sortMapAsc(map, 0);
	}
	
	/**
	 * 降序排列Map
	 * @param map
	 * @return
	 */
	public static Map sortMapDesc(Map map) {
		return sortMapAsc(map, 1);
	}
	
	public static Map sortMapAsc(Map map,final int sort) {
		Map treeMap = new TreeMap(
			new Comparator() {
				public int compare(Object o1, Object o2) {
					if (o1 == null || o2 == null) {
						return 0;
					}
					if(0 == sort){
						return String.valueOf(o1).compareTo(String.valueOf(o2));
					}else{
						return String.valueOf(o2).compareTo(String.valueOf(o1));
					}
				}
		});
		treeMap.putAll(map);
		return treeMap;
	}
	
	/**
	 * 复制map
	 * @param inputMap 输入map
	 * @return 输出map
	 */
	public static Map cloneMap(Map inputMap){
		if(null == inputMap){
			return inputMap;
		}
		Map outputMap=new HashMap();
		for(Object o:inputMap.keySet()){
			outputMap.put(o, inputMap.get(o));
		}
		return outputMap;
	}
	
	/**
	 * 创建hashmap
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public static Map createHashMap(Object key,Object value){
		Map map=new HashMap();
		map.put(key, value);
		return map;
	}

	
	/**
	 * 将对象列表转化为Map列表
	 * @param object 输入对象
	 * @param map 数据map
	 */
	public static List<Map> transObjectList2MapList(List list){
		if(ValidateUtils.isCollectionEmpty(list)){
			return null;
		}
		List<Map> outputList=new ArrayList<Map>(list.size());
		for(Object o:list){
			Map map=transObject2Map(o);
			outputList.add(map);
		}
		return outputList;
	}

	/**
	 * 将对象转换为Map
	 * @param object 输入对象
	 * @return 对象Map
	 */
	public static Map<Object,Object> transObject2Map(Object object){
		Map<Object,Object> map=new LinkedHashMap<Object, Object>();
		if(null == object){
			return map;
		}
		transObject2Map(object, map);
		return map;
	}
	
	/**
	 * 将对象转化为Map
	 * @param object 输入对象
	 * @param map 数据map
	 */
	public static void transObject2Map(Object object,final Map map){
		ReflectionUtils.iterateFields(object, new IFieldsIteratorCallback() {
			@Override
			public void callback(Object object, Class curClass, Field field)
					throws Exception {
				String fieldName=field.getName();
				if(ReflectionUtils.hasMethod(object, ReflectionUtils.getGetterMethod(fieldName))){
					Object fieldValue = ReflectionUtils.getGetterMethodValue(object, fieldName);
					map.put(fieldName, fieldValue);
				}
			}
		});
	}
	
	/**
	 * 将Map转为对象
	 * @param map 数据map
	 * @param object 输入对象
	 */
	public static void transMap2Object(final Map map,Object object){
		for(Object key:map.keySet()){
			if(null == key){
				continue;
			}
			String lowwerKey=key.toString();
			Object value=map.get(key);
			ReflectionUtils.setFieldValueOnForce(object, value, lowwerKey);
		}
	}
	
	/**
	 * 将Map转为对象
	 * @param map 数据map
	 * @param claxx 输入对象
	 */
	public static <T> T  transMap2ObjectOnClass(final Map map,Class<T> claxx){
		T obj=ReflectionUtils.newInstanceClass(claxx);
		transMap2Object(map, obj);
		return obj;
	}
	
	/**
	 * 将Map列表转为对象列表
	 * @param mapList 数据map
	 * @param objectClaxx 输入对象
	 */
	public static List transMapList2ObjectList(List<Map> mapList,Class objectClaxx){
		if(ValidateUtils.isCollectionEmpty(mapList)){
			return null;
		}
		
		List outputList=new ArrayList(mapList.size());
		for(Map map:mapList){
			Object object=ReflectionUtils.newInstanceClass(objectClaxx);
			transMap2Object(map, object);
			outputList.add(object);
		}
		return outputList;
	}
	
	
	/**
	 * 对Map值进行排序
	 * @param map
	 */
	public static Map<String,Integer> sortMapValue(Map<String,Integer> map) {
	    ValueComparator bvc =  new ValueComparator(map);
	    Map<String,Integer> sortedMap = new TreeMap<String,Integer>(bvc);
	    sortedMap.putAll(map);  
	    return sortedMap;
	}

	private static class ValueComparator implements Comparator<String> {

	    Map<String, Integer> base;
	    public ValueComparator(Map<String, Integer> base) {
	        this.base = base;
	    }

	    // Note: this comparator imposes orderings that are inconsistent with equals.    
	    public int compare(String a, String b) {
	        if (base.get(a) >= base.get(b)) {
	            return -1;
	        } else {
	            return 1;
	        } // returning 0 would merge keys
	    }
	}
}
