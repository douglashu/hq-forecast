package com.hq.scrati.common.util.enu;

/**
 * 枚举对象
 * 将基础枚举映射为基础对象
 * @author Karma
 */
public class EnumObject {
	//域值
	private Object value;
	//描述
	private String desp;
	//细节
	private String detail;
	
	public EnumObject(BaseEnum<?> base){
		this.value=base.getValue();
		this.desp=base.getDesp();
		this.detail=base.getDetail();
	}
	
	public EnumObject(Object value,String desp){
		this.value=value;
		this.desp=desp;
	}
	
	public EnumObject(Object value,String desp,String detail){
		this.value=value;
		this.desp=desp;
		this.detail=detail;
	}
	
	public EnumObject(){
	}
	
	public String toString(){
		return value+":"+desp;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
