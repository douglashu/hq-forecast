package com.hq.diego.gateway.service.core.cib;

import com.alibaba.fastjson.JSONObject;
import com.hq.diego.gateway.constant.config.GatewayConfig;
import com.hq.diego.gateway.httpclient.executor.CibServiceExecutor;
import com.hq.diego.gateway.service.core.common.PrepayOrderGenService;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.req.cib.CibOrderPrepayReq;
import com.hq.diego.model.resp.OrderPrepayResp;
import com.hq.diego.model.resp.cib.CibUnifiedOrderResp;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyang on 04/03/2017.
 */
@Service("CibH5PayService")
public class CibH5PayService implements PrepayOrderGenService {

    private static final Logger logger = Logger.getLogger(CibH5PayService.class);

    @Autowired
    private CibServiceExecutor executor;

    @Override
    public OrderPrepayResp genPrepayOrder(OrderTradeReq tradeReq, ChannelRoute route) {
        DateTime now = DateTime.now();
        final CibOrderPrepayReq prepayReq = new CibOrderPrepayReq();
        if (PayChannels.WEIXIN_PAY.getCode().equals(tradeReq.getPayChannel())) {
            prepayReq.setService("pay.weixin.jspay");
            prepayReq.setIs_raw("1");
            /*
            if (!StringUtils.isEmpty(route.getAppId())) {
                prepayReq.setSub_appid(route.getAppId());
            } else {
                prepayReq.setSub_appid(GatewayConfig.WX_APP_ID);
            }
            prepayReq.setSub_openid(tradeReq.getOpenId());
            */
        } else if (PayChannels.ALI_PAY.getCode().equals(tradeReq.getPayChannel())) {
            prepayReq.setService("pay.alipay.jspay");
            prepayReq.setBuyer_id(tradeReq.getBuyerId());
        }
        prepayReq.setVersion("2.0");
        prepayReq.setMch_id(route.getMchId());
        prepayReq.setOut_trade_no(tradeReq.getOrderId());
        prepayReq.setDevice_info(tradeReq.getTerminalId());
        prepayReq.setBody(tradeReq.getTitle());
        prepayReq.setTotal_fee(tradeReq.getTotalAmount());
        prepayReq.setMch_create_ip(tradeReq.getIpAddress());
        prepayReq.setTime_start(now.toString("YYYYMMddHHmmss"));
        prepayReq.setNotify_url(GatewayConfig.CIB_NOTIFY_URL);
        prepayReq.setTime_expire(now.plusMinutes(GatewayConfig
                .ORDER_DEFAULT_EXPIRE_MINUTES).toString("YYYYMMddHHmmss"));
        prepayReq.setOp_user_id(tradeReq.getOperatorId());
        final OrderPrepayResp prepayResp = new OrderPrepayResp();
        prepayResp.from(tradeReq);
        try {
            CibUnifiedOrderResp resp = this.executor.execute(
                    prepayReq, CibUnifiedOrderResp.class, route.getKey());
            if ("0".equals(resp.getStatus())) {
                if ("0".equals(resp.getResult_code())) {
                    Map<String, Object> params = new HashMap<>();
                    JSONObject jsonObj = JSONObject.parseObject(resp.getPay_info());
                    if (jsonObj.containsKey("paySign")) {
                        params.put("appId", jsonObj.getString("appId"));
                        params.put("timeStamp", jsonObj.getString("timeStamp"));
                        params.put("nonceStr", jsonObj.getString("nonceStr"));
                        params.put("package", jsonObj.getString("package"));
                        params.put("signType", jsonObj.getString("signType"));
                        params.put("paySign", jsonObj.getString("paySign"));
                    } else if (jsonObj.containsKey("tradeNO")) {
                        params.put("tradeNO", jsonObj.getString("tradeNO"));
                    }
                    prepayResp.setTradeState(TradeState.PRE_CREATE);
                    prepayResp.setOrderInfo(params);
                } else {
                    prepayResp.setTradeState(TradeState.FAIL);
                    prepayResp.setErrCode(resp.getErr_code());
                    prepayResp.setErrCodeDes(resp.getErr_msg());
                }
            } else {
                prepayResp.setTradeState(TradeState.FAIL);
                prepayResp.setErrCode(resp.getStatus());
                prepayResp.setErrCodeDes(resp.getMessage());
            }
        } catch (Throwable th) {
            logger.warn("<<< Create Cib H5 PAY Fail", th);
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
