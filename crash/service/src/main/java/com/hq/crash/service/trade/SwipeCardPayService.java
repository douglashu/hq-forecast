package com.hq.crash.service.trade;

import com.alibaba.fastjson.JSON;
import com.hq.crash.model.auth.UserSession;
import com.hq.crash.service.UnifyOrderService;
import com.hq.crash.service.common.CrashCommonService;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 14/01/2017.
 */
@Service
public class SwipeCardPayService extends CrashCommonService {

    @Autowired
    private UnifyOrderService unifyOrderService;

    public OrderTradeResp swipeCard(OrderTradeReq tradeReq, UserSession userSession) {
        if(tradeReq == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        if(tradeReq.getIpAddress() == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "终端IP地址不能为空[ipAddress]");
        }
        if(StringUtils.isEmpty(tradeReq.getTerminalId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "终端号不能为空[terminalId]");
        }
        if(tradeReq.getTotalAmount() == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "交易金额不能为空[totalAmount]");
        }
        if(tradeReq.getTotalAmount() <= 0) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "交易金额不正确[totalAmount=" + tradeReq.getTotalAmount() + "]");
        }
        tradeReq.setTerminalId("MBL@" + tradeReq.getTerminalId());
        if(StringUtils.isEmpty(tradeReq.getAttach())) tradeReq.setAttach("");
        tradeReq.setOperatorId(userSession.getUserId());
        tradeReq.setOperatorName(userSession.getUserName());
        tradeReq.setTradeType("SWIPE_CARD");
        PayChannels.PayChannel payChannel = PayChannels.fromAuthCode(tradeReq.getAuthCode());
        if(payChannel == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "客户付款码错误[authCode]");
        }
        tradeReq.setPayChannel(payChannel.getCode());
        if(StringUtils.isEmpty(tradeReq.getTitle())) {
            if(StringUtils.isEmpty(userSession.activeMchInfo().getStoreName())) {
                tradeReq.setTitle(userSession.activeMchInfo().getMchShortName());
            } else {
                tradeReq.setTitle(userSession.activeMchInfo().getMchShortName()
                        + "-" + userSession.activeMchInfo().getStoreName());
            }
        }
        if(StringUtils.isEmpty(tradeReq.getBody())) {
            tradeReq.setBody(tradeReq.getTitle());
        }
        tradeReq.setFeeType("CNY");
        tradeReq.setMchId(userSession.getActiveMch());
        tradeReq.setCustom(null);
        tradeReq.settStoreId(null);
        try {
            tradeReq.setOrderId(this.unifyOrderService.unifyOrder(tradeReq));
        } catch (Throwable th) {
            throw new CommonException(CommonErrCode.BUSINESS, "统一支付订单创建失败", th);
        }
        HqRequest hqRequest = getHqRequest(userSession);
        hqRequest.setBizContent(JSON.toJSONString(tradeReq));
        return invoke("diego.trade.pay", "1.0", hqRequest, OrderTradeResp.class);
    }

}
