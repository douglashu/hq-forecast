package com.hq.scrati.common.exception;

public class InfException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public InfException() {
		super();
	}
	
	/**
	 * 自定义异常
	 * @param msg
	 */
	public InfException(String key,String msg,String requestId) {
	   super(key+"|"+msg+"|"+requestId);
	   
	}

	
}
