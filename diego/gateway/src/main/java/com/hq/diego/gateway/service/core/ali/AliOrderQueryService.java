package com.hq.diego.gateway.service.core.ali;

import com.alibaba.fastjson.JSON;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.diego.gateway.httpclient.executor.AliServiceExecutor;
import com.hq.diego.gateway.service.core.common.OrderQueryService;
import com.hq.diego.model.resp.OrderTradeResp;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyang on 04/12/2016.
 */
@Service("AliOrderQueryService")
public class AliOrderQueryService extends AliCommonService implements OrderQueryService {

    @Autowired
    private AliServiceExecutor aliExecutor;

    @Override
    public OrderTradeResp queryOrder(String tradeId, OrderTradeReq tradeReq, ChannelRoute route) {
        Map<String, String> params = new HashMap<>();
        params.put("out_trade_no", tradeReq.getOrderId());
        // params.put("trade_no", queryReq.gettOrderId());
        OrderTradeResp tradeResp = new OrderTradeResp();
        try {
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            request.setBizContent(JSON.toJSONString(params));
            AlipayTradeQueryResponse response = this
                    .aliExecutor.execute(request, null, route.getKey());
            if("10000".equals(response.getCode())) {
                fill(tradeResp, response);
            } else {
                String errCode = response.getCode();
                if(!StringUtils.isEmpty(response.getSubCode())) {
                    errCode += ("(" + response.getSubCode() + ")");
                }
                tradeResp.setErrCode(errCode);
                tradeResp.setErrCodeDes(response.getSubMsg());
                if("20000".equals(response.getCode())) {
                    tradeResp.setTradeState(TradeState.UNKNOW);
                } else {
                    tradeResp.setTradeState(TradeState.FAIL);
                }
            }
        } catch (Throwable th) {
            tradeResp.setTradeState(TradeState.UNKNOW);
            if(th instanceof CommonException) {
                CommonException ce = (CommonException) th;
                tradeResp.setErrCode(ce.getErrCode());
                tradeResp.setErrCodeDes(ce.getErrMsg());
            } else {
                tradeResp.setErrCode(CommonErrCode.UNKNOW_ERROR.getCode());
                tradeResp.setErrCodeDes(CommonErrCode.UNKNOW_ERROR.getDesc());
            }
        }
        return tradeResp;
    }

    public void fill(OrderTradeResp tradeResp, AlipayTradeQueryResponse response) {
        tradeResp.setOrderId(response.getOutTradeNo());
        tradeResp.settOrderId(response.getTradeNo());
        tradeResp.setTradeState(
                TradeState.fromAliTradeState(response.getTradeStatus()));
        tradeResp.settLoginId(response.getBuyerLogonId());
        tradeResp.settUserId(response.getBuyerUserId());
        if(response.getSendPayDate() != null) {
            DateTime paymentTimestamp = new DateTime(response.getSendPayDate());
            tradeResp.setEndDate(paymentTimestamp.toString("YYYYMMdd"));
            tradeResp.setEndTime(paymentTimestamp.toString("HHmmss"));
        }
        tradeResp.setTotalAmount(toPrice(response.getTotalAmount()).intValue());
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
        if(response.getFundBillList() != null
                && response.getFundBillList().size() > 0) {
            tradeResp.setFundBills(JSON.toJSONString(response.getFundBillList()));
        }
    }

}
