package com.hq.diego.gateway.service.persist.trade;

import com.hq.diego.gateway.service.core.common.OrderCancelService;
import com.hq.scrati.common.constants.trade.CancelState;
import com.hq.diego.model.resp.OrderCancelResp;
import com.hq.diego.repository.dao.generate.TDiegoCancelOrderMapper;
import com.hq.diego.repository.model.generate.TDiegoCancelOrder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 09/12/2016.
 */
@Service
public class CancelOrderService {

    @Autowired
    private TDiegoCancelOrderMapper cancelOrderMapper;

    public String createPreCancelOrder(String tradeId) {
        DateTime now = DateTime.now();
        TDiegoCancelOrder cancelOrder = new TDiegoCancelOrder();
        String cancelId = String.valueOf(System.currentTimeMillis());
        cancelOrder.setId(cancelId);
        cancelOrder.setTradeId(tradeId);
        cancelOrder.setCancelState(CancelState.PRE_CREATE);
        cancelOrder.setCreateDate(now.toString("YYYYMMdd"));
        cancelOrder.setCreateTime(now.toString("HHmmss"));
        this.cancelOrderMapper.insert(cancelOrder);
        return cancelId;
    }

    public void updateWithCancelResp(OrderCancelResp cancelResp) {
        DateTime now = DateTime.now();
        TDiegoCancelOrder updateEntity = new TDiegoCancelOrder();
        updateEntity.setId(cancelResp.getId());
        String cancelState;
        if(OrderCancelService.CANCEL_RESULT_SUCCESS.equals(cancelResp.getResult())) {
            cancelState = CancelState.SUCCESS;
        } else if(OrderCancelService.CANCEL_RESULT_FAIL.equals(cancelResp.getResult())) {
            cancelState = CancelState.FAIL;
        } else {
            cancelState = CancelState.UNKNOW;
        }
        updateEntity.setCancelState(cancelState);
        updateEntity.setAction(cancelResp.getAction());
        updateEntity.setEndDate(now.toString("YYYYMMdd"));
        updateEntity.setEndTime(now.toString("HHmmss"));
        updateEntity.setErrCode(cancelResp.getErrCode());
        updateEntity.setErrCodeDes(cancelResp.getErrCodeDes());
        this.cancelOrderMapper.updateByPrimaryKeySelective(updateEntity);
    }

}
