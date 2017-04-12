package com.hq.diego.gateway.service.core.wx;

import com.alibaba.fastjson.JSON;
import com.hq.diego.gateway.common.XStreamUtil;
import com.hq.diego.gateway.constant.config.GatewayConfig;
import com.hq.diego.gateway.httpclient.executor.WxServiceExecutor;
import com.hq.diego.gateway.service.core.PayResultNotifier;
import com.hq.diego.gateway.service.core.common.TradeNotifyService;
import com.hq.diego.gateway.service.persist.trade.TradeOrderQueryService;
import com.hq.diego.gateway.service.persist.trade.TradeOrderService;
import com.hq.diego.model.resp.wx.WxCallbackResp;
import com.hq.diego.model.resp.wx.WxPayResp;
import com.hq.diego.repository.model.generate.TDiegoTradeOrder;
import com.hq.scrati.common.constants.trade.TradeState;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Map;

/**
 * Created by zhaoyang on 30/01/2017.
 */
@Service("WxCallbackService")
public class WxCallbackService implements TradeNotifyService {

    private static final Logger logger = Logger.getLogger(WxCallbackService.class);

    @Autowired
    private TradeOrderService tradeOrderService;

    @Autowired
    private TradeOrderQueryService tradeOrderQueryService;

    @Autowired
    private PayResultNotifier notifier;

    @Override
    public String onCallback(String bizContent) {
        WxCallbackResp wxCallbackResp = onWxCallback(bizContent);
        return XStreamUtil.toXml(wxCallbackResp);
    }

    private WxCallbackResp onWxCallback(String xmlStr) {
        WxPayResp wxPayResp;
        try {
            wxPayResp = XStreamUtil.fromXml(xmlStr, WxPayResp.class);
        } catch (Throwable th) {
            logger.error("<<<<<< Parse Wx Callback Resp Xml Fail", th);
            return new WxCallbackResp("FAIL", "PARAM_ERROR");
        }
        if (wxPayResp == null) return new WxCallbackResp("FAIL", "PARAM_ERROR");
        // 通讯结果
        if (!"SUCCESS".equals(wxPayResp.getReturn_code())) {
            return new WxCallbackResp("FAIL", "RETURN_CODE_ERROR");
        }
        // 业务结果
        if (!"SUCCESS".equals(wxPayResp.getResult_code())) {
            return new WxCallbackResp("FAIL", "RESULT_CODE_ERROR");
        }
        // 检查必要参数
        if (StringUtils.isEmpty(wxPayResp.getSign()) ||
                StringUtils.isEmpty(wxPayResp.getSub_mch_id()) ||
                StringUtils.isEmpty(wxPayResp.getOut_trade_no()) ||
                StringUtils.isEmpty(wxPayResp.getTransaction_id()) ||
                wxPayResp.getTotal_fee() == null) {
            return new WxCallbackResp("FAIL", "PARAM_ERROR");
        }
        TDiegoTradeOrder tradeOrder = this
                .tradeOrderQueryService.findByOrderId(wxPayResp.getOut_trade_no());
        if (tradeOrder == null) {
            return new WxCallbackResp("FAIL", "ORDER_NOT_EXISTS");
        }
        if (!tradeOrder.gettMchId().equals(wxPayResp.getSub_mch_id())) {
            return new WxCallbackResp("FAIL", "MCH_ID_ERROR");
        }
        if (tradeOrder.getTotalAmount().intValue() != wxPayResp.getTotal_fee()) {
            return new WxCallbackResp("FAIL", "TOTAL_FEE_ERROR");
        }
        if (!tradeOrder.getTradeState().equals(TradeState.PRE_CREATE)
                && !tradeOrder.getTradeState().equals(TradeState.WAIT_PAY)) {
            logger.warn("<<<<<< Wx Callback On State Error(orderId=" + getString(wxPayResp.getOut_trade_no())
                    + ", tOrderId=" + getString(wxPayResp.getTransaction_id()) + ", tradeState=" + tradeOrder.getTradeState()
                    + ", xmlStr=" + xmlStr + ")");
            // 通知微信回调成功, 本地不处理此状态
            return new WxCallbackResp("SUCCESS", "OK");
        }
        // 验签
        Map<String, Object> params = (Map<String, Object>) JSON.toJSON(wxPayResp);
        params.remove("sign");
        String signStr = WxServiceExecutor.genSignStr(params, GatewayConfig.WX_API_SECRET_KEY);
        if (!signStr.equals(wxPayResp.getSign())) {
            return new WxCallbackResp("FAIL", "SIGN_ERROR");
        }
        // 持久化到数据库
        try {
            this.tradeOrderService.confirmTradeOrder(
                    wxPayResp.getOut_trade_no(),
                    wxPayResp.getTransaction_id(),
                    (wxPayResp.getTotal_fee() != null ?
                            wxPayResp.getTotal_fee() * 1L : null),
                    (wxPayResp.getSettlement_total_fee() != null ?
                        wxPayResp.getSettlement_total_fee() * 1L : null),
                    (wxPayResp.getCash_fee() != null ?
                            wxPayResp.getCash_fee() * 1L : null),
                    null, null,
                    wxPayResp.getOpenid(), wxPayResp.getSub_openid(),
                    wxPayResp.getIs_subscribe(), wxPayResp.getSub_is_subscribe(),
                    wxPayResp.getTime_end().substring(0, 8), wxPayResp.getTime_end().substring(8),
                    wxPayResp.getBank_type(), null, tradeOrder.getRate());
            this.notifier.notifyPayResult(
                      tradeOrder.getMchId()
                    , Integer.valueOf(tradeOrder.getOperatorId())
                    , tradeOrder.getOperatorName()
                    , tradeOrder.getOrderId(), tradeOrder.getPayChannel()
                    , tradeOrder.getTradeType(), tradeOrder.getTotalAmount());
            return new WxCallbackResp("SUCCESS", "OK");
        } catch (Throwable th) {
            logger.warn("<<<<<< Wx Callback Save DB On Fail(orderId=" + getString(wxPayResp.getOut_trade_no())
                    + ", tOrderId=" + getString(wxPayResp.getTransaction_id()) + ")", th);
            return new WxCallbackResp("FAIL", "SYSTEM_ERROR");
        }
    }

    private static String getString(String str) {
        if(StringUtils.isEmpty(str)) return "";
        return str;
    }
}
