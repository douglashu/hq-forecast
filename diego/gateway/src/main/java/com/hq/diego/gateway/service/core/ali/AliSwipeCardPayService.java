package com.hq.diego.gateway.service.core.ali;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.hq.diego.gateway.constant.config.GatewayConfig;
import com.hq.diego.model.ali.AliExtendParams;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.diego.gateway.httpclient.executor.AliServiceExecutor;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.diego.gateway.service.core.common.SwipeCardPayService;
import com.hq.diego.model.req.ali.AliSwipeCardPayReq;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;

/**
 * Created by zhaoyang on 26/11/2016.
 */
@Service("AliSwipeCardPayService")
public class AliSwipeCardPayService extends AliCommonService implements SwipeCardPayService {

    private static final Logger logger = Logger.getLogger(AliSwipeCardPayService.class);

    @Autowired
    private AliServiceExecutor aliExecutor;

    @Override
    public OrderTradeResp onSwipePay(OrderTradeReq tradeReq, ChannelRoute route) {
        final AliSwipeCardPayReq payReq = new AliSwipeCardPayReq();
        DateTime now = DateTime.now();
        payReq.setSeller_id(route.getMchId());
        payReq.setOperator_id(tradeReq.getOperatorId());
        payReq.setTerminal_id(tradeReq.getTerminalId());
        payReq.setAlipay_store_id(tradeReq.gettStoreId());
        payReq.setOut_trade_no(tradeReq.getOrderId());
        payReq.setScene("bar_code");
        payReq.setAuth_code(tradeReq.getAuthCode());
        payReq.setSubject(tradeReq.getTitle());
        payReq.setBody(tradeReq.getBody());
        payReq.setTotal_amount(getPrice(tradeReq.getTotalAmount()));
        payReq.setUndiscountable_amount(null);
        payReq.setGoods_detail(getGoodsDetail(tradeReq.getGoodsList()));
        payReq.setTimestamp(now.toString("YYYY-MM-dd HH:mm:ss"));
        payReq.setTimeout_express((GatewayConfig.ORDER_DEFAULT_EXPIRE_MINUTES + "m"));
        AliExtendParams extendParams = new AliExtendParams();
        // 系统商编号 该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
        extendParams.setSys_service_provider_id(GatewayConfig.ALIPAY_ISV_PID);
        payReq.setExtend_params(extendParams);

        final OrderTradeResp tradeResp = new OrderTradeResp();
        tradeResp.from(tradeReq);
        try {
            AlipayTradePayRequest request = new AlipayTradePayRequest();
            request.setBizContent(JSON.toJSONString(payReq));
            AlipayTradePayResponse response = this.aliExecutor
                    .execute(request, null, route.getKey());
            tradeResp.settOrderId(response.getTradeNo());
            tradeResp.settUserId(response.getBuyerUserId());
            tradeResp.settLoginId(response.getBuyerLogonId());
            BigDecimal totalAmt = toPrice(response.getTotalAmount());
            if (totalAmt != null) {
                tradeResp.setTotalAmount(totalAmt.intValue());
            }
            BigDecimal receiptAmt = toPrice(response.getReceiptAmount());
            if(receiptAmt != null) {
                tradeResp.setReceiptAmount(receiptAmt.intValue());
            } else {
                tradeResp.setReceiptAmount(tradeResp.getTotalAmount());
            }
            BigDecimal buyerPayAmt = toPrice(response.getBuyerPayAmount());
            if(buyerPayAmt != null) {
                tradeResp.setBuyerPayAmount(buyerPayAmt.intValue());
            } else {
                tradeResp.setBuyerPayAmount(tradeResp.getReceiptAmount());
            }
            if(!StringUtils.isEmpty(response.getGmtPayment())) {
                DateTime paymentTimestamp = new DateTime(response.getGmtPayment());
                tradeResp.setEndDate(paymentTimestamp.toString("YYYYMMdd"));
                tradeResp.setEndTime(paymentTimestamp.toString("HHmmss"));
            }
            if(response.getFundBillList() != null
                    && response.getFundBillList().size() > 0) {
                tradeResp.setFundBills(JSON.toJSONString(response.getFundBillList()));
            }
            if("10000".equals(response.getCode())) {
                // 记录交易结果并在客户端显示支付成功，进入后续的业务处理
                tradeResp.setTradeState(TradeState.SUCCESS);
            } else {
                if ("10003".equals(response.getCode())) {
                    // 发起轮询流程：等待5秒后调用交易查询接口alipay.trade.query
                    // 通过支付时传入的商户订单号(out_trade_no)查询支付结果（返回参数TRADE_STATUS）
                    // 如果仍然返回等待用户付款（WAIT_BUYER_PAY），则再次等待5秒后继续查询
                    // 直到返回确切的支付结果（成功TRADE_SUCCESS 或 已撤销关闭TRADE_CLOSED）
                    // 或是超出轮询时间。在最后一次查询仍然返回等待用户付款的情况下
                    // 必须立即调用交易撤销接口alipay.trade.cancel将这笔交易撤销，避免用户继续支付
                    tradeResp.setTradeState(TradeState.WAIT_PAY);
                } else {
                    String errCode = response.getCode();
                    if(!StringUtils.isEmpty(response.getSubCode())) {
                        errCode += ("(" + response.getSubCode() + ")");
                    }
                    tradeResp.setErrCode(errCode);
                    tradeResp.setErrCodeDes(response.getSubMsg());
                    if ("20000".equals(response.getCode())) {
                        // 未知异常,调用查询接口确认支付结果，进入异常处理流程
                        // 支付接口alipay.trade.core
                        // 立即调用查询接口：支付成功或等待用户付款	继续业务处理，履行订单或发起轮询
                        // 查询的交易不存在（错误码ACQ.TRADE_NOT_EXIST）	使用相同的参数重新调用支付接口
                        // 网络超时或未知异常	继续查询一分钟，如仍然超时或未知异常，需要记录该异常交易并走人工处理流程，不能简单的推断为付款失败。
                        // 查询接口aipay.trade.query 和 撤销接口aipay.trade.cancel
                        // 立即重试一分钟，如果仍然返回超时或未知异常，需要记录该异常交易并走人工处理流程。
                        // 预下单接口alipay.trade.precreate
                        // 使用新的商户订单号out_trade_no重新调用预下单接口。
                        logger.warn("<<< Ali Swipe Pay UnKnow(ErrCode="
                                + errCode + ", ErrCodeDes=" + response.getSubMsg() + ")");
                        tradeResp.setTradeState(TradeState.UNKNOW);
                    } else {
                        // 支付失败, 记录交易结果并在客户端显示错误信息（display_message)
                        logger.warn("<<< Ali Swipe Pay Fail(ErrCode="
                                + errCode + ", ErrCodeDes=" + response.getSubMsg() + ")");
                        tradeResp.setTradeState(TradeState.FAIL);
                    }
                }
            }
        } catch (AlipayApiException ae) {
            logger.warn("<<< Ali Swipe Pay Api Error", ae);
            tradeResp.setTradeState(TradeState.FAIL);
            tradeResp.setErrCode(ae.getErrCode());
            tradeResp.setErrCodeDes(ae.getErrMsg());
        } catch (Throwable th) {
            logger.warn("<<< Ali Swipe Pay UnKnow", th);
            tradeResp.setTradeState(TradeState.UNKNOW);
            tradeResp.setErrCode(CommonErrCode.UNKNOW_ERROR.getCode());
            tradeResp.setErrCodeDes(CommonErrCode.UNKNOW_ERROR.getDesc());
        }
        return tradeResp;
    }

}
