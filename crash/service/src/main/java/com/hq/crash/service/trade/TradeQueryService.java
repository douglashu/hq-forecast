package com.hq.crash.service.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hq.crash.model.auth.UserSession;
import com.hq.crash.service.common.CrashCommonService;
import com.hq.diego.model.req.OrderQueryReq;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.MapBuilder;
import com.hq.scrati.model.HqRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by zhaoyang on 14/01/2017.
 */
@Service
public class TradeQueryService extends CrashCommonService {

    public JSONObject getTradeOrders(OrderQueryReq queryReq, UserSession userSession) {
        HqRequest request = getHqRequest(userSession);
        if(queryReq != null) {
            request.setBizContent(JSON.toJSONString(queryReq));
        }
        return invoke("diego.trade.query", "1.0", request, JSONObject.class);
    }

    public JSONObject getTradeOrder(String id, String type, UserSession userSession) {
        if(StringUtils.isEmpty(id)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "id不能为空[id]");
        }
        if(StringUtils.isEmpty(type)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "id类型不能为空[type]");
        }
        if(!"trade".equals(type) && !"order".equals(type) && !"third".equals(type)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "id类型错误[type]");
        }
        HqRequest request = getHqRequest(userSession);
        Map<String, Object> reqParams = MapBuilder.create("id", id).add("type", type).get();
        request.setBizContent(JSON.toJSONString(reqParams));
        return invoke("diego.trade.queryone", "1.0", request, JSONObject.class);
    }

}
