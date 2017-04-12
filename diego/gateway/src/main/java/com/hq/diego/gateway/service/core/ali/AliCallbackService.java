package com.hq.diego.gateway.service.core.ali;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.hq.diego.gateway.constant.config.GatewayConfig;
import com.hq.diego.gateway.service.core.PayResultNotifier;
import com.hq.diego.gateway.service.core.common.TradeNotifyService;
import com.hq.diego.gateway.service.persist.trade.TradeOrderQueryService;
import com.hq.diego.gateway.service.persist.trade.TradeOrderService;
import com.hq.diego.repository.model.generate.TDiegoTradeOrder;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.scrati.common.util.UrlParamUtil;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyang on 30/01/2017.
 */
@Service("AliCallbackService")
public class AliCallbackService implements TradeNotifyService {

    private static final Logger logger = Logger.getLogger(AliCallbackService.class);

    @Autowired
    private TradeOrderService tradeOrderService;

    @Autowired
    private TradeOrderQueryService tradeOrderQueryService;

    @Autowired
    private PayResultNotifier notifier;

    @Override
    public String onCallback(String bizContent) {
        JSONObject jsonObj = null;
        try {
            jsonObj = JSON.parseObject(bizContent);
        } catch (Throwable th) {
            logger.warn("<<<<<< Alipay On Callback Fail, json:", th);
            logger.warn(bizContent);
        }
        if (jsonObj == null) return "param_error";
        // 检查必要参数
        if (!jsonObj.containsKey("sign_type") ||
                !jsonObj.containsKey("sign") ||
                !jsonObj.containsKey("notify_type") ||
                !jsonObj.containsKey("trade_status") ||
                !jsonObj.containsKey("auth_app_id") ||
                !jsonObj.containsKey("out_trade_no") ||
                !jsonObj.containsKey("trade_no") ||
                !jsonObj.containsKey("total_amount") ||
                !jsonObj.containsKey("seller_id")) {
            return "param_error";
        }
        if (!"trade_status_sync".equals(jsonObj.getString("notify_type"))) {
            return "notify_type_error";
        }
        /*
        TRADE_FINISHED	交易完成	false（不触发通知）
        TRADE_SUCCESS	支付成功	true （触发通知）
        WAIT_BUYER_PAY	交易创建	false（不触发通知）
        TRADE_CLOSED	交易关闭	false（不触发通知）
         */
        if (!"TRADE_SUCCESS".equals(jsonObj.getString("trade_status")) &&
                !"TRADE_FINISHED".equals(jsonObj.getString("trade_status"))) {
            logger.warn("<<<<<< Alipay On Callback Trade Status Ignored(tradeStatus=" + jsonObj.getString("trade_status") + ")");
            return "success";
        }
        if (!"RSA2".equals(jsonObj.getString("sign_type"))) {
            return "sign_type_error";
        }
        TDiegoTradeOrder tradeOrder = this.tradeOrderQueryService
                .findByOrderId(jsonObj.getString("out_trade_no"));
        if (tradeOrder == null) {
            return "order_not_exists";
        }
        if (!tradeOrder.gettMchId().equals(jsonObj.getString("seller_id"))) {
            return "seller_id_error";
        }
        if (tradeOrder.getTotalAmount().intValue() !=
                jsonObj.getBigDecimal("total_amount").multiply(BigDecimal.valueOf(100)).intValue()) {
            return "total_amount_error";
        }
        if (!tradeOrder.getTradeState().equals(TradeState.PRE_CREATE)
                && !tradeOrder.getTradeState().equals(TradeState.WAIT_PAY)) {
            logger.warn("<<<<<< Ali Callback On State Error(orderId=" + getString(jsonObj.getString("out_trade_no"))
                    + ", tOrderId=" + getString(jsonObj.getString("trade_no")) + ", tradeState=" + tradeOrder.getTradeState()
                    + ", jsonStr=" + bizContent + ")");
            // 通知支付宝回调成功, 本地不处理此状态
            return "success";
        }
        String endDate;
        String endTime;
        if (jsonObj.containsKey("gmt_payment")) {
            DateTime paymentTimestamp = new DateTime(jsonObj.getDate("gmt_payment"));
            endDate = paymentTimestamp.toString("YYYYMMdd");
            endTime = paymentTimestamp.toString("HHmmss");
        } else {
            DateTime now = DateTime.now();
            endDate = now.toString("YYYYMMdd");
            endTime = now.toString("HHmmss");
        }
        // 验签
        boolean signValid = false;
        try {
            Map<String, Object> signParams = new HashMap<>();
            for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
                if ("sign".equals(entry.getKey()) ||
                        "sign_type".equals(entry.getKey())) {
                    continue;
                }
                signParams.put(entry.getKey(), URLDecoder.decode((String) entry.getValue(), "UTF-8"));
            }
            String signStr = UrlParamUtil.createLinkString(signParams);
            signValid = AlipaySignature.rsa256CheckContent(
                    signStr, jsonObj.getString("sign"), GatewayConfig.ALIPAY_PKCS8_PUBLIC_KEY, "UTF-8");
        } catch (Throwable th) {
            logger.warn("<<<<<< Alipay On Callback Fail, On Sign Error", th);
        }
        if (!signValid) {
            return "sign_error";
        }
        // 持久化到数据库
        try {
            this.tradeOrderService.confirmTradeOrder(
                    jsonObj.getString("out_trade_no"),
                    jsonObj.getString("trade_no"),
                    getCentAmount(jsonObj.getBigDecimal("total_amount")),
                    getCentAmount(jsonObj.getBigDecimal("receipt_amount")),
                    getCentAmount(jsonObj.getBigDecimal("buyer_pay_amount")),
                    jsonObj.getString("buyer_id"),
                    jsonObj.getString("buyer_logon_id"),
                    null, null, null, null, endDate, endTime, null,
                    jsonObj.getString("fund_bill_list"),
                    tradeOrder.getRate()
            );
            this.notifier.notifyPayResult(tradeOrder.getMchId(), Integer.valueOf(tradeOrder.getOperatorId())
                    , tradeOrder.getOperatorName(), tradeOrder.getOrderId(), tradeOrder.getPayChannel()
                    , tradeOrder.getTradeType(), tradeOrder.getTotalAmount());
            return "success";
        } catch (Throwable th) {
            logger.warn("<<<<<< Ali Callback Save DB On Fail(orderId=" + getString(jsonObj.getString("out_trade_no"))
                    + ", tOrderId=" + getString(jsonObj.getString("trade_no")) + ")", th);
            return "system_error";
        }
    }

    private static Long getCentAmount(BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(100)).longValue();
    }

    private static String getString(String str) {
        if(StringUtils.isEmpty(str)) return "";
        return str;
    }
}
