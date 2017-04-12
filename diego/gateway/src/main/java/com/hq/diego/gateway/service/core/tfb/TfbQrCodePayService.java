package com.hq.diego.gateway.service.core.tfb;

import com.hq.diego.gateway.constant.config.GatewayConfig;
import com.hq.diego.gateway.httpclient.executor.TfbServiceExecutor;
import com.hq.diego.gateway.service.core.common.PrepayOrderGenService;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.req.tfb.TfbTradeReq;
import com.hq.diego.model.resp.OrderPrepayResp;
import com.hq.diego.model.resp.tfb.TfbPayResp;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 15/03/2017.
 */
@Service("TfbQrCodePayService")
public class TfbQrCodePayService implements PrepayOrderGenService {

    private static final Logger logger = Logger.getLogger(TfbQrCodePayService.class);

    @Autowired
    private TfbServiceExecutor executor;

    @Override
    public OrderPrepayResp genPrepayOrder(OrderTradeReq tradeReq, ChannelRoute route) {
        final TfbTradeReq tfbReq = new TfbTradeReq();
        tfbReq.setSp_billno(tradeReq.getOrderId());
        tfbReq.setSpbill_create_ip(tradeReq.getIpAddress());
        tfbReq.setPay_type("800201");
        tfbReq.setCur_type(tradeReq.getFeeType());
        tfbReq.setAuth_code(tradeReq.getAuthCode());
        tfbReq.setItem_name(tradeReq.getTitle());
        tfbReq.setItem_attach(tradeReq.getBody());
        tfbReq.setTran_time(DateTime.now().toString("YYYYMMddHHmmss"));
        tfbReq.setTran_amt(tradeReq.getTotalAmount());
        tfbReq.setNotify_url(GatewayConfig.TFB_NOTIFY_URL);
        tfbReq.setSpid(route.getMchId());
        if (PayChannels.WEIXIN_PAY.getCode().equals(tradeReq.getPayChannel())) {


            tfbReq.setBank_mch_id("7656765");
            tfbReq.setBank_mch_name("rrrr");



            tfbReq.setReqUrl("/api_wx_pay_apply.cgi");
        } else if (PayChannels.ALI_PAY.getCode().equals(tradeReq.getPayChannel())) {
            tfbReq.setReqUrl("/api_ali_pay_apply.cgi");
        }
        final OrderPrepayResp prepayResp = new OrderPrepayResp();
        prepayResp.from(tradeReq);
        try {
            TfbPayResp resp = this.executor.execute(tfbReq, TfbPayResp.class, route.getKey());
            if ("00".equals(resp.getRetcode())) {
                prepayResp.setTradeState(TradeState.PRE_CREATE);
                prepayResp.setCodeUrl(resp.getQrcode());
                prepayResp.settOrderId(resp.getListid());
                prepayResp.setTtOrderId(resp.getMerch_listid());
            } else {
                prepayResp.setTradeState(TradeState.FAIL);
                prepayResp.setErrCode(resp.getRetcode());
                prepayResp.setErrCodeDes(resp.getRetmsg());
            }
        } catch (Throwable th) {
            logger.warn("<<< Create Tfb QRCode Fail", th);
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
