package com.hq.guan.service.callback;

import com.alibaba.fastjson.JSON;
import com.hq.diego.model.req.TradeNotifyReq;
import com.hq.guan.service.common.GuanCommonService;
import com.hq.scrati.model.HqRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 01/02/2017.
 */
@Service
public class AlipayCallbackService extends GuanCommonService {

    private static final Logger logger = Logger.getLogger(AlipayCallbackService.class);

    public String processAliCallback(String aliRespJson) {
        try {
            TradeNotifyReq tradeNotifyReq = new TradeNotifyReq();
            tradeNotifyReq.setService("AliCallbackService");
            tradeNotifyReq.setBizContent(aliRespJson);
            HqRequest request = new HqRequest();
            request.setBizContent(JSON.toJSONString(tradeNotifyReq));
            return invoke("diego.trade.confirm", "1.0", request, String.class);
        } catch (Throwable th) {
            logger.warn("<<<<<< Ali Callback On Fail:", th);
            logger.warn("<<<<<< Ali Resp Json:\r\n" + aliRespJson);
            return "system_error";
        }
    }

}
