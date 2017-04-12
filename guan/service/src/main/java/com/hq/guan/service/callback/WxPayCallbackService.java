package com.hq.guan.service.callback;

import com.alibaba.fastjson.JSON;
import com.hq.diego.model.req.TradeNotifyReq;
import com.hq.diego.model.resp.wx.WxCallbackResp;
import com.hq.guan.service.common.GuanCommonService;
import com.hq.guan.service.util.XStreamUtil;
import com.hq.scrati.model.HqRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 26/01/2017.
 */
@Service
public class WxPayCallbackService extends GuanCommonService {

    private static final Logger logger = Logger.getLogger(WxPayCallbackService.class);

    public String processWxCallback(String wxRespXml) {
        if(StringUtils.isEmpty(wxRespXml)) {
            WxCallbackResp resp = new WxCallbackResp("FAIL", "PARAM_ERROR");
            return XStreamUtil.toXml(resp);
        }
        try {
            TradeNotifyReq tradeNotifyReq = new TradeNotifyReq();
            tradeNotifyReq.setService("WxCallbackService");
            tradeNotifyReq.setBizContent(wxRespXml);
            HqRequest request = new HqRequest();
            request.setBizContent(JSON.toJSONString(tradeNotifyReq));
            return invoke("diego.trade.confirm", "1.0", request, String.class);
        } catch (Throwable th) {
            logger.warn("<<<<<< Wx Callback On Fail:", th);
            logger.warn("<<<<<< Wx Resp Xml:\r\n" + wxRespXml);
            WxCallbackResp resp = new WxCallbackResp("FAIL", "SYSTEM_ERROR");
            return XStreamUtil.toXml(resp);
        }
    }

}
