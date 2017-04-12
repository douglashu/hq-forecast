package com.hq.scrati.common.util.enu;

import java.util.ArrayList;
import java.util.List;

import com.hq.scrati.common.util.ValidateUtils;

/**
 * 枚举工具
 * @author Karma
 */
public class EnumUtils {
	
	/**
	 * 获取枚举描述的字符串
	 * @param baseEnum 基础枚举
	 * @return 枚举值描述字符串
	 */
	public static String getEnumDesp(BaseEnum<?> baseEnum){
		if(null == baseEnum){
			return null;
		}
		return baseEnum.getDesp();
	}
	
	/**
	 * 获取枚举列表
	 * @param type 类对象
	 * @return 枚举对象列表
	 */
	public static List<EnumObject> getEnumList(Class<? extends BaseEnum<?>> type){
		BaseEnum<?> [] enums=type.getEnumConstants();
		if(ValidateUtils.isArrEmpty(enums)){
			return null;
		}
		List<EnumObject> enumList=new ArrayList<EnumObject>(enums.length);
        for(BaseEnum<?> status :enums) {
        	enumList.add(new EnumObject(status));
        }
		return enumList;
	}
	
	/**
	 * 值和枚举是否相等
	 * @param value 值
	 * @param base 枚举2
	 * @return 是否相等
	 */
	public static boolean isValueEqEnum(Object value,BaseEnum<?> base){
		if(null == value || null == base || null == base.getValue()){
			return false;
		}
		
		if(value.equals(base.getValue())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 枚举是否相等
	 * @param base1 枚举1
	 * @param base2 枚举2
	 * @return 是否相等
	 */
	public static boolean isEq(BaseEnum<?> base1,BaseEnum<?> base2){
		if(null == base1 || null == base2){
			return false;
		}
		
		if(null == base1.getValue() || null == base2.getValue()){
			return false;
		}
		
		if(base1.getValue().equals(base2.getValue())){
			return true;
		}else{
			return false;
		}
	}
	
	 /**
     * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
     * @param code 数据库中存储的自定义code属性
     * @param type 枚举类
     * @return code对应的枚举类
     */
    public static BaseEnum<?> locateEnumStatus(Object code,Class<? extends BaseEnum<?>> type) {
    	if(null == code){
    		return null;
    	}
    	
    	BaseEnum<?> [] enumsArr=type.getEnumConstants();
    	if(ValidateUtils.isArrEmpty(enumsArr)){
    		return null;
    	}
    	
        for(BaseEnum<?> status :enumsArr) {
        	if(null == status.getValue()){
        		continue;
        	}
        	
            if(status.getValue().equals(code)) {
                return status;
            }
        }
        return null;
    }
	
}
