package com.hq.scrati.common.exception;

/**
 * 业务异常类
 * @author zhouyang
 *
 */
public class BusinessException extends HqBaseException {

	/**
	 *
	 */
	private static final long serialVersionUID = 4872379062283135112L;

	/**
	 * 业务异常编码，按系统分配
	 */
	private String code;

	/**
	 * 异常msg
	 */
	private String msg;
	public BusinessException(){
		super(CommonErrCode.BUSINESS.getCode(),CommonErrCode.BUSINESS.getDesc());
		this.msg = CommonErrCode.BUSINESS.desc;
		this.code = CommonErrCode.BUSINESS.code;
	}

	public BusinessException(String msg) {
		super(CommonErrCode.BUSINESS.getCode(),msg);
		this.msg = msg;
	}

	public BusinessException(String code, String msg) {
		super(code,msg);
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


}
