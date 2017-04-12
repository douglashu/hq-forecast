package com.hq.diego.gateway.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.hq.diego.gateway.dubbo.TradeQueryDubboService;
import com.hq.diego.gateway.service.persist.trade.TradeOrderQueryService;
import com.hq.diego.model.req.OrderQueryReq;
import com.hq.diego.repository.model.generate.TDiegoTradeOrder;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 02/01/2017.
 */
@Service(interfaceName = "com.hq.diego.gateway.dubbo.TradeQueryDubboService", version = "1.0")
public class TradeQueryDubboServiceImpl extends DubboBaseService implements TradeQueryDubboService {

    @Autowired
    private TradeOrderQueryService queryService;

    @Override
    public RespEntity query(HqRequest request) {
        try {
            OrderQueryReq queryReq = parseRequest(request, OrderQueryReq.class);
            PageInfo<TDiegoTradeOrder> pageable = this.queryService.findByCondition(queryReq);
            return getSuccessResp(pageable);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

    @Override
    public RespEntity queryOne(HqRequest request) {
        OrderQueryReq queryReq = parseRequest(request, OrderQueryReq.class);
        if(StringUtils.isEmpty(queryReq.getId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "id不能为空");
        }
        TDiegoTradeOrder order;
        if(StringUtils.isEmpty(queryReq.getType())
                || "trade".equals(queryReq.getType())) {
            order = this.queryService.findByTradeId(queryReq.getId());
        } else if("order".equals(queryReq.getType())) {
            order = this.queryService.findByOrderId(queryReq.getId());
        } else if("third".equals(queryReq.getType())) {
            PayChannels.PayChannel payChannel = PayChannels.fromString(queryReq.getPayChannel());
            if(payChannel == null) {
                throw new CommonException(CommonErrCode.ARGS_INVALID, "支付渠道错误");
            }
            order = this.queryService.findByTOrderId(queryReq.getPayChannel(), queryReq.getId());
        } else {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "错误的订单类型[type=" + queryReq.getType() + "]");
        }
        if(order == null) {
            throw new CommonException(CommonErrCode.NO_DATA_FOUND, "找不到该订单");
        }
        return getSuccessResp(order);
    }

}
