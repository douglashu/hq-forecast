package com.hq.scrati.framework.rpc.helper;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.hq.esc.inf.entity.RespEntity;

public class RpcReturnHelper {

    public enum RpcResult{
        SUCCESS("0000", "成功"),
        CONVERT_JSON_ERROR("1000","JSON转换失败"),
        REQUEST_PARAM_ERROR("1001","请求参数错误"),
        OPER_URM_ID_UNKNOWN("2000","未知的操作员"),
        NODE_CREATE_TYPE_ERROR("3000","节点创建类型错误"),
        NODE_NOT_FOUND("3001","对应的节点不存在"),
        P_NODE_UNKNOWN("3002","未知上级"),
        SYSTEM_EXCEPTION("9999", "系统异常");

        private String key;
        private String value;
        private RpcResult(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

	public static <T> RespEntity<T> getSuccessRespEntity(T ext) {
		RespEntity<T> retVal = new RespEntity<>();
		retVal.setKey(RpcResult.SUCCESS.getKey());
		retVal.setMsg("成功");
		retVal.setExt(ext);
		return retVal;
	}

	public static <T> RespEntity<T> getSuccessRespEntity() {
		RespEntity<T> retVal = new RespEntity<>();
		retVal.setKey(RpcResult.SUCCESS.getKey());
		retVal.setMsg("成功");
		return retVal;
	}

	public static <T> RespEntity<T> getRespEntityParamError() {
		return getRespEntity(RpcResult.REQUEST_PARAM_ERROR);
	}

	public static <T> RespEntity<T> getRespEntityParamError(String message) {
		RespEntity<T> respEntity = getRespEntity(RpcResult.REQUEST_PARAM_ERROR);
		respEntity.setMsg(message);
		return respEntity;
	}

	public static <T> RespEntity<T> getFailRespEntity(String msg, T ext) {
		RespEntity<T> retVal = new RespEntity<>();
		retVal.setKey(RpcResult.SYSTEM_EXCEPTION.getKey());
		if (StringUtils.isBlank(msg)) {
			retVal.setMsg(RpcResult.SYSTEM_EXCEPTION.getValue());
		} else {
			retVal.setMsg(msg);
		}
		retVal.setExt(ext);
		return retVal;
	}

	public static <T> RespEntity<T> getFailRespEntity(String message) {
		RespEntity<T> retVal = new RespEntity<>();
		retVal.setKey(RpcResult.SYSTEM_EXCEPTION.getKey());
		retVal.setMsg(message);
		return retVal;
	}

	public static <T> RespEntity<T> getFailRespEntity() {
		RespEntity<T> retVal = new RespEntity<>();
		retVal.setKey(RpcResult.SYSTEM_EXCEPTION.getKey());
		retVal.setMsg(RpcResult.SYSTEM_EXCEPTION.getValue());
		return retVal;
	}

	public static <T> RespEntity<T> getRespEntity(RpcResult result) {
		RespEntity<T> retVal = new RespEntity<>();
		retVal.setKey(result.getKey());
		retVal.setMsg(result.getValue());
		return retVal;
	}

	public static <T> RespEntity<T> getRespEntity(RpcResult result, T ext) {
		RespEntity<T> retVal = new RespEntity<>();
		retVal.setKey(result.getKey());
		retVal.setMsg(result.getValue());
		retVal.setExt(ext);
		return retVal;
	}

}