package com.hq.diego.gateway.service.core;

import com.hq.diego.gateway.service.core.common.TradeNotifyService;
import com.hq.diego.model.req.TradeNotifyReq;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 16/03/2017.
 */
@Service
public class TradeCallbackDispatcher {

    private static final Logger logger = Logger.getLogger(TradeCallbackDispatcher.class);

    @Autowired
    private ApplicationContext applicationContext;

    public String dispatch(TradeNotifyReq tradeNotifyReq) {
        if (tradeNotifyReq == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        if (StringUtils.isEmpty(tradeNotifyReq.getService())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "异步通知服务名不能为空[service]");
        }
        if (StringUtils.isEmpty(tradeNotifyReq.getBizContent())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "异步通知业务参数不能为空[bizContent]");
        }
        Object obj = this.applicationContext.getBean(tradeNotifyReq.getService());
        if (obj == null || !(obj instanceof TradeNotifyService)) {
            logger.error("<<< Can not find the valid trade notify service("
                    + tradeNotifyReq.getService() + ")");
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR
                    , "找不到异步通知处理服务[" + tradeNotifyReq.getService() + "]");
        }
        return ((TradeNotifyService) obj).onCallback(tradeNotifyReq.getBizContent());
    }

}
