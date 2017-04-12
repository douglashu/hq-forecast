package com.hq.scrati.framework.rpc.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.filter.ExceptionFilter;
import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.HqBaseException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.framework.rpc.helper.RpcReturnHelper;

/**
 * Created by Zale on 2016/12/22.
 */
@Activate(group = Constants.PROVIDER,order = -20001)
public class HqFilter extends ExceptionFilter {

    private Logger logger = Logger.getLogger();

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            Result result = invoker.invoke(invocation);
            String host = invoker.getUrl().getAddress();
            if (result.hasException() && GenericService.class != invoker.getInterface()) {
                try {
                    Throwable exception = result.getException();
                    String code;
                    String msg;
                        if (exception instanceof HqBaseException){
                            code = ((HqBaseException) exception).getErrCode();
                            msg = ((HqBaseException) exception).getErrMsg();
                        } else {
                            code = CommonErrCode.UNKNOW_ERROR.getCode();
                            msg = CommonErrCode.UNKNOW_ERROR.getDesc();
                        }
                    RespEntity<String> retVal = new RespEntity<String>();
                    retVal.setKey(code);
                    retVal.setMsg(msg);
                    retVal.setHost(host);
                    Result nResult = new RpcResult(retVal);

                    // 未在方法签名上定义的异常，在服务器端打印ERROR日志
                    logger.error(
                            "Got unchecked and undeclared exception which called by " + RpcContext.getContext().getRemoteHost()
                                    + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                                    + ", exception: " + exception.getClass().getName() + ": " + exception.getMessage(),
                            exception);

                    // 是Dubbo本身的异常，直接抛出
                    if (exception instanceof RpcException) {
                        return result;
                    }

                    // 否则，包装成RuntimeException抛给客户端
                    return new RpcResult(retVal);
                } catch (Throwable e) {
                    logger.warn(
                            "Fail to ExceptionFilter when called by " + RpcContext.getContext().getRemoteHost() + ". service: "
                                    + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                                    + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
                    return result;
                }
            }else if(!(result.getValue() instanceof  RespEntity) ){
                Object ext = result.getValue();
//                String ext = null;
//                if(!(obj instanceof String)){
//                    ext = JSON.toJSONString(obj);
//                }else{
//                    ext = (String)obj;
//                }
                RespEntity respEntity = RpcReturnHelper.getSuccessRespEntity(ext);
                respEntity.setHost(host);
               return new RpcResult(respEntity);
            }else{
                if(result.getValue() instanceof RespEntity) {
                    ((RespEntity) result.getValue()).setHost(host);
                }
            }
            return result;
        } catch (RuntimeException e) {
            logger.error("Got unchecked and undeclared exception which called by " + RpcContext.getContext().getRemoteHost()
                    + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                    + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
            throw e;
        }
    }
}
