package com.hq.diego.gateway.service.core.ali;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.hq.diego.gateway.constant.config.GatewayConfig;
import com.hq.diego.model.ali.AliExtendParams;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.diego.gateway.httpclient.executor.AliServiceExecutor;
import com.hq.diego.model.req.ali.AliOrderPrepayReq;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.resp.OrderPrepayResp;
import com.hq.diego.gateway.service.core.common.PrepayOrderGenService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 26/11/2016.
 */
@Service("AliQrCodePayService")
public class AliQrCodePayService extends AliCommonService implements PrepayOrderGenService {

    private static final Logger logger = Logger.getLogger(AliQrCodePayService.class);

    @Autowired
    private AliServiceExecutor aliExecutor;

    @Override
    public OrderPrepayResp genPrepayOrder(OrderTradeReq tradeReq, ChannelRoute route) {
        final AliOrderPrepayReq prepayReq = new AliOrderPrepayReq();
        prepayReq.setSeller_id(route.getMchId());
        prepayReq.setOut_trade_no(tradeReq.getOrderId());
        prepayReq.setSubject(tradeReq.getTitle());
        prepayReq.setBody(tradeReq.getBody());
        prepayReq.setTotal_amount(getPrice(tradeReq.getTotalAmount()));
        prepayReq.setUndiscountable_amount(null);
        prepayReq.setGoods_detail(getGoodsDetail(tradeReq.getGoodsList()));
        prepayReq.setTimeout_express((GatewayConfig.ORDER_DEFAULT_EXPIRE_MINUTES + "m"));
        AliExtendParams extendParams = new AliExtendParams();
        // 系统商编号 该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
        extendParams.setSys_service_provider_id(GatewayConfig.ALIPAY_ISV_PID);
        prepayReq.setExtend_params(extendParams);

        final OrderPrepayResp prepayResp = new OrderPrepayResp();
        prepayResp.from(tradeReq);
        try {
            AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
            request.setNotifyUrl(GatewayConfig.ALIPAY_NOTIFY_URL);
            request.setBizContent(JSON.toJSONString(prepayReq));
            AlipayTradePrecreateResponse response = this
                    .aliExecutor.execute(request, null, route.getKey());
            if("10000".equals(response.getCode())) {
                prepayResp.setTradeState(TradeState.PRE_CREATE);
                prepayResp.setCodeUrl(response.getQrCode());
            } else {
                String errCode = response.getCode();
                if(!StringUtils.isEmpty(response.getSubCode())) {
                    errCode += ("(" + response.getSubCode() + ")");
                }
                prepayResp.setTradeState(TradeState.FAIL);
                prepayResp.setErrCode(errCode);
                prepayResp.setErrCodeDes(response.getSubMsg());
            }
        } catch (AlipayApiException ae) {
            logger.warn("<<< Create Alipay QRCode On Api Error", ae);
            prepayResp.setTradeState(TradeState.FAIL);
            prepayResp.setErrCode(ae.getErrCode());
            prepayResp.setErrCodeDes(ae.getErrMsg());
        } catch (Throwable th) {
            logger.warn("<<< Create Alipay QRCode On Fail", th);
            prepayResp.setTradeState(TradeState.FAIL);
            prepayResp.setErrCode(CommonErrCode.UNKNOW_ERROR.getCode());
            prepayResp.setErrCodeDes(CommonErrCode.UNKNOW_ERROR.getDesc());
        }
        return prepayResp;
    }
}
