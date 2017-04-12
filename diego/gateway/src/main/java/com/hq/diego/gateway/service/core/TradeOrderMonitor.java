package com.hq.diego.gateway.service.core;

import com.alibaba.fastjson.JSON;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.diego.gateway.service.core.common.OrderCancelService;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.diego.gateway.service.core.common.OrderQueryService;
import com.hq.diego.gateway.service.persist.trade.TradeOrderService;
import com.hq.diego.model.req.OrderCancelReq;
import com.hq.diego.model.resp.OrderCancelResp;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.diego.repository.model.generate.TDiegoTradeOrder;
import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.redis.RedisCacheDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhaoyang on 29/11/2016.
 */
@Service
public class TradeOrderMonitor {

    private static final Logger logger = Logger.getLogger(TradeOrderMonitor.class);

    public static final Integer MAX_ORDER_QUERY_TIMES = 8;
    public static final Integer ORDER_QUERY_MILLS_DURATION = 5000;

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TradeOrderService tradeOrderService;

    @Autowired
    private CancelOrderDispatcher cancelDispatcher;

    @Autowired
    private PayResultNotifier notifier;

    private ExecutorService executorService;

    public TradeOrderMonitor() {
        this.executorService = Executors.newCachedThreadPool();
    }

    public void startMonitor(final String tradeId
            , final OrderTradeReq tradeReq, final ChannelRoute route) {
        this.executorService.submit(new Runnable() {
            @Override
            public void run() {
                startMonitorInternal(tradeId, tradeReq, route);
            }
        });
    }

    private void startMonitorInternal(String tradeId, OrderTradeReq tradeReq, ChannelRoute route) {
        int queryTimes = 1;
        boolean isDone = false;
        PayChannels.PayChannel payChannel = PayChannels.fromString(tradeReq.getPayChannel());
        Object orderQueryObj = this.applicationContext.getBean(route.getQueryService());
        OrderQueryService orderQueryService = (OrderQueryService) orderQueryObj;
        OrderTradeResp tradeResp = null;
        while (!isDone) {
            try {
                Thread.sleep(ORDER_QUERY_MILLS_DURATION);
                tradeResp = orderQueryService.queryOrder(tradeId, tradeReq, route);
                isDone = !(TradeState.WAIT_PAY.equals(tradeResp.getTradeState())
                                || TradeState.UNKNOW.equals(tradeResp.getTradeState()));
                if(!isDone) {
                    logger.warn("<<< Monitor Payment Order(" + tradeReq.getOrderId()
                            + ") On Times " + queryTimes +", RespCode=" + tradeResp.getErrCode()
                            + ", RespMsg=" + tradeResp.getErrCodeDes() + ")");
                }
            } catch (Throwable th) {
                // 此处需要吞掉异常
                logger.warn(("<<< Monitor Payment Order("
                        + tradeReq.getOrderId() + ") Fail On Times " + queryTimes), th);
            }
            if(queryTimes >= MAX_ORDER_QUERY_TIMES) break;
            queryTimes++;
        }
        if(tradeResp == null) {
            tradeResp = new OrderTradeResp();
            tradeResp.setTradeState(TradeState.UNKNOW);
        }
        tradeResp.setTradeId(tradeId);
        // 订单长时间未支付或者状态异常需要撤销此订单
        if(TradeState.WAIT_PAY.equals(tradeResp.getTradeState())
                || TradeState.UNKNOW.equals(tradeResp.getTradeState())) {
            OrderCancelReq cancelReq = new OrderCancelReq();
            cancelReq.setTradeId(tradeId);
            cancelReq.setTotalAmount(tradeReq.getTotalAmount());
            cancelReq.setOrderId(tradeReq.getOrderId());
            cancelReq.setPayChannel(payChannel.getCode());
            OrderCancelResp cancelResp = this.cancelDispatcher.dispatch(cancelReq, route);
            if(OrderCancelService.CANCEL_RESULT_SUCCESS.equals(cancelResp.getResult())) {
                // 撤销成功
                tradeResp.setTradeState(TradeState.REVOKED);
                tradeResp.setReceiptAmount(0);
                tradeResp.setErrCode(null);
                tradeResp.setErrCodeDes(null);
            } else {
                // 撤销状态未知或失败, 记录异常订单人工处理
                tradeResp.setTradeState(TradeState.ABNORMAL);
                tradeResp.setErrCode(CommonErrCode.UNKNOW_ERROR.getCode());
                tradeResp.setErrCodeDes(CommonErrCode.UNKNOW_ERROR.getDesc());
                tradeResp.setErrCodeDes("交易状态未知,尝试撤销失败");
            }
        }
        // 持久化交易结果
        try {
            this.tradeOrderService.updateWithTradeResp(tradeResp, route.getRate());
        } catch (Throwable th) {
            logger.error("<<< Update Resp Order Fail", th);
            logger.error(JSON.toJSONString(tradeResp));
        }
        // 删除redis监控key
        String cacheKey = RedisKeyConfigure.DiegoTradePollingCacheKey(tradeId);
        try {
            this.redisCacheDao.delete(cacheKey, OrderTradeResp.class);
        } catch (Throwable th) {
            logger.warn("<<< Delete Trade Polling Cache Key " + cacheKey + " From Redis Fail", th);
        }
        // 对于交易成功的订单, 发送异步通知
        if (TradeState.SUCCESS.equals(tradeResp.getTradeState())) {
            this.notifier.notifyPayResult(tradeReq.getMchId(),
                    Integer.valueOf(tradeReq.getOperatorId()),
                    tradeReq.getOperatorName(), tradeReq.getOrderId(),
                    tradeReq.getPayChannel(), tradeReq.getTradeType(),
                    Long.valueOf(tradeResp.getTotalAmount()));
        }
    }

