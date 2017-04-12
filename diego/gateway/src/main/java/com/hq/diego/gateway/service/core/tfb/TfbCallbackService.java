package com.hq.diego.gateway.service.core.tfb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hq.diego.gateway.service.core.PayResultNotifier;
import com.hq.diego.gateway.service.core.common.TradeNotifyService;
import com.hq.diego.gateway.service.core.route.MchChannelService;
import com.hq.diego.gateway.service.persist.trade.TradeOrderQueryService;
import com.hq.diego.gateway.service.persist.trade.TradeOrderService;
import com.hq.diego.repository.model.generate.TDiegoTradeOrder;
import com.hq.diego.repository.model.generate.TRudyMchChannel;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.scrati.common.util.UrlParamUtil;
import com.hq.scrati.common.util.signature.MD5Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyang on 16/03/2017.
 */
@Service("TfbCallbackService")
public class TfbCallbackService implements TradeNotifyService {

    private static final Logger logger = Logger.getLogger(TfbCallbackService.class);

    @Autowired
    private TradeOrderService tradeOrderService;

    @Autowired
    private MchChannelService mchChannelService;

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
            logger.warn("<<<<<< Tfb Callback On Parse Json Fail, bizContent=", th);
            logger.warn(bizContent);
        }
        if (jsonObj == null) return "PARAM_ERROR";
        if (!jsonObj.containsKey("notify_type") ||
                !jsonObj.containsKey("pay_type")) {
            logger.warn("<<< Tfb Callback Fail, Missing params notify_type, pay_type");
            return "PARAM_ERROR";
        }
        if (!"1".equals(jsonObj.getString("notify_type"))) {
            logger.warn("<<< Tfb Callback Fail, notify_type=" + jsonObj.getString("notify_type"));
            return "PARAM_ERROR";
        }
        // 刷卡支付不需要回调处理
        if ("800208".equals(jsonObj.getString("pay_type"))) {
            logger.warn("<<< Tfb Callback On SwipeCardPay");
            return "SUCCESS";
        }
        // 检查必要参数
        if (!jsonObj.containsKey("sign_type") ||
                !jsonObj.containsKey("sign") ||
                !jsonObj.containsKey("sp_billno") ||
                !jsonObj.containsKey("listid") ||
                !jsonObj.containsKey("tran_state") ||
                !jsonObj.containsKey("tran_amt") ||
                !jsonObj.containsKey("tran_time") ||
                !jsonObj.containsKey("item_name") ||
                !jsonObj.containsKey("item_attach")) {
            logger.warn("<<< Tfb Callback Fail, Missing params");
            return "PARAM_ERROR";
        }
        TDiegoTradeOrder tradeOrder = this
                .tradeOrderQueryService.findByOrderId(jsonObj.getString("sp_billno"));
        if (tradeOrder == null) {
            return "ORDER_NOT_EXISTS";
        }
        if (tradeOrder.getTotalAmount().intValue() !=
                Integer.valueOf(jsonObj.getString("tran_amt"))) {
            return "TOTAL_AMOUNT_ERROR";
        }
        if (!tradeOrder.getTradeState().equals(TradeState.PRE_CREATE)
                && !tradeOrder.getTradeState().equals(TradeState.WAIT_PAY)) {
            logger.warn("<<<<<< Tfb Callback On State Error(orderId="
                    + getString(jsonObj.getString("sp_billno"))
                    + ", tOrderId=" + getString(jsonObj.getString("listid"))
                    + ", tradeState=" + tradeOrder.getTradeState()
                    + ", jsonStr=" + bizContent + ")");
            // 通知天付宝回调成功, 本地不处理此状态
            return "SUCCESS";
        }
        String endDate = null;
        String endTime = null;
        if (jsonObj.containsKey("tran_time")) {
            if (jsonObj.getString("tran_time").length() == 14) {
                String payTime = jsonObj.getString("tran_time");
                endDate = payTime.substring(0, 8);
                endTime = payTime.substring(8);
            } else {
                String[] payTime = jsonObj.getString("tran_time").split(" ");
                endDate = payTime[0].replaceAll("-", "");
                endTime = payTime[1].replaceAll(":", "");
            }
        }
        // 验签
        TRudyMchChannel mchChannel = this.mchChannelService
                .getMchChannel(tradeOrder.getMchId(), "TFB");
        if (mchChannel == null) {
            logger.warn("<<<<<< Query Mch(" + tradeOrder.getMchId() + ") TFB Channel Error");
            return "channel_error";
        }
        boolean signValid = false;
        try {
            Map<String, Object> signParams = new HashMap<>();
            for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
                if ("sign".equals(entry.getKey()) || "sign_type".equals(entry.getKey()) ||
                        "retcode".equals(entry.getKey()) || "retmsg".equals(entry.getKey()) ||
                        "ver".equals(entry.getKey()) || "input_charset".equals(entry.getKey()) ||
                        "sign_key_index".equals(entry.getKey())) {
                    continue;
                }
                signParams.put(entry.getKey(), URLDecoder.decode((String) entry.getValue(), "UTF-8"));
            }
            String signStr = genSignStr(signParams, "12345");
            signValid = jsonObj.getString("sign").equals(signStr);
        } catch (Throwable th) {
            logger.warn("<<<<<< Tfb On Callback Fail, On Sign Error", th);
        }
        if (!signValid) {
            // return "SIGN_ERROR";
        }
        // 持久化到数据库
        try {
            this.tradeOrderService.confirmTradeOrder(
                    jsonObj.getString("sp_billno"),
                    jsonObj.getString("listid"),
                    Long.valueOf(jsonObj.getString("tran_amt")),
                    null, null,
                    null, null, null, null, null, null,
                    endDate, endTime,
                    null, null,
                    tradeOrder.getRate()
            );
            this.notifier.notifyPayResult(tradeOrder.getMchId(), Integer.valueOf(tradeOrder.getOperatorId())
                    , tradeOrder.getOperatorName(), tradeOrder.getOrderId(), tradeOrder.getPayChannel()
                    , tradeOrder.getTradeType(), tradeOrder.getTotalAmount());
            return "SUCCESS";
        } catch (Throwable th) {
            logger.warn("<<<<<< Tfb Callback Save DB On Fail(orderId="
                    + getString(jsonObj.getString("sp_billno"))
                    + ", tOrderId=" + getString(jsonObj.getString("listid")) + ")", th);
            return "SYSTEM_ERROR";
        }
    }

    private static String genSignStr(Map<String, Object> params, String key) {
        List<String> emptyKeys = new ArrayList<>();
        for(String k: params.keySet()) {
            if("".equals(params.get(k))) emptyKeys.add(k);
        }
        for(String k: emptyKeys) params.remove(k);
        String signStr = UrlParamUtil.createLinkString(params);
        signStr += ("&key=" + key);
System.out.println(signStr);
        return MD5Util.MD5Encode(signStr, "UTF-8").toUpperCase();
    }

    private static String getString(String str) {
        if(StringUtils.isEmpty(str)) return "";
        return str;
    }

}
