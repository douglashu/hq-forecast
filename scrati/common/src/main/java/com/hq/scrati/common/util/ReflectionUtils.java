package com.hq.scrati.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * 反射工具
 * @author lcb
 */
public class ReflectionUtils {
	public static final String GET_METHOD_HEAD_KEY="get";
	public static final String SET_METHOD_HEAD_KEY="set";
	
	
	/**
	 * 将对象的空字符串设置为NULL
	 * @param object 对象
	 */
	public static void setObjectEmptyString2Null(Object object){
		iterateFields(object, new IFieldsIteratorCallback() {
			@Override
			public void callback(Object object, Class<?> curClass, Field field)
					throws Exception {
				String methodName=getGetterMethod(field.getName());
				if(!hasMethod(object, methodName)){
					return;
				}
				Object val=getGetterMethodValue(object, field.getName());
				if(null == val){
					return;
				}
				if(val instanceof String){
					String s=(String)val;
					if(ValidateUtils.isStrEmpty(s)){
						setSetterMethodValue(object, field, null);
					}else{
						setSetterMethodValue(object, field, s.trim());
					}
				}
			}
		});
	}
	
	/**
	 * 实例化类路径
	 * @param className 类名
	 * @return 类对象
	 */
	public static Class<?> ClassForName(String className){
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 把properties数据填充到对象
	 * @param properties 数据
	 * @param object 填充对象
	 */
	public static void fillPropertiesObject(final Properties properties,Object object){
		fillObjectWithData(object, new ISourceDataGetter() {
			@Override
			public Object getData(Object object, Class<?> curClass, Field field) {
				//获取域名
				String fieldName=field.getName();
				
				final String value=properties.getProperty(fieldName);
				if(!ValidateUtils.isStrEmpty(value)){
					//将String数据转化为对应的类型数据
					return transString2Object(value, field.getType());
				}
				return null;
			}
		});
	}
	
	/**
	 * 数据获取接口
	 * @author Zale
	 */
	public static interface ISourceDataGetter{
		
		/**
		 * 获取数据
		 * @param object 填充目标对象
		 * @param curClass 填充目标当前的class对象
		 * @param field 填充对象域
		 * @return 待填充数据
		 */
		Object getData(Object object, Class<?> curClass, Field field);
		
	}
	
	/**
	 * 将数据填充到对象中
	 * @param object 填充对象
	 * @param sourceDataGetter 数据源获取者
	 */
	public static void fillObjectWithData(Object object,final ISourceDataGetter sourceDataGetter){
		iterateFields(object,true, new IFieldsIteratorCallback() {
			@Override
			public void callback(Object object, Class<?> curClass, Field field)
					throws Exception {
				if(!hasMethod(object, getSetterMethod(field.getName()), field.getType())){
					return;
				}
				//获取值
				Object value=sourceDataGetter.getData(object, curClass, field);
				//填充对象
				setSetterMethodValue(object, field, value);
			}
		});
	}
	
	
	/**
	 * 将String数据转为对应的数据类型对象
	 * @param value 数据
	 * @param valueClass 数据类型
	 * @return 转换后的数据
	 */
	public static Object transString2Object(String value,Class<?> valueClass){

		return transObject2Object(value,valueClass);
	}
	
	/**
	 * 将Object数据转为对应的数据类型对象
	 * @param value 数据
	 * @param valueClass 数据类型
	 * @return 转换后的数据
	 */
	public static Object transObject2Object(Object value,Class<?> valueClass){
		if(null == value){
			return null;
		}
		String stringValue=value.toString();
		if(valueClass.isAssignableFrom(String.class)){
			return stringValue;
		}else if(valueClass.isAssignableFrom(Integer.class)){
			return Integer.valueOf(stringValue);
		}else if(valueClass.isAssignableFrom(Double.class)){
			return Double.valueOf(stringValue);
		}else if(valueClass.isAssignableFrom(Float.class)){
			return Float.valueOf(stringValue);
		}else if(valueClass.isAssignableFrom(Long.class)){
			return Long.valueOf(stringValue);
		}else if(valueClass.isAssignableFrom(Short.class)){
			return Short.valueOf(stringValue);
		}else if(valueClass.isAssignableFrom(Boolean.class)){
			return Boolean.valueOf(stringValue);
		} 
		return value;
	}
	
	/**
	 * 迭代属性域
	 * @param curClass 当前class
	 * @param doParent 是否遍历父接口
	 * @param callback 回调函数
	 * @throws Exception
	 */
	public static void iterateClassFields(Class<?> curClass,Boolean doParent,IFieldsIteratorCallback callback){
		if(null == curClass){
			return;
		}
		
		Field [] srcFields=curClass.getDeclaredFields();
		if(!ValidateUtils.isArrEmpty(srcFields)){
			for(int i=0;i<srcFields.length;i++){
				Field field=srcFields[i];
				try {
					callback.callback(null, curClass,field);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		
		if(doParent){
			Class<?> claxx=curClass.getSuperclass();
			iterateClassFields(claxx, doParent, callback);
		}
	}
	
	/**
	 * 迭代属性域
	 * @param claxx
	 * @param callback 回调函数
	 */
	public static void iterateClassFields(Class<?> claxx,IFieldsIteratorCallback callback){
		iterateClassFields(claxx, true, callback);
	}
	
	
	/**
	 * 属性域迭代回调函数
	 *
	 */
	public static interface IFieldsIteratorCallback{
		/**
		 * 回调
		 * @param object 对象
		 * @param curClass 当前的class
		 * @param field 属性域
		 */
		void callback(Object object,Class<?> curClass,Field field) throws Exception;
		
	}
	
	
	
	/**
	 * 迭代属性域
	 * @param object 对象
	 * @param doParent 是否遍历父接口
	 * @param callback 回调函数
	 */
	public static void iterateFields(Object object,Boolean doParent,IFieldsIteratorCallback callback){
		Class<?> curClass=object.getClass();
		iterateFields(curClass, object, doParent, callback);
	}
	
	/**
	 * 迭代属性域
	 * @param curClass 当前class
	 * @param doParent 是否遍历父接口
	 * @param callback 回调函数
	 * @throws Exception
	 */
	public static void iterateFields(Class<?> curClass,Object object,Boolean doParent,IFieldsIteratorCallback callback){
		if(null == curClass){
			return;
		}
		Field [] srcFields=curClass.getDeclaredFields();
		if(!ValidateUtils.isArrEmpty(srcFields)){
			for(int i=0;i<srcFields.length;i++){
				Field field=srcFields[i];
				try {
					callback.callback(object, curClass,field);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		
		if(doParent){
			Class<?> claxx=curClass.getSuperclass();
			iterateFields(claxx, object, doParent, callback);
		}
	}
	
	/**
	 * 迭代属性域
	 * @param object 对象
	 * @param callback 回调函数
	 */
	public static void iterateFields(Object object,IFieldsIteratorCallback callback){
		iterateFields(object, true, callback);
	}
	
	/**
	 * 获取action方法中对外公开的方法
	 * @param claxx
	 * @return
	 */
	public static List<String> getActionOpenMethod(Class<?> claxx){
		Method []  mArr=claxx.getDeclaredMethods();
		List<String> list=new ArrayList<String>();
    	for(Method m:mArr){
			Class<?> returnType=m.getReturnType();
    		int mod=m.getModifiers();
    		//是公共方法而且返回类型是String
    		if(1 == mod &&
    				returnType.getSimpleName().equals("String")
    				){
    			list.add(m.getName());
    		}
    	}
    	return list;
	}
	
	/**
	 * 获取容器class的根路径
	 * @param claxx
	 * @return
	 */
	public static String getWebClassRootPath(Class<?> claxx){
		URL url=claxx.getResource("");
		String filePath=url.getFile();
		String keyName="classes";
		int index=filePath.indexOf(keyName);
		String cut=filePath.substring(0,index+keyName.length());
		return cut;
	}
	
	/**
	 * 拷贝新的集合
	 * @param collection
	 * @return
	 */
	public static <T> Collection<T> cloneCollection(Collection<T> collection){
		if(ValidateUtils.isCollectionEmpty(collection)){
			return collection;
		}
		
		Collection<T> outputList=(Collection<T>)newInstance(collection);
		for(T object:collection){
			T newObject=copy2NewObject(object);
			if(null!=newObject){
				outputList.add(newObject);
			}
		}
		return outputList;
	}
	
	/**
	 * 拷贝新的列表
	 * @param srcList
	 * @return
	 */
	public static <T> List<T> cloneList(List<T> srcList){
		if(ValidateUtils.isCollectionEmpty(srcList)){
			return srcList;
		}
		
		List<T> outputList=new ArrayList<T>(srcList.size());
		for(T object:srcList){
			T newObject=ReflectionUtils.copy2NewObject(object);
			if(null!=newObject){
				outputList.add(newObject);
			}
		}
		return outputList;
	}
	
	/**
	 * 拷贝到新的对象内
	 * 使用PropertyUtil工具类型
	 * @param srcObject 源对象
	 * @return 新对象
	 */
	public static <T> T copy2NewObject(T srcObject){
		T newObject=newInstance(srcObject);
		if(null == newObject){
			return null;
		}
		//复制私有区域
		copyPrivField(newObject, srcObject);
		return newObject;
		//屏蔽，不能复制没有get的方法
		/*try {
			PropertyUtils.copyProperties(newObject, srcObject);
			return newObject;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}*/
	}
	
	
	/**
	 * 创建新的实例
	 * @param srcObject 输入对象
	 * @return 新对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(T srcObject){
		if(null == srcObject){
			return null;
		}
		
		Class<T> claxx=(Class<T>) srcObject.getClass();
		return newInstanceClass(claxx);
	}
	
	/**
	 * 创新新对象
	 * @param claxx 类
	 * @return 新创建对象
	 */
	public static <T> T newInstanceClass(Class<T> claxx){
		if(null == claxx){
			return null;
		}
		
		try {
			return claxx.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	/**
	 * 获取类名
	 * @param object
	 * @return
	 */
	public static String getClassName(Object object){
		return object.getClass().getName();
	}
	/**
	 * 获取简单类名
	 * @param object
	 * @return
	 */
	public static String getSimpleClassName(Object object){
		return object.getClass().getSimpleName();
	}
	
	/**
	 * 将集合转换为Map列表
	 * @param collection 输入集合
	 * @return 对象Map
	 */
	public static List<Map<Object,Object>> covertCollection2MapList(Collection<?> collection){
		if(ValidateUtils.isCollectionEmpty(collection)){
			return null;
		}
		
		List<Map<Object,Object>> list=new ArrayList<Map<Object,Object>>(collection.size());
		for(Object object:collection){
			Map<Object,Object> mapData=MapUtils.transObject2Map(object);
			list.add(mapData);
		}
		
		return list;
	}
	
	
	/**
	 * 打印对象
	 * @param object 输入对象
	 */
	public static void print(Object object){
		if(null == object){
			stdOutput(object);
			return;
		}
		
		if(object instanceof Collection){
			Collection<?> col=(Collection<?>)object;
			if(ValidateUtils.isCollectionEmpty(col)){
				return;
			}
			for(Object o:col){
				if(o instanceof Map
						|| o instanceof Collection){
					stdOutput(o);
				}else{
					printImpl(o);
				}
			}
		}else{
			printImpl(object);
		}
	}
	
	/**
	 * 标准输出
	 * @param object 对象
	 */
	private static void stdOutput(Object object){
		System.out.println(object);
	}
	
	/**
	 * 打印对象列表
	 * @param object 输入对像
	 */
	private static void printImpl(Object object){
		stdOutput(MapUtils.transObject2Map(object));
	}
	
	/**
	 * 获取方法返回值
	 * @param src 对象
	 * @param fieldName 方法名
	 * @return 方法值
	 */
	public static Object getGetterMethodValue(Object src,String fieldName){
		 return invokeMethodNoArgs(src, getGetterMethod(fieldName));
	}
	
	/**
	 * 获取方法返回值
	 * @param src 对象
	 * @param method 方法对象
	 * @return 方法值
	 */
	public static Object getGetterMethodValue(Object src,Method method){
		 return invokeMethodNoArgs(src, method);
	}
	
	
	/**
	 * 执行方法
	 * @param method 方法
	 * @param src 对象
	 * @param args 参数
	 * @return 方法返回值
	 */
	public static Object invokeMethod(Method method,Object src,Object ... args){
		Object fieldValue;
		try {
			fieldValue = method.invoke(src,   args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return fieldValue;
	}
	
	
	/**
	 * 根据属性名获取属性对象
	 * @param claxx 类对象
	 * @param fieldName 属性名
	 * @return 属性对象
	 */
	public static Field getFieldByName(Class<?> claxx,String fieldName){
		
		Field [] fields=null;
		Class<?> curClass=claxx;
		while(null!=curClass){
			fields=curClass.getDeclaredFields();
			curClass=curClass.getSuperclass();
			if(ValidateUtils.isArrEmpty(fields)){
				continue;
			}
			for(Field f:fields){
				if(f.getName().equals(fieldName)){
					return f;
				}
			}
		}
		return null;
	}
	
	/**
	 * 强制填充属性到一个对象
	 * @param object 对象
	 * @param value 值
	 * @param fieldName 属性名
	 */
	public static void setFieldValueOnForce(Object object,
			Object value,String fieldName){
		Field f=getFieldByName(object.getClass(), fieldName);
		if(null == f){
			return;
		}
		Object afterValue=transObject2Object(value, f.getType());
		setValue2Field(f, object, afterValue);
	}
	
	/**
	 * 是否存在方法
	 * @param src 对象
	 * @param methodName 方法名
	 * @param types 类型数组
	 * @return 是否存在
	 */
	public static boolean hasMethod(Object src,String methodName,Class<?> ... types){
		Method method=null;
		try{
			method = getMethod(src,methodName,types); 
		}catch (Exception e) {
			return false;
		}
		if(null == method){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 执行无参数的方法
	 * @param src 对象
	 * @param methodName 方法名
	 * @return 方法返回值
	 */
	public static Object invokeMethodNoArgs(Object src,String methodName){
		 Method method=getMethod(src,methodName); 
		 return invokeMethod(method, src);
	}
	
	/**
	 * 执行无参数的方法
	 * @param src 对象
	 * @param method 方法
	 * @return 方法返回值
	 */
	public static Object invokeMethodNoArgs(Object src,Method method){
		 return invokeMethod(method, src);
	}
	
	
	
	
	/**
	 * 执行方法
	 * @param src 对象
	 * @param methodName 方法名
	 * @param paramClass 方法参数class
	 * @param paramValue 方法参数值
	 * @return 方法返回值
	 */
	public static Object invokeMethod(Object src,String methodName,Class<?> [] paramClass,Object [] paramValue){
		 Method  method =  getMethod(src,methodName,   paramClass); 
		 Object  fieldValue = invokeMethod(method,src,paramValue); 
		 return fieldValue;
	}
	
	/**
	 * 执行方法
	 * @param src 对象
	 * @param methodName 方法名
	 * @param paramValue 参数值
	 * @return 方法返回值
	 */
	public static Object invokeMethod(Object src,String methodName,Object [] paramValue){
		Method [] method=src.getClass().getMethods();
		for(Method m:method){
			if(m.getName().equals(methodName)){
				Object fieldValue=invokeMethod(m, src, paramValue);
				return fieldValue;
			}
		}
		//如果没有找到则抛出异常
		throw ExceptionUtils.newRuntimeException("找不到对象的方法[%s],对象[%s],参数列表[%s]", methodName,src,Arrays.toString(paramValue));
	}
	
	
	/**
	 * 获取对象的方法对象
	 * @param src 对象
	 * @param fieldName 方法名
	 * @return 方法对象
	 */
	public static Method getMethod(Object src,String fieldName,Class<?> ... types ){
		return getMethodByClass(src.getClass(), fieldName, types);
	}
	
	/**
	 * 获取对象的方法对象
	 * @param claxx class对象
	 * @param fieldName 方法名
	 * @return 方法对象
	 */
	public static Method getMethodByClass(Class<?> claxx,String fieldName,Class<?> ... types ){
		try {
			Method method=claxx.getMethod(fieldName,types);
			return method;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	/**
	 * 将值设置进setter方法
	 * @param dst 目标对象
	 * @param fieldName 属性名
	 * @param fieldType 属性类型
	 * @param fieldValue 设置的值
	 * @param claxx 方法获取对象额Class
	 */
	public static void setSetterMethodValue(Object dst,String fieldName,Class<?> fieldType,Object fieldValue,Class<?> claxx){
		 Method method=getMethodByClass(claxx, getSetterMethod(fieldName), new Class[]{fieldType});
         invokeMethod(method,dst,fieldValue);
	}
	
	/**
	 * 将值设置进setter方法
	 * @param dst 目标对象
	 * @param field 属性域对象
	 * @param fieldValue 设置的值
	 */
	public static void setSetterMethodValue(Object dst,Field field,Object fieldValue){
		setSetterMethodValue(dst, field.getName(), field.getType(), fieldValue,dst.getClass());
	}
	
	/**
	 * 获取读取方法
	 * @param fieldName
	 * @return
	 */
	public static String getGetterMethod(String fieldName){
		return GET_METHOD_HEAD_KEY+getUpperFirstString(fieldName);
	}
	/**
	 * 获取设置方法
	 * @param fieldName
	 * @return
	 */
	public static String getSetterMethod(String fieldName){
		return SET_METHOD_HEAD_KEY+getUpperFirstString(fieldName);
	}
	
	/**
	 * 获取首字母大写
	 * @param str
	 * @return
	 */
	public static String getUpperFirstString(String str){
		return str.substring(0,1).toUpperCase()+str.substring(1);
	}
	
	/**
	 * 获取首字母小写
	 * @param str
	 * @return
	 */
	public static String getLowerFirstString(String str){
		return str.substring(0,1).toLowerCase()+str.substring(1);
	}
	
	/**
	 * 根据对象获取其属性
	 * @param obj
	 * @return
	 */
	public static Field[] getDeclaredFields(Object obj){
		return obj.getClass().getDeclaredFields();
	}
	
	/**
	 * 根据对象获取其属性
	 * @param claxx
	 * @return
	 */
	public static Field[] getDeclaredFields(Class<?> claxx){
		return claxx.getDeclaredFields();
	}
	
	
	/**
	 * 获取目标指定的属性
	 * @param claxx class对象
	 * @param fieldName 属性名
	 * @return
	 */
	public static Field getDeclaredField(Class<?> claxx,String fieldName){
		try {
			return claxx.getDeclaredField(fieldName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	/**
	 * class是否相等
	 * @param claxx1 class对象
	 * @param claxx2 class对象
	 * @return 是否相等
	 */
	public static boolean isClaxxEq(Class<?> claxx1,Class<?> claxx2){
		if(claxx1.getName().equals(claxx2.getName())){
			return true;
		}else{
			return false;
		}
	}
	
	
	//##############################复制对象#############################

	/**
	 * 当源属性为空的时候不复制
	 * @param dst 目标对象
	 * @param src 源对象
	 */
	public static void copyWhenPropertyIsNotNull(final Object dst, Object src) {
		iterateFields(src, new IFieldsIteratorCallback() {
			@Override
			public void callback(Object object, Class<?> curClass, Field field)
					throws Exception {
				copyWhenPropertyIsNotNull(dst, object, field,curClass);
			}
		});
	}
	
	
	/**
	 * 当源属性为空的时候不复制
	 * 复制指定属性
	 * @param dst
	 * @param src
	 */
	public static void copyWhenPropertyIsNotNull(Object dst, Object src,Field field,Class<?> curClass){
		String setterMethodName=getSetterMethod(field.getName());
		//目标对象是否有注入方法
		boolean isDstObjectHasMethod=hasMethod(dst,setterMethodName,field.getType());
		if(!isDstObjectHasMethod){
			return;
		}
		
		Object fieldValue = getGetterMethodValue(src,field.getName());
		if(null!=fieldValue){
			setSetterMethodValue(dst, field, fieldValue);
		}
	}
	
	
	/**
	 * 拷贝属性，包括私有属性
	 * @param dst 目标对象
	 * @param src 源对象
	 */
	public static void copyPrivField(final Object dst,final Object src){
		if(null == src){
			throw new RuntimeException("源对象不能为空");
		}
		
		if(null == dst){
			throw new RuntimeException("目标对象不能为空");
		}
		
		String srcClass=src.getClass().getName();
		String dstClass=dst.getClass().getName();
		if(!srcClass.equals(dstClass)){
			throw new RuntimeException("源对象和目标对象必须是同一种类");
		}
		
		iterateFields(src, new IFieldsIteratorCallback() {
			@Override
			public void callback(Object object, Class<?> curClass, Field field)
					throws Exception {
				Object fieldValue = getValueFromField(field, src); 
				setValue2Field(field, dst, fieldValue);
			}
		});
	}
	
	/**
	 * 将值设置到属性域中
	 * @param fieldName 属性名
	 * @param object 对象
	 * @param value 值
	 */
	public static void setValue2Field(String fieldName,Object object,Object value){
		Field field=getFieldByName(object.getClass(), fieldName);
		if(null == field){
			return;
		}
		setValue2Field(field, object, value);
	}
	
	/**
	 * 将值设置到属性域中
	 * @param field 属性域
	 * @param object 对象
	 * @param value 值
	 */
	public static void setValue2Field(Field field,Object object,Object value){
		//属性域是否为final
		if(isFieldFinal(field)){
			return;
		}
		
		field.setAccessible(true); 
		try {
			field.set(object, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			field.setAccessible(false); 
		}
	}
	
	/**
	 * 从属性与获取值
	 * @param field 属性域
	 * @param object 对象
	 * @return 值
	 */
	public static Object getValueFromField(Field field,Object object){
		field.setAccessible(true); 
		try {
			return field.get(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			field.setAccessible(false); 
		}
	}
	
	/**
	 * 从属性与获取值
	 * @param fieldName 属性域名
	 * @param object 对象
	 * @return 值
	 */
	public static Object getValueFromField(String fieldName,Object object){
		Field field=getFieldByName(object.getClass(), fieldName);
		if(null == field){
			return null;
		}
		return getValueFromField(field, object);
	}
	
	/**
	 * 属性域是否为final
	 * @param field 属性域
	 * @return 是否为final
	 */
	public static boolean isFieldFinal(Field field){
		String s=Modifier.toString(field.getModifiers());
		if(-1!=s.indexOf("final")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 比较对象
	 * @param src 源对象
	 * @param dst 目标对象
	 */
	public static void compareObject(final Object src,final Object dst){
		compareObject(src, dst, false, null);
	}
	
	/**
	 * 比较对象
	 * @param src 源对象
	 * @param dst 目标对象
	 * @param isAllField 是否使用全部域比较:true是,false 只用非null的src中的域进行比较
	 * @param fieldList 对比域列表
	 */
	public static void compareObject(final Object src,final Object dst,final boolean isAllField,final 
			String [] fieldList){
		if(null == src || null == dst){
			ExceptionUtils.throwRuntimeException("入参对象不能为空");
		}
		
		iterateClassFields(src.getClass(), new IFieldsIteratorCallback() {
			
			@Override
			public void callback(Object object, Class<?> curClass, Field field)
					throws Exception {
				String fName=field.getName();
				if(!ValidateUtils.isArrEmpty(fieldList)){
					boolean isEq=false;
					for(String f:fieldList){
						if(f.equals(fName)){
							isEq=true;
							break;
						}
					}
					if(!isEq){
						return;
					}
				}
				
				Object srcVal=getValueFromField(field, src);
				Object dstVal=getValueFromField(field, dst);
				if(null == srcVal){
					if(!isAllField){
						return;
					}else{
						if(null!=dstVal){
							ExceptionUtils.throwRuntimeException("域:%s,值[%s]-[%s]不相等",fName,srcVal,dstVal);
						}
					}
				}else if(null == dstVal){
					ExceptionUtils.throwRuntimeException("域:%s,值[%s]-[%s]不相等",fName,srcVal,dstVal);
				}else {
					if(srcVal instanceof Date){
						String s1= DateUtils.format2StandardDate((Date)srcVal);
						String s2=DateUtils.format2StandardDate((Date)dstVal);
						if(!s1.equals(s2)){
							ExceptionUtils.throwRuntimeException("域:%s,值[%s]-[%s]不相等",fName,srcVal,dstVal);
						}
					}else{
						if(!srcVal.equals(dstVal)){
							ExceptionUtils.throwRuntimeException("域:%s,值[%s]-[%s]不相等",fName,srcVal,dstVal);
						}
					}
				}
			}
		});
	}
	

}
