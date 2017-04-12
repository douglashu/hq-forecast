package com.hq.diego.gateway.service.core.cib;

import com.alibaba.fastjson.JSON;
import com.hq.diego.gateway.common.XStreamUtil;
import com.hq.diego.gateway.httpclient.executor.CibServiceExecutor;
import com.hq.diego.gateway.service.core.PayResultNotifier;
import com.hq.diego.gateway.service.core.common.TradeNotifyService;
import com.hq.diego.gateway.service.core.route.MchChannelService;
import com.hq.diego.gateway.service.persist.trade.TradeOrderQueryService;
import com.hq.diego.gateway.service.persist.trade.TradeOrderService;
import com.hq.diego.model.resp.cib.CibPayResp;
import com.hq.diego.repository.model.generate.TDiegoTradeOrder;
import com.hq.diego.repository.model.generate.TRudyMchChannel;
import com.hq.scrati.common.constants.trade.TradeState;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by zhaoyang on 21/03/2017.
 */
@Service("CibCallbackService")
public class CibCallbackService implements TradeNotifyService {

    private static final Logger logger = Logger.getLogger(CibCallbackService.class);

    @Autowired
    private TradeOrderService tradeOrderService;

    @Autowired
    private TradeOrderQueryService tradeOrderQueryService;

    @Autowired
    private MchChannelService mchChannelService;

    @Autowired
    private PayResultNotifier notifier;

    @Override
    public String onCallback(String bizContent) {
        CibPayResp cibPayResp;
        try {
            cibPayResp = XStreamUtil.fromXml(bizContent, CibPayResp.class);
        } catch (Throwable th) {
            logger.error("<<<<<< Parse Cib Callback Resp Xml Fail", th);
            return "param_error";
        }
        if (cibPayResp == null) return "param_error";
        if (!"0".equals(cibPayResp.getStatus())) {
            return "status_error";
        }
        if (!"0".equals(cibPayResp.getResult_code())) {
            return "result_code_error";
        }
        if (0 != cibPayResp.getPay_result()) {
            return "pay_result_error";
        }
        // 检查必要参数
        if (StringUtils.isEmpty(cibPayResp.getSign()) ||
                StringUtils.isEmpty(cibPayResp.getMch_id()) ||
                StringUtils.isEmpty(cibPayResp.getOut_trade_no()) ||
                StringUtils.isEmpty(cibPayResp.getTransaction_id()) ||
                StringUtils.isEmpty(cibPayResp.getTrade_type()) ||
                cibPayResp.getTotal_fee() == null) {
            return "param_error";
        }
        if(StringUtils.isEmpty(cibPayResp.getTime_end())) {
            cibPayResp.setTime_end(DateTime.now().toString("YYYYMMddHHmmss"));
        }
        TDiegoTradeOrder tradeOrder = this
                .tradeOrderQueryService.findByOrderId(cibPayResp.getOut_trade_no());
        if (tradeOrder == null) {
            return "order_not_exists";
        }
        if (!tradeOrder.gettMchId().equals(cibPayResp.getMch_id())) {
            return "mch_id_error";
        }
        if (tradeOrder.getTotalAmount().intValue() != cibPayResp.getTotal_fee()) {
            return "total_fee_error";
        }
        if (!tradeOrder.getTradeState().equals(TradeState.PRE_CREATE)
                && !tradeOrder.getTradeState().equals(TradeState.WAIT_PAY)) {
            logger.warn("<<<<<< Cib Callback On State Error(orderId="
                    + getString(cibPayResp.getOut_trade_no())
                    + ", tOrderId=" + getString(cibPayResp.getTransaction_id())
                    + ", tradeState=" + tradeOrder.getTradeState()
                    + ", xmlStr=" + bizContent + ")");
            // 通知微信回调成功, 本地不处理此状态
            return "success";
        }
        // 验签
        TRudyMchChannel mchChannel = this.mchChannelService
                .getMchChannel(tradeOrder.getMchId(), "CIB");
        if (mchChannel == null) {
            logger.warn("<<<<<< Query Mch(" + tradeOrder.getMchId() + ") CIB Channel Error");
            return "channel_error";
        }
        Map<String, Object> params = (Map<String, Object>) JSON.toJSON(cibPayResp);
        params.remove("sign");
        String signStr = CibServiceExecutor.genSignStr(params, "9d101c97133837e13dde2d32a5054abb");
        if (!signStr.equals(cibPayResp.getSign())) {
            return "sign_error";
        }
        // 持久化到数据库
        try {
            Long receiptAmount = null;
            Long buyerPayAmount = null;
            String buyerId = null;
            if ("pay.alipay.native".equals(cibPayResp.getTrade_type())) {
                if (!StringUtils.isEmpty(cibPayResp.getReceipt_amount())) {
                    receiptAmount = Long.valueOf(cibPayResp.getReceipt_amount());
                }
                if (!StringUtils.isEmpty(cibPayResp.getBuyer_pay_amount())) {
                    buyerPayAmount = Long.valueOf(cibPayResp.getBuyer_pay_amount());
                }
                buyerId = cibPayResp.getOpenid();
            }
            this.tradeOrderService.confirmTradeOrder(
                    cibPayResp.getOut_trade_no(),
                    cibPayResp.getTransaction_id(),
                    (cibPayResp.getTotal_fee() != null ?
                            cibPayResp.getTotal_fee() * 1L : null),
                    receiptAmount, buyerPayAmount,
                    buyerId, null,
                    cibPayResp.getOpenid(), cibPayResp.getSub_openid(),
                    cibPayResp.getIs_subscribe(), cibPayResp.getSub_is_subscribe(),
                    cibPayResp.getTime_end().substring(0, 8), cibPayResp.getTime_end().substring(8),
                    cibPayResp.getBank_type(), null, tradeOrder.getRate());
            this.notifier.notifyPayResult(
                    tradeOrder.getMchId()
                    , Integer.valueOf(tradeOrder.getOperatorId())
                    , tradeOrder.getOperatorName()
                    , tradeOrder.getOrderId(), tradeOrder.getPayChannel()
                    , tradeOrder.getTradeType(), tradeOrder.getTotalAmount());
            return "success";
        } catch (Throwable th) {
            logger.warn("<<<<<< Cib Callback Save DB On Fail(orderId="
                    + getString(cibPayResp.getOut_trade_no()) + ", tOrderId="
                    + getString(cibPayResp.getTransaction_id()) + ")", th);
            return "system_error";
        }
    }

    private static String getString(String str) {
        if(StringUtils.isEmpty(str)) return "";
        return str;
    }
}
