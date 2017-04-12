package com.hq.scrati.common.log;

import java.util.UUID;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;

public class Logger{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9124300182732396533L;

	public ThreadLocal<String> threadLocalLogId = new ThreadLocal<String>();

	private final org.apache.log4j.Logger logger;

	final static String FQCN = Logger.class.getName(); 
	
	private Logger(Class<?> clazz) {
		logger = org.apache.log4j.Logger.getLogger(clazz);
	}

	private Logger() {
		logger = org.apache.log4j.Logger.getRootLogger();
		
	}

	public static Logger getLogger() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		return new Logger(sts[2].getClass());
	}
	
	public static Logger getLogger(Class<?> clazz) {
		return new Logger(clazz);
	}

	public static Logger getRootLogger() {
		return new Logger();
	}

	
	public String getLogId() {
		String logId = threadLocalLogId.get();
		if(null == logId||"".equals(logId)){
			logId = RpcContext.getContext().getAttachment("logId");
		}
		if(null == logId||"".equals(logId)){
			logId = UUID.randomUUID().toString();
		}
		threadLocalLogId.set(logId);
		return logId;
	}

	/**
	 * debug日志输出
	 * @param message
	 */
	public void debug(Object message) {
		if (logger.isDebugEnabled()) {
			if(message instanceof java.lang.String){
				logger.debug(getLogId()+":"+message);
			}else{
				logger.debug(getLogId()+":"+JSON.toJSONString(message));
			}
		}
	}
	
	/**
	 * 带异常的日志输出
	 * @param message
	 * @param t
	 */
	public void debug(Object message,Throwable t) {
		if (logger.isDebugEnabled()) {
			if(message instanceof java.lang.String){
				logger.debug(getLogId()+":"+message,t);
			}else{
				logger.debug( getLogId()+":"+JSON.toJSONString(message),t);
			}
		}
	}
	
	/**
	 * 对日志带格式化输出
	 * @param format
	 * @param args
	 */
	public void debugFormat(String format, Object... args){
		String msg = String.format(format, args);
		debug(msg);
	}
	
	/**
	 * 带异常的日志格式化输出
	 * @param t
	 * @param format
	 * @param args
	 */
	
	public void debugFormat(Throwable t,String format, Object... args){
		String msg = String.format(format, args);
		debug(msg,t);
	}	
	
	/**
	 * info日志输出
	 * @param message
	 */
	public void info(Object message) {
		if (logger.isInfoEnabled()) {
			if(message instanceof java.lang.String){
				logger.info(getLogId()+":"+message);
			}else{
				logger.info(getLogId()+":"+JSON.toJSONString(message));
			}
		}
	}
	
	/**
	 * 带异常的info日志输出
	 * @param message
	 * @param t
	 */
	public void info(Object message,Throwable t) {
		if (logger.isInfoEnabled()) {
			if(message instanceof java.lang.String){
				logger.info(getLogId()+":"+message,t);
			}else{
				logger.info( getLogId()+":"+JSON.toJSONString(message),t);
			}
		}
	}
	
	/**
	 * 对info日志带格式化输出
	 * @param format
	 * @param args
	 */
	public void infoFormat(String format, Object... args){
		String msg = String.format(format, args);
		info(msg);
	}
	
	/**
	 * 带异常的info日志格式化输出
	 * @param t
	 * @param format
	 * @param args
	 */
	
	public void infoFormat(Throwable t,String format, Object... args){
		String msg = String.format(format, args);
		info(msg,t);
	}
	
	/**
	 * warn日志输出
	 * @param message
	 */
	public void warn(Object message) {		
		if(message instanceof java.lang.String){
			logger.warn(getLogId()+":"+message);
		}else{
			logger.warn(getLogId()+":"+JSON.toJSONString(message));
		}	
	}
	
	/**
	 * 带异常的warn日志输出
	 * @param message
	 * @param t
	 */
	public void warn(Object message,Throwable t) {
		if(message instanceof java.lang.String){
			logger.warn(getLogId()+":"+message,t);
		}else{
			logger.warn( getLogId()+":"+JSON.toJSONString(message),t);
		}
	}
	
	/**
	 * 对warn日志带格式化输出
	 * @param format
	 * @param args
	 */
	public void warnFormat(String format, Object... args){
		String msg = String.format(format, args);
		warn(msg);
	}
	
	/**
	 * 带异常的warn日志格式化输出
	 * @param t
	 * @param format
	 * @param args
	 */
	
	public void warnFormat(Throwable t,String format, Object... args){
		String msg = String.format(format, args);
		warn(msg,t);
	}
	
	/**
	 * error日志输出
	 * @param message
	 */
	public void error(Object message) {	
		if(message instanceof java.lang.String){
			logger.error(getLogId()+":"+message);
		}else{
			logger.error(getLogId()+":"+JSON.toJSONString(message));
		}		
	}
	
	/**
	 * 带异常的error日志输出
	 * @param message
	 * @param t
	 */
	public void error(Object message,Throwable t) {	
		if(message instanceof java.lang.String){
			logger.error(getLogId()+":"+message,t);
		}else{
			logger.error(getLogId()+":"+JSON.toJSONString(message),t);
		}		
	}
	
	/**
	 * 对error日志带格式化输出
	 * @param format
	 * @param args
	 */
	public void errorFormat(String format, Object... args){
		String msg = String.format(format, args);
		error(msg);
	}
	
	/**
	 * 带异常的error日志格式化输出
	 * @param t
	 * @param format
	 * @param args
	 */
	
	public void errorFormat(Throwable t,String format, Object... args){
		String msg = String.format(format, args);
		error(msg,t);
	}
	
	/**
	 * fatal日志输出
	 * @param message
	 */
	public void fatal(Object message) {
		if(message instanceof java.lang.String){
			logger.fatal( getLogId()+":"+message);
		}else{
			logger.fatal( getLogId()+":"+JSON.toJSONString(message));
		}
	}
	
	/**
	 * 带异常的fatal日志输出
	 * @param message
	 * @param t
	 */
	public void fatal(Object message,Throwable t) {
		if(message instanceof java.lang.String){
			logger.fatal(  getLogId()+":"+message,t);
		}else{
			logger.fatal( getLogId()+":"+JSON.toJSONString(message),t);
		}
	}
	
	/**
	 * 对fatal日志带格式化输出
	 * @param format
	 * @param args
	 */
	public void fatalFormat(String format, Object... args){
		String msg = String.format(format, args);
		fatal(msg);
	}
	
	/**
	 * 带异常的fatal日志格式化输出
	 * @param t
	 * @param format
	 * @param args
	 */
	
	public void fatalFormat(Throwable t,String format, Object... args){
		String msg = String.format(format, args);
		fatal(msg,t);
	}
	/**
	 * debug级别日志是否可用
	 * @return
	 */
	public Boolean isDebugEnabled(){
		return logger.isDebugEnabled();
	}
	
	/**
	 * Info级别日志是否可用
	 * @return
	 */
	public Boolean isInfoEnabled(){
		return logger.isInfoEnabled();
	}
		


}
