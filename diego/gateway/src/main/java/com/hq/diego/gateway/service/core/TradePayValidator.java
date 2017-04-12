package com.hq.diego.gateway.service.core;

import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.constants.trade.TradeTypes;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.diego.model.req.OrderTradeReq;
import org.apache.commons.lang.StringUtils;
import java.util.regex.Pattern;

/**
 * Created by zhaoyang on 28/11/2016.
 */
public class TradePayValidator {

    private static final Pattern IPV4_REX_PATTERN =
            Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");

    private static final Integer MAX_AMOUNT_CENTS = 999999999;

    public void validate(OrderTradeReq tradeReq) {
        if(tradeReq == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        PayChannels.PayChannel payChannel = fromOrderTradeReq(tradeReq);
        if(payChannel == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "支付渠道不正确[payChannel]");
        }
        tradeReq.setPayChannel(payChannel.getCode());
        TradeTypes.TradeType tradeType = TradeTypes.fromString(tradeReq.getTradeType());
        if(tradeType == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "交易类型不正确[tradeType=" + tradeReq.getTradeType() + "]");
        }
        if(StringUtils.isBlank(tradeReq.getMchId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商户号不能为空[mchId]");
        }
        if(StringUtils.isBlank(tradeReq.getTerminalId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "终端号不能为空[terminalId]");
        }
        if(StringUtils.isBlank(tradeReq.getOperatorId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "操作员ID不能为空[operatorId]");
        }
        if(StringUtils.isBlank(tradeReq.getIpAddress())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "终端IP地址不能为空[ipAddress]");
        } else {
            if(!IPV4_REX_PATTERN.matcher(tradeReq.getIpAddress()).matches()) {
                throw new CommonException(
                        CommonErrCode.ARGS_INVALID, "终端IP地址格式不正确[ipAddress=" + tradeReq.getIpAddress() + "]");
            }
        }
        if(StringUtils.isBlank(tradeReq.getOrderId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商户订单号不能为空[outTradeNo]");
        }
        if(StringUtils.isBlank(tradeReq.getTitle())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商品标题不能为空[title]");
        }
        if(StringUtils.isBlank(tradeReq.getBody())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商品描述不能为空[body]");
        }
        if(StringUtils.isEmpty(tradeReq.getFeeType())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "货币类型不能为空[feeType]");
        }
        if(!"CNY".equals(tradeReq.getFeeType())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "暂不支持的货币类型[feeType=" + tradeReq.getFeeType() + "]");
        }
        if(tradeReq.getTotalAmount() == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "交易金额不能为空[totalAmount]");
        }
        if(tradeReq.getTotalAmount() < 1) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "交易金额格式错误[totalAmount=" + tradeReq.getTotalAmount() + "]");
        }
        if(tradeReq.getTotalAmount() > MAX_AMOUNT_CENTS) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "交易金额过大[totalAmount=" + tradeReq.getTotalAmount() + "]");
        }
        if(PayChannels.WEIXIN_PAY.equals(payChannel)) {
            if (TradeTypes.H5_JSAPI.equals(tradeType)) {
                if (StringUtils.isEmpty(tradeReq.getOpenId())
                        && StringUtils.isEmpty(tradeReq.getSubOpenId())) {
                    throw new CommonException(CommonErrCode.ARGS_INVALID, "openId或subOpenId不能同时为空");
                }
            }
        } else if(PayChannels.ALI_PAY.equals(payChannel)) {
            if (TradeTypes.H5_JSAPI.equals(tradeType)) {
                if (StringUtils.isEmpty(tradeReq.getBuyerId())) {
                    throw new CommonException(CommonErrCode.ARGS_INVALID, "buyerId不能为空");
                }
            }
        }
    }

    public static PayChannels.PayChannel fromOrderTradeReq(OrderTradeReq tradeReq) {
        if (tradeReq == null) return null;
        PayChannels.PayChannel payChannel = PayChannels.fromString(tradeReq.getPayChannel());
        if (payChannel != null) return payChannel;
        TradeTypes.TradeType tradeType = TradeTypes.fromString(tradeReq.getTradeType());
        if (!TradeTypes.SWIPE_CARD.equals(tradeType)) return null;
        return PayChannels.fromAuthCode(tradeReq.getAuthCode());
    }
}
