package com.hq.scrati.common.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
/**
 * 异常工具类
 * @Author Zale
 * @Date 2016/11/28 下午7:26
 *
 */
public class ExceptionUtils {

	public interface IExceptionIterator {

		/**
		 * 当前异常结构
		 * @param throwable 当前异常
		 * @return 是否继续遍历
		 */
		boolean iterate(Throwable throwable);
	}
	
	/**
	 * 获取关注的堆信息
	 * @param e 异常
	 * @param key 关键字
	 * @return
	 */
	public static StackTraceElement getFocusStackTraceElement(Exception e,String key){
		if(null == e){
			return null;
		}
		StackTraceElement [] eleArr= e.getStackTrace();
		if(ValidateUtils.isArrEmpty(eleArr)){
			return null;
		}
		
		for(StackTraceElement ste:eleArr){
			if(-1!=ste.getClassName().indexOf(key)){
				return ste;
			}
		}
		
		return null;
	}
	
	/**
	 * 获取关注的堆信息字符串
	 * @param e 异常
	 * @param key 关键字
	 * @return
	 */
	public static String getFocusStackStr(Exception e,String key){
		StackTraceElement ste=getFocusStackTraceElement(e, key);
		if(null == ste){
			return null;
		}
		return ste.toString();
	}
	
	
	/**
	 * 遍历异常链条
	 * @param throwable 异常
	 * @param iterator 遍历器
	 */
    public static void iterateThrowable(Throwable throwable,IExceptionIterator iterator){
    	if(null == throwable){
    		return;
    	}
    	
    	//执行回调函数，如果不继续则终端遍历
    	boolean ret=iterator.iterate(throwable);
    	if(!ret){
    		return;
    	}
    	
    	iterateThrowable(throwable.getCause(), iterator);
    }
	
	/**
     * 获取异常列表
     * @param throwable 异常
     * @return 异常列表
     */
    public static List<Throwable> getExpList(Throwable throwable){
    	final List<Throwable> list=new ArrayList<Throwable>();
    	
    	ExceptionUtils.iterateThrowable(throwable, new IExceptionIterator() {
			
			@Override
			public boolean iterate(Throwable throwable) {
				list.add(throwable);
				return true;
			}
		});
    	
    	return list;
    }
	
	/**
	 * 抛出运行时异常
	 * @param msg 传入消息
	 */
	public static void throwRuntimeException(String msg){
		throw newRuntimeException(msg);
	}
	
	/**
	 * 抛出运行时异常
	 * @param e 传入异常
	 */
	public static void throwRuntimeException(Exception e){
		throw newRuntimeException(e);
	}
	
	/**
	 * 抛出运行时异常
	 * @param template 模板
	 * @param args 参数
	 */
	public static void throwRuntimeException(String template,Object ...args){
		throwRuntimeException(String.format(template, args));
	}
	
	/**
	 * 创建运行时异常
	 * @param template 模板
	 * @param args 参数
	 * @return 运行时异常
	 */
	public static RuntimeException newRuntimeException(String template,Object ...args){
		return new RuntimeException(String.format(template, args));
	}
	
	/**
	 * 创建运行时异常
	 * @param msg 消息
	 * @return 运行时异常
	 */
	public static RuntimeException newRuntimeException(String msg){
		return new RuntimeException(msg);
	}
	
	/**
	 * 创建运行时异常
	 * @param parentExp 父异常
	 * @return 运行时异常
	 */
	public static RuntimeException newRuntimeException(Exception parentExp){
		return new RuntimeException(parentExp);
	}
	
	//默认的异常字符编码
	//public static final String DEFAULT_EXP_STR_ENCODING="utf-8";
	
	/**
	 * 获取异常字符串
	 * @param e 异常
	 * @return 异常字符串
	 */
	public static String getExpStr(Throwable e){
		if(null == e){
			return null;
		}
		ByteArrayOutputStream bou=new ByteArrayOutputStream(1024); 
	    PrintStream printStream=new PrintStream(bou,true); 
		e.printStackTrace(printStream);
		return new String(bou.toByteArray());
	}
	
	//页面换行符
	public static final String WEB_LINE_SPLITOR="<br/>";
	/**
	 * 获取网页模式异常字符串
	 * @param e 异常
	 * @return 异常字符串
	 */
	public static String getExpWebStr(Throwable e){
		return getExpStr(e,WEB_LINE_SPLITOR);
	}
	
	/**
	 * 获取异常字符串
	 * @param e 异常
	 * @param wrapSymbol 换行符号
	 * @return 异常字符串
	 */
	public static String getExpStr(Throwable e,String wrapSymbol){
		String expMsg=getExpStr(e);
		expMsg=expMsg.replaceAll("\r\n", wrapSymbol);
		return expMsg;
	}
	
	/**
	 * 获取异常翻转顺序的字符串
	 * @param e 异常
	 * @param wrapSymbol 换行符
	 * @return 异常字符串
	 */
	public static String getReverseExpStr(Throwable e,String wrapSymbol){
		List<Throwable> expList=getExpList(e);
		StringBuffer msg=new StringBuffer(1024);
		for(int i=expList.size()-1;i>=0;i++){
			Throwable th=expList.get(i);
			StackTraceElement [] sElements= th.getStackTrace();
			msg.append(th.getClass().getName());
			msg.append(":");
			msg.append(th.getMessage());
			msg.append(wrapSymbol);
			for(StackTraceElement s:sElements){
				msg.append(s);
				msg.append(wrapSymbol);
			}
		}
		return msg.toString();
	}
	
	/**
	 * 获取异常翻转顺序的字符串
	 * @param e 异常
	 * @return 异常字符串
	 */
	public static String getReverseExpStr(Throwable e){
		return getReverseExpStr(e, "");
	}
	
	/**
	 * 获取web异常翻转顺序的字符串
	 * @param e 异常
	 * @return 异常字符串
	 */
	public static String getReverseExpWebStr(Throwable e){
		return getReverseExpStr(e,WEB_LINE_SPLITOR);
	}
}	