    public OrderTradeResp pollingTradeResp(String mchId, String tradeId) {
        if(StringUtils.isEmpty(mchId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商户号不能为空[mchId]");
        }
        if(StringUtils.isEmpty(tradeId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "交易号不能为空[tradeId]");
        }
        String cacheKey = RedisKeyConfigure.DiegoTradePollingCacheKey(tradeId);
        OrderTradeResp tradeResp = null;
        try {
            tradeResp = this.redisCacheDao.read(cacheKey, OrderTradeResp.class);
        } catch (Throwable th) {
            logger.warn("<<< Read Trade Polling Cache Key " + cacheKey + " From Redis Fail", th);
            try { this.redisCacheDao.delete(cacheKey, OrderTradeResp.class); } finally { }
        }
        if(tradeResp != null) {
            if(tradeResp.getTradeId().startsWith(mchId)) {
                return tradeResp;
            } else {
                throw new CommonException(CommonErrCode.BUSINESS, "交易记录不存在");
            }
        }

        TDiegoTradeOrder tradeOrder = this.tradeOrderService.getTradeOrderById(tradeId);
        if(tradeOrder == null || !tradeOrder.getId().startsWith(mchId)) {
            throw new CommonException(CommonErrCode.NO_DATA_FOUND, "交易记录不存在");
        }
        tradeResp = new OrderTradeResp();
        tradeResp.setTradeId(tradeOrder.getId());
        tradeResp.setOrderId(tradeOrder.getOrderId());
        tradeResp.setPayChannel(tradeOrder.getPayChannel());
        tradeResp.setTradeType(tradeOrder.getTradeType());
        tradeResp.setTradeState(tradeOrder.getTradeState());
        tradeResp.setTotalAmount(tradeOrder.getTotalAmount().intValue());
        tradeResp.setReceiptAmount(tradeOrder.getReceiptAmount().intValue());
        tradeResp.setEndDate(tradeOrder.getEndDate());
        tradeResp.setEndTime(tradeOrder.getEndTime());
        tradeResp.setErrCode(tradeOrder.getErrorCode());
        tradeResp.setErrCodeDes(tradeOrder.getErrorCodeDes());
        tradeResp.setTips(TradeState.getCNString(tradeOrder.getTradeState()));
        return tradeResp;
    }
}
