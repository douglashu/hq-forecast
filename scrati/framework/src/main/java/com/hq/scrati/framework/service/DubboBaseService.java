package com.hq.scrati.framework.service;

import com.alibaba.fastjson.JSON;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.model.HqRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Created by zhaoyang on 8/14/16.
 */
public class DubboBaseService {

    private static final Logger logger = Logger.getLogger(DubboBaseService.class);

    public static final String SUCCESS_CODE = "000000";
    public static final String DEFAULT_SUCCESS_MSG = "请求成功";

    public static final String DEFAULT_ERROR_CODE = "999999";
    public static final String DEFAULT_ERROR_MSG = "出了点小问题,请稍后再试吧";

    public <T> T parseRequest(HqRequest request, Class<T> clazz) {
        if(clazz == null) return null;
        T object = null;
        if(!StringUtils.isEmpty(request.getBizContent())) {
            try {
                if (clazz != null) {
                    object = JSON.parseObject(request.getBizContent(), clazz);
                }
            } catch (Throwable th) {
                logger.warn("<<<<<< Parse BizContent To Object Fail(" + request.getBizContent() + ")", th);
            }
        }
        if(object == null) {
            try {
                object = clazz.newInstance();
            } catch (Throwable th) {
                logger.warn("<<<<<< New Instance For Class Fail(" + clazz.toString() + ")", th);
            }
        }
        if(object != null && object instanceof HqRequest) {
            HqRequest hqRequest = (HqRequest) object;
            hqRequest.copyFrom(request);
        }
        return object;
    }

    public RespEntity getSuccessResp(Object resp) {
        return getSuccessResp(null, resp);
    }

    public RespEntity getSuccessResp(String msg, Object resp) {
        if(StringUtils.isEmpty(msg)) msg = DEFAULT_SUCCESS_MSG;
        if(resp == null) {
            return new RespEntity(SUCCESS_CODE, msg);
        }
        return new RespEntity(SUCCESS_CODE, msg, resp);
    }

    public RespEntity getErrorResp(Throwable th) {
        logger.warn("<<< Internal Server Error", th);
        RespEntity resp = new RespEntity();
        if(th instanceof CommonException) {
            CommonException ce = (CommonException) th;
            resp.setKey(ce.getErrCode());
            resp.setMsg(ce.getErrMsg());
            resp.setExt(ce.getExt());
        } else {
            resp.setKey(DEFAULT_ERROR_CODE);
            resp.setMsg(DEFAULT_ERROR_MSG);
        }
        return resp;
    }

}