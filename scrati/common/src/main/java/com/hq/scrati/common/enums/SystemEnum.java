package com.hq.scrati.common.enums;

/**
 * @包名称：com.hq.scrati.common.enums
 * @创建人：yyx
 * @创建时间：17/2/12 下午11:23
 */
public enum SystemEnum {

    APP("001","App应用"),
    OPERATION("002","运营后台");

    private String code;

    private String value;

    SystemEnum(String code,String value){
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
