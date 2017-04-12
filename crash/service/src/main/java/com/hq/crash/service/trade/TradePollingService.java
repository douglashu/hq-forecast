package com.hq.crash.service.trade;

import com.alibaba.fastjson.JSON;
import com.hq.crash.model.auth.UserSession;
import com.hq.crash.service.common.CrashCommonService;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.model.HqRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyang on 14/01/2017.
 */
@Service
public class TradePollingService extends CrashCommonService {

    public OrderTradeResp pollingTradeOrder(String tradeId, UserSession userSession) {
        if(StringUtils.isEmpty(tradeId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "交易流水号不能为空[tradeId]");
        }
        HqRequest hqRequest = getHqRequest(userSession);
        Map<String, Object> bizMap = new HashMap<>();
        bizMap.put("tradeId", tradeId);
        hqRequest.setBizContent(JSON.toJSONString(bizMap));
        return invoke("diego.trade.polling", "1.0", hqRequest, OrderTradeResp.class);
    }

}
