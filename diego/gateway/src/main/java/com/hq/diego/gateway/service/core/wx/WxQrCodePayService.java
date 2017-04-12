package com.hq.diego.gateway.service.core.wx;

import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.diego.gateway.constant.url.WxPayURL;
import com.hq.diego.gateway.httpclient.executor.WxServiceExecutor;
import com.hq.diego.model.resp.wx.WxUnifiedOrderResp;
import com.hq.diego.model.resp.OrderPrepayResp;
import com.hq.diego.gateway.service.core.common.PrepayOrderGenService;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.req.wx.WxOrderPrepayReq;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 25/11/2016.
 */
@Service("WxQrCodePayService")
public class WxQrCodePayService extends WxCommonService implements PrepayOrderGenService {

    private static final Logger logger = Logger.getLogger(WxQrCodePayService.class);

    @Autowired
    private WxServiceExecutor executor;

    @Override
    public OrderPrepayResp genPrepayOrder(OrderTradeReq tradeReq, ChannelRoute route) {
        final WxOrderPrepayReq prepayReq = createPrepayOrder(tradeReq, route);
        prepayReq.setTrade_type("NATIVE");

        final OrderPrepayResp prepayResp = new OrderPrepayResp();
        prepayResp.from(tradeReq);
        try {
            WxUnifiedOrderResp resp = this.executor.execute(
                    WxPayURL.getCreatePrepayOrderUrl(), prepayReq, WxUnifiedOrderResp.class);
            if ("SUCCESS".equals(resp.getReturn_code())) {
                if ("SUCCESS".equals(resp.getResult_code())) {
                    prepayResp.setTradeState(TradeState.PRE_CREATE);
                    prepayResp.setCodeUrl(resp.getCode_url());
                } else {
                    prepayResp.setTradeState(TradeState.FAIL);
                    prepayResp.setErrCode(resp.getErr_code());
                    prepayResp.setErrCodeDes(resp.getErr_code_des());
                }
            } else {
                prepayResp.setTradeState(TradeState.FAIL);
                prepayResp.setErrCode(resp.getReturn_code());
                prepayResp.setErrCodeDes(resp.getReturn_msg());
            }
        } catch (Throwable th) {
            logger.warn("<<< Create Wx QRCode Fail", th);
            prepayResp.setTradeState(TradeState.FAIL);
            if(th instanceof CommonException) {
                CommonException ce = (CommonException) th;
                prepayResp.setErrCode(ce.getErrCode());
                prepayResp.setErrCodeDes(ce.getErrMsg());
            } else {
                prepayResp.setErrCode(CommonErrCode.UNKNOW_ERROR.getCode());
                prepayResp.setErrCodeDes(CommonErrCode.UNKNOW_ERROR.getDesc());
            }
        }

        return prepayResp;
    }

}
