package com.hq.scrati.common.log;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class JobLogger {

	/**
	 *
	 */
	private static final long serialVersionUID = -9124300182732396533L;

	public ThreadLocal<String> threadLocalLogId = new ThreadLocal<String>();

	private final org.apache.log4j.Logger logger;

	final static String FQCN = JobLogger.class.getName();

	private static Queue<LogModel> logQueue = new ConcurrentLinkedQueue<>();

	private JobLogger(Class<?> clazz) {
		logger = org.apache.log4j.Logger.getLogger(clazz);
	}

	private JobLogger() {
		logger = org.apache.log4j.Logger.getRootLogger();

	}

	public static JobLogger getLogger() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		return new JobLogger(sts[2].getClass());
	}

	public static JobLogger getLogger(Class<?> clazz) {
		return new JobLogger(clazz);
	}

	public static JobLogger getRootLogger() {
		return new JobLogger();
	}


	public String getLogId() {
		String logId = threadLocalLogId.get();
		if(null == logId||"".equals(logId)){
			logId = RpcContext.getContext().getAttachment("joblogId");
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
			String logId = getLogId();
			if(message instanceof String){
				logger.debug(logId+":"+message);
				logQueue.offer(LogModel.build(logId,(String)message));
			}else{
				String messageStr = JSON.toJSONString(message);
				logger.debug(logId+":"+messageStr);
				logQueue.offer(LogModel.build(logId,messageStr));
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
			String logId = getLogId();
			if(message instanceof String){
				logger.debug(getLogId()+":"+message,t);
				logQueue.offer(LogModel.build(logId,(String)message,t));
			}else{
				String messageStr = JSON.toJSONString(message);
				logger.debug( getLogId()+":"+messageStr,t);
				logQueue.offer(LogModel.build(logId,messageStr,t));
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
			String logId = getLogId();
			if(message instanceof String){
				logger.info(getLogId()+":"+message);
				logQueue.offer(LogModel.build(logId,(String)message));
			}else{
				String messageStr = JSON.toJSONString(message);
				logger.info(getLogId()+":"+JSON.toJSONString(message));
				logQueue.offer(LogModel.build(logId,messageStr));
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
			String logId = getLogId();
			if(message instanceof String){
				logger.info(getLogId()+":"+message,t);
				logQueue.offer(LogModel.build(logId,(String)message,t));
			}else{
				String messageStr = JSON.toJSONString(message);
				logger.info( getLogId()+":"+JSON.toJSONString(message),t);
				logQueue.offer(LogModel.build(logId,messageStr,t));
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
		String logId = getLogId();
		if(message instanceof String){
			logger.warn(getLogId()+":"+message);
			logQueue.offer(LogModel.build(logId,(String)message));
		}else{
			String messageStr = JSON.toJSONString(message);
			logger.warn(getLogId()+":"+JSON.toJSONString(message));
			logQueue.offer(LogModel.build(logId,messageStr));
		}
	}

	/**
	 * 带异常的warn日志输出
	 * @param message
	 * @param t
	 */
	public void warn(Object message,Throwable t) {
		String logId = getLogId();
		if(message instanceof String){
			logger.warn(getLogId()+":"+message,t);
			logQueue.offer(LogModel.build(logId,(String)message,t));
		}else{
			String messageStr = JSON.toJSONString(message);
			logger.warn( getLogId()+":"+JSON.toJSONString(message),t);
			logQueue.offer(LogModel.build(logId,messageStr,t));
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
		String logId = getLogId();
		if(message instanceof String){
			logger.error(getLogId()+":"+message);
			logQueue.offer(LogModel.build(logId,(String)message));
		}else{
			String messageStr = JSON.toJSONString(message);
			logger.error(getLogId()+":"+JSON.toJSONString(message));
			logQueue.offer(LogModel.build(logId,messageStr));
		}
	}

	/**
	 * 带异常的error日志输出
	 * @param message
	 * @param t
	 */
	public void error(Object message,Throwable t) {
		String logId = getLogId();
		if(message instanceof String){
			logger.error(getLogId()+":"+message,t);
			logQueue.offer(LogModel.build(logId,(String)message,t));
		}else{
			String messageStr = JSON.toJSONString(message);
			logger.error(getLogId()+":"+JSON.toJSONString(message),t);
			logQueue.offer(LogModel.build(logId,messageStr,t));
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
		String logId = getLogId();
		if(message instanceof String){
			logger.fatal( getLogId()+":"+message);
			logQueue.offer(LogModel.build(logId,(String)message));
		}else{
			String messageStr = JSON.toJSONString(message);
			logger.fatal( getLogId()+":"+JSON.toJSONString(message));
			logQueue.offer(LogModel.build(logId,messageStr));
		}
	}

	/**
	 * 带异常的fatal日志输出
	 * @param message
	 * @param t
	 */
	public void fatal(Object message,Throwable t) {
		String logId = getLogId();
		if(message instanceof String){
			logger.fatal(  getLogId()+":"+message,t);
			logQueue.offer(LogModel.build(logId,(String)message,t));
		}else{
			String messageStr = JSON.toJSONString(message);
			logger.fatal( getLogId()+":"+JSON.toJSONString(message),t);
			logQueue.offer(LogModel.build(logId,messageStr,t));
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
