package com.hq.brooke.service.common;

import com.alibaba.fastjson.JSONObject;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 01/01/2017.
 */
public class WxCommonService {

    public void throwExceptionWithJsonObj(JSONObject jsonObject) {
        StringBuilder sb = new StringBuilder();
        Integer errCode = jsonObject.getInteger("errcode");
        String errMsg = jsonObject.getString("errmsg");
        boolean hasMsgText = false;
        if(!StringUtils.isEmpty(errMsg)) {
            hasMsgText = true;
            sb.append(errMsg);
        }
        if(!StringUtils.isEmpty(errCode)) {
            if(hasMsgText) sb.append("(");
            sb.append(errCode);
            if(hasMsgText) sb.append(")");
        }
        throw new CommonException(CommonErrCode.BUSINESS, sb.toString());
    }

    public void throwExceptionWithStatusCode(int statusCode) {
        throw new CommonException(CommonErrCode.NETWORK_ERROR, "Http返回错误码(" + statusCode + ")");
    }

}
