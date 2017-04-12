package com.hq.diego.gateway.service.core.wx;

import com.hq.diego.gateway.constant.url.WxPayURL;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.diego.gateway.httpclient.executor.WxServiceExecutor;
import com.hq.diego.gateway.service.core.common.SwipeCardPayService;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.req.wx.WxOrderPrepayReq;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.diego.model.resp.wx.WxPayResp;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 15/11/2016.
 */
@Service("WxSwipeCardPayService")
public class WxSwipeCardPayService extends WxCommonService implements SwipeCardPayService {

    private static final Logger logger = Logger.getLogger(WxSwipeCardPayService.class);

    @Autowired
    private WxServiceExecutor executor;

    @Override
    public OrderTradeResp onSwipePay(OrderTradeReq tradeReq, ChannelRoute route) {
        final WxOrderPrepayReq wxReq = new WxOrderPrepayReq();
        wxReq.setSub_appid(route.getAppId());
        wxReq.setSub_mch_id(route.getMchId());
        wxReq.setDevice_info(tradeReq.getTerminalId());
        wxReq.setSpbill_create_ip(tradeReq.getIpAddress());
        wxReq.setOut_trade_no(tradeReq.getOrderId());
        wxReq.setBody(tradeReq.getTitle());
        wxReq.setDetail(getDetailJsonStr(tradeReq.getGoodsList()));
        wxReq.setFee_type(tradeReq.getFeeType());
        wxReq.setTotal_fee(tradeReq.getTotalAmount());
        wxReq.setAuth_code(tradeReq.getAuthCode());
        wxReq.setAttach(tradeReq.getCustom());

        final OrderTradeResp tradeResp = new OrderTradeResp();
        tradeResp.from(tradeReq);
        try {
            WxPayResp wxPayResp = this.executor.execute(
                    WxPayURL.getDoSwipePayUrl(), wxReq, WxPayResp.class);
            if ("SUCCESS".equals(wxPayResp.getReturn_code())) {
                // 通讯成功
                if ("SUCCESS".equals(wxPayResp.getResult_code())) {
                    // 支付成功, 更新交易流水,返回成功消息
                    tradeResp.settOrderId(wxPayResp.getTransaction_id());
                    tradeResp.setTradeState(TradeState.SUCCESS);
                    tradeResp.setOpenId(wxPayResp.getOpenid());
                    tradeResp.setIsSubscribe(wxPayResp.getIs_subscribe());
                    tradeResp.setSubOpenId(wxPayResp.getSub_openid());
                    tradeResp.setSubIsSubscribe(wxPayResp.getSub_is_subscribe());
                    if(wxPayResp.getSettlement_total_fee() != null) {
                        tradeResp.setReceiptAmount(wxPayResp.getSettlement_total_fee());
                    } else {
                        tradeResp.setReceiptAmount(wxPayResp.getTotal_fee());
                    }
                    tradeResp.setBuyerPayAmount(wxPayResp.getCash_fee());
                    tradeResp.setBankType(wxPayResp.getBank_type());
                    if(!StringUtils.isEmpty(wxPayResp.getTime_end())) {
                        tradeResp.setEndDate(wxPayResp.getTime_end().substring(0, 8));
                        tradeResp.setEndTime(wxPayResp.getTime_end().substring(8));
                    }
                } else {
                    if ("USERPAYING".equals(wxPayResp.getErr_code())) {
                        tradeResp.setTradeState(TradeState.WAIT_PAY);
                    } else {
                        tradeResp.setErrCode(wxPayResp.getErr_code());
                        tradeResp.setErrCodeDes(wxPayResp.getErr_code_des());
                        if("SYSTEMERROR".equals(wxPayResp.getErr_code())
                                || "BANKERROR".equals(wxPayResp.getErr_code())) {
                            // 支付未知
                            logger.warn("<<< Wx Swipe Pay UnKnow(ErrCode=" + wxPayResp.getErr_code()
                                    + ", ErrCodeDes=" + wxPayResp.getErr_code_des() + ")");
                            tradeResp.setTradeState(TradeState.UNKNOW);
                        } else {
                            // 支付失败
                            logger.warn("<<< Wx Swipe Pay Fail(ErrCode=" + wxPayResp.getErr_code()
                                    + ", ErrCodeDes=" + wxPayResp.getErr_code_des() + ")");
                            tradeResp.setTradeState(TradeState.FAIL);
                        }
                    }
                }
            } else {
                // 通讯失败
                logger.warn("<<< Wx Swipe Pay Fail(ReturnCode=" + wxPayResp.getReturn_code()
                        + ", ReturnMsg=" + wxPayResp.getReturn_msg() + ")");
                tradeResp.setTradeState(TradeState.FAIL);
                tradeResp.setErrCode(wxPayResp.getReturn_code());
                tradeResp.setErrCodeDes(wxPayResp.getReturn_msg());
            }
        } catch (Throwable th) {
            logger.warn("<<< Wx Swipe Pay UnKnow", th);
            tradeResp.setTradeState(TradeState.UNKNOW);
            tradeResp.setErrCode(CommonErrCode.UNKNOW_ERROR.getCode());
            tradeResp.setErrCodeDes(CommonErrCode.UNKNOW_ERROR.getDesc());
        }
        return tradeResp;
    }
}
