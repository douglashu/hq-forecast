package com.hq.guan.service.callback;

import com.alibaba.fastjson.JSON;
import com.hq.diego.model.req.TradeNotifyReq;
import com.hq.guan.service.common.GuanCommonService;
import com.hq.scrati.model.HqRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 16/03/2017.
 */
@Service
public class CibCallbackService extends GuanCommonService {

    private static final Logger logger = Logger.getLogger(CibCallbackService.class);

    public String processCibCallback(String cibRespXml) {
        if(StringUtils.isEmpty(cibRespXml)) {
            return "param_error";
        }
        try {
            TradeNotifyReq tradeNotifyReq = new TradeNotifyReq();
            tradeNotifyReq.setService("CibCallbackService");
            tradeNotifyReq.setBizContent(cibRespXml);
            HqRequest request = new HqRequest();
            request.setBizContent(JSON.toJSONString(tradeNotifyReq));
            return invoke("diego.trade.confirm", "1.0", request, String.class);
        } catch (Throwable th) {
            logger.warn("<<<<<< Cib Callback On Fail:", th);
            logger.warn("<<<<<< Cib Resp Xml:\r\n" + cibRespXml);
            return "system_error";
        }
    }

}
