package com.hq.guan.service.callback;

import com.alibaba.fastjson.JSON;
import com.hq.diego.model.req.TradeNotifyReq;
import com.hq.guan.service.common.GuanCommonService;
import com.hq.scrati.model.HqRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 16/03/2017.
 */
@Service
public class TfbCallbackService extends GuanCommonService {

    private static final Logger logger = Logger.getLogger(TfbCallbackService.class);

    public String processTfbCallback(String tfbRespJson) {
        try {
            TradeNotifyReq tradeNotifyReq = new TradeNotifyReq();
            tradeNotifyReq.setService("TfbCallbackService");
            tradeNotifyReq.setBizContent(tfbRespJson);
            HqRequest request = new HqRequest();
            request.setBizContent(JSON.toJSONString(tradeNotifyReq));
            return invoke("diego.trade.confirm", "1.0", request, String.class);
        } catch (Throwable th) {
            logger.warn("<<<<<< Tfb Callback On Fail:", th);
            logger.warn("<<<<<< Tfb Resp Json:\r\n" + tfbRespJson);
            return "SYSTEM_ERROR";
        }
    }

}
