package com.hq.diego.gateway.service.core;

import com.alibaba.fastjson.JSON;
import com.hq.diego.gateway.service.persist.trade.CancelOrderService;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.diego.gateway.service.core.common.OrderCancelService;
import com.hq.diego.model.req.OrderCancelReq;
import com.hq.diego.model.resp.OrderCancelResp;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 07/12/2016.
 */
@Service
public class CancelOrderDispatcher {

    private static final Logger logger = Logger.getLogger(CancelOrderDispatcher.class);

    private static final Integer MAX_TRY_TIMES = 3;

    private static final String CANCEL_RESULT_SUCCESS = "SUCCESS";
    private static final String CANCEL_RESULT_FAIL = "FAIL";
    private static final String CANCEL_RESULT_UNKNOW = "UNKNOW";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CancelOrderService cancelOrderService;

    public OrderCancelResp dispatch(OrderCancelReq cancelReq, ChannelRoute route) {
        if(cancelReq == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        PayChannels.PayChannel payChannel = PayChannels.fromString(cancelReq.getPayChannel());
        if(StringUtils.isEmpty(cancelReq.getOrderId())
                && StringUtils.isEmpty(cancelReq.gettOrderId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "订单号或商户订单号不能同时为空");
        }
        String cancelId;
        OrderCancelResp orderCancelResp = null;
        try {
            // 生成撤销请求订单
            cancelId = this.cancelOrderService.createPreCancelOrder(cancelReq.getTradeId());
        } catch (Throwable th) {
            logger.error("<<< Save Pre Cancel Order DataBase Fail(" + cancelReq.getTradeId() + ")", th);
            orderCancelResp = new OrderCancelResp();
            orderCancelResp.setResult(OrderCancelService.CANCEL_RESULT_FAIL);
            orderCancelResp.setErrCode(CommonErrCode.DB_ERROR.getCode());
            orderCancelResp.setErrCodeDes(CommonErrCode.DB_ERROR.getDesc());
            return orderCancelResp;
        }
        Object orderCancelObj = this.applicationContext.getBean(route.getCancelService());
        int tryTimes = 1;
        while (true) {
            try {
                if (tryTimes > MAX_TRY_TIMES) {
                    if (orderCancelResp == null) orderCancelResp = new OrderCancelResp();
                    orderCancelResp.setResult(CANCEL_RESULT_UNKNOW);
                    if (StringUtils.isEmpty(orderCancelResp.getErrCode())) {
                        orderCancelResp.setErrCode(CommonErrCode.UNKNOW_ERROR.getCode());
                    }
                    if (StringUtils.isEmpty(orderCancelResp.getErrCodeDes())) {
                        orderCancelResp.setErrCodeDes("交易撤销已失败" + MAX_TRY_TIMES + "次");
                    }
                    break;
                }
                orderCancelResp = ((OrderCancelService) orderCancelObj).cancelOrder(cancelReq, route);
                if (orderCancelResp != null &&
                        CANCEL_RESULT_SUCCESS.equals(orderCancelResp.getResult())) {
                    orderCancelResp.setErrCode(null);
                    orderCancelResp.setErrCodeDes(null);
                    break;
                } else if (orderCancelResp != null &&
                        CANCEL_RESULT_FAIL.equals(orderCancelResp.getResult())) {
                    break;
                } else {
                    tryTimes++;
                    continue;
                }
            } catch (Throwable th) {
                // 此处异常吞掉, 并且置失败错误次数+1
                logger.warn("<<< " + route.getCancelService() + " Cancel Order Fail", th);
                tryTimes++;
                continue;
            }
        }
        // set cancel id
        orderCancelResp.setId(cancelId);
        try {
            this.cancelOrderService.updateWithCancelResp(orderCancelResp);
        } catch (Throwable th) {
            logger.error("<<< Update Cancel Order(" + cancelReq.getTradeId() + ") Fail", th);
            logger.error(JSON.toJSONString(orderCancelResp));
        }
        return orderCancelResp;
    }

}
