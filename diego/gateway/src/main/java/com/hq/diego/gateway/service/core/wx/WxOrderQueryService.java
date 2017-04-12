package com.hq.diego.gateway.service.core.wx;

import com.hq.diego.gateway.constant.url.WxPayURL;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.diego.gateway.httpclient.executor.WxServiceExecutor;
import com.hq.diego.gateway.service.core.common.OrderQueryService;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.diego.model.req.wx.WxQueryOrderReq;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.diego.model.resp.wx.WxPayResp;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 17/11/2016.
 */
@Service("WxOrderQueryService")
public class WxOrderQueryService implements OrderQueryService {

    private static final Logger logger = Logger.getLogger(WxOrderQueryService.class);

    @Autowired
    private WxServiceExecutor executor;

    @Override
    public OrderTradeResp queryOrder(String tradeId, OrderTradeReq tradeReq, ChannelRoute route) {
        WxQueryOrderReq queryOrderReq = new WxQueryOrderReq();
        // queryOrderReq.setTransaction_id(queryReq.gettOrderId());
        queryOrderReq.setOut_trade_no(tradeReq.getOrderId());
        queryOrderReq.setSub_appid(route.getAppId());
        queryOrderReq.setSub_mch_id(route.getMchId());
        OrderTradeResp tradeResp = new OrderTradeResp();
        try {
            WxPayResp response = this.executor.execute(
                    WxPayURL.getQueryOrderUrl(), queryOrderReq, WxPayResp.class);
            if("SUCCESS".equals(response.getReturn_code())) {
                if("SUCCESS".equals(response.getResult_code())) {
                    fill(tradeResp, response);
                } else {
                    if("SYSTEMERROR".equals(response.getResult_code())) {
                        tradeResp.setTradeState(TradeState.UNKNOW);
                    } else {
                        tradeResp.setTradeState(TradeState.FAIL);
                    }
                    tradeResp.setErrCode(response.getErr_code());
                    tradeResp.setErrCodeDes(response.getErr_code_des());
                }
            } else {
                tradeResp.setTradeState(TradeState.UNKNOW);
                tradeResp.setErrCode(response.getReturn_code());
                tradeResp.setErrCodeDes(response.getReturn_msg());
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

    public void fill(OrderTradeResp tradeResp, WxPayResp response) {
        tradeResp.setOrderId(response.getOut_trade_no());
        tradeResp.settOrderId(response.getTransaction_id());
        tradeResp.setTradeState(
                TradeState.fromWxTradeState(response.getTrade_state()));
        tradeResp.setOpenId(response.getOpenid());
        tradeResp.setIsSubscribe(response.getIs_subscribe());
        tradeResp.setSubOpenId(response.getSub_openid());
        tradeResp.setSubIsSubscribe(response.getSub_is_subscribe());
        tradeResp.setTotalAmount(response.getTotal_fee());
        if(response.getSettlement_total_fee() != null) {
            tradeResp.setReceiptAmount(response.getSettlement_total_fee());
        } else {
            tradeResp.setReceiptAmount(response.getTotal_fee());
        }
        tradeResp.setBuyerPayAmount(response.getCash_fee());
        tradeResp.setBankType(response.getBank_type());
        if(TradeState.FAIL.equals(tradeResp.getTradeState())) {
            tradeResp.setErrCode(response.getTrade_state());
            tradeResp.setErrCodeDes(response.getTrade_state_desc());
        }
        if(!StringUtils.isEmpty(response.getTime_end())) {
            tradeResp.setEndDate(response.getTime_end().substring(0, 8));
            tradeResp.setEndTime(response.getTime_end().substring(8));
        }
    }
}
