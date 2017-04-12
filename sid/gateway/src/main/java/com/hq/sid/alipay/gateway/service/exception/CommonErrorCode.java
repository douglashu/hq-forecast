package com.hq.sid.alipay.gateway.service.exception;

/**
 * @包名称：com.hq.sid.alipay.gateway.service.exception
 * @创建人：yyx
 * @创建时间：16/12/13 上午11:07
 */
public enum CommonErrorCode {

    // 商户入驻单查询接口业务错误码
    M_APPLY_ORDER_NOT_EXIST("M_APPLY_ORDER_NOT_EXIST","订单不存在"),
    THIS_ORDER_NOT_BELONG_TO_APPID("THIS_ORDER_NOT_BELONG_TO_APPID","该订单不属于该ISV"),
    INVALID_PARAMETER("INVALID_PARAMETER","参数有误。"),


    // 个体工商户入驻接口/企业级商户入驻接口 业务错误码
    SYSTEM_ERROR("SYSTEM_ERROR","系统繁忙"),
//    INVALID_PARAMETER("INVALID_PARAMETER","参数有误。"),
    INVALID_MCC_CODE("INVALID_MCC_CODE","不合法的MCC编码"),
    PRODUCT_LIST_EMPTY("PRODUCT_LIST_EMPTY","所需签约产品集为空"),
    CONTEXT_INCONSISTENT("CONTEXT_INCONSISTENT","入驻单信息被篡改"),

    // isv图片上传接口业务错误码
    EXCEED_MAX_SIZE("EXCEED_MAX_SIZE","超过单个图片允许的最大值 (5M)"),
    INVALID_IMAGE_FORMAT("INVALID_IMAGE_FORMAT","文件格式不支持"),
    IMAGE_UNSAFE("IMAGE_UNSAFE","文件未通过安全校验");


    String code;
    String desc;

    CommonErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }



}
