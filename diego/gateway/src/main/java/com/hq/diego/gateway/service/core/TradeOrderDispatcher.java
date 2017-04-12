package com.hq.diego.gateway.service.core;

import com.alibaba.fastjson.JSON;
import com.hq.diego.gateway.service.core.route.RouteDetermineService;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.diego.gateway.service.persist.trade.TradeOrderService;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.diego.gateway.service.core.common.PrepayOrderGenService;
import com.hq.diego.gateway.service.core.common.SwipeCardPayService;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.redis.RedisCacheDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;

/**
 * Created by zhaoyang on 10/5/16.
 */
@Service
public class TradeOrderDispatcher {

    private static final Logger logger = Logger.getLogger(TradeOrderDispatcher.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TradeOrderService tradeOrderService;

    @Autowired
    private TradeOrderMonitor orderMonitor;

    @Autowired
    private RouteDetermineService routeService;

    @Autowired
    private PayResultNotifier notifier;

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    private TradePayValidator validator = new TradePayValidator();

    public OrderTradeResp dispatch(OrderTradeReq tradeReq) {

        // Do Validation
        this.validator.validate(tradeReq);

        // Find the channel route
        ChannelRoute route = this.routeService.determine(tradeReq);
        if (route == null) {
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "该商户没有可用的支付通道");
        }

        // Find Payment Service
        Object payServiceObj = this.applicationContext.getBean(route.getTradeService());
        if (payServiceObj == null) {
            logger.warn("<<< Can Not Find The Payment Service(" + route.getTradeService() + ")");
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR
                    , "找不到支付服务[" + route.getTradeService() + "]");
        }
        if (!(payServiceObj instanceof PrepayOrderGenService)
                && !(payServiceObj instanceof SwipeCardPayService)) {
            logger.warn("<<< Payment Service(" + payServiceObj.getClass() + ") Invalid");
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "支付服务错误");
        }

        // Save Prepay Order
        String tradeId;
        OrderTradeResp tradeResp;
        try {
            tradeId = this.tradeOrderService.savePreCreateOrder(tradeReq, route);
        } catch (Throwable th) {
            logger.warn("<<< Save PreCreateOrder Fail", th);
            tradeResp = new OrderTradeResp();
            tradeResp.setTradeState(TradeState.FAIL);
            tradeResp.setTips("出了点小问题,请稍后再试吧");
            tradeResp.clearSecureInfo();
            return tradeResp;
        }

        // Do Payment
        if(payServiceObj instanceof PrepayOrderGenService) {
            tradeResp = ((PrepayOrderGenService) payServiceObj).genPrepayOrder(tradeReq, route);
        } else {
            tradeResp = ((SwipeCardPayService) payServiceObj).onSwipePay(tradeReq, route);
        }
        tradeResp.setTradeId(tradeId);

        // Update Payment State
        try {
            this.tradeOrderService.updateWithTradeResp(tradeResp, route.getRate());
        } catch (Throwable th) {
            logger.error("<<< Save Resp Order Fail", th);
            logger.error("<[RESP JSON]:\r\n" + JSON.toJSONString(tradeResp));
        }

        // Clear some info
        if(!(TradeState.FAIL.equals(tradeResp.getTradeState())
                || TradeState.UNKNOW.equals(tradeResp.getTradeState())
                || TradeState.ABNORMAL.equals(tradeResp.getTradeState()))) {
            tradeResp.setErrCode("");
            tradeResp.setErrCodeDes("");
        }
        if(StringUtils.isEmpty(tradeResp.getTips())) {
            tradeResp.setTips(TradeState.getCNString(tradeResp.getTradeState()));
        }
        tradeResp.clearSecureInfo();

        // Monitor Unknow Payment Result
        if(TradeState.WAIT_PAY.equals(tradeResp.getTradeState())
                || TradeState.UNKNOW.equals(tradeResp.getTradeState())) {
            String cacheKey = RedisKeyConfigure.DiegoTradePollingCacheKey(tradeId);
            try {
                this.redisCacheDao.save(cacheKey, tradeResp, 3 * 60L);
            } catch (Throwable th) {
                logger.warn("<<< Save Trade Polling Cache Key " + cacheKey + " To Redis Fail", th);
            }
            // Start Monitor Payment State If Uncertain
            this.orderMonitor.startMonitor(tradeId, tradeReq, route);
        } else if (TradeState.SUCCESS.equals(tradeResp.getTradeState())) {
            this.notifier.notifyPayResult(
                      tradeReq.getMchId()
                    , Integer.valueOf(tradeReq.getOperatorId())
                    , tradeReq.getOperatorName()
                    , tradeReq.getOrderId(), tradeReq.getPayChannel()
                    , tradeReq.getTradeType(), Long.valueOf(tradeReq.getTotalAmount()));
        }
        return tradeResp;
    }

}
