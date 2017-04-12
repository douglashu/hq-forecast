package com.hq.diego.gateway.service.persist.trade;

import com.github.pagehelper.PageInfo;
import com.hq.diego.model.req.OrderQueryReq;
import com.hq.diego.repository.dao.generate.TDiegoTradeOrderMapper;
import com.hq.diego.repository.model.generate.TDiegoTradeOrder;
import com.hq.diego.repository.model.generate.TDiegoTradeOrderExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by zhaoyang on 09/12/2016.
 */
@Service
public class TradeOrderQueryService {

    @Autowired
    private TDiegoTradeOrderMapper tradeOrderMapper;

    public TDiegoTradeOrder findByTradeId(String tradeId) {
        return this.tradeOrderMapper.selectByPrimaryKey(tradeId);
    }

    public TDiegoTradeOrder findByOrderId(String orderId) {
        TDiegoTradeOrderExample example = new TDiegoTradeOrderExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<TDiegoTradeOrder> tradeOrders = this.tradeOrderMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(tradeOrders)) return null;
        return tradeOrders.get(0);
    }

    public TDiegoTradeOrder findByTOrderId(String payChannel, String tOrderId) {
        TDiegoTradeOrderExample example = new TDiegoTradeOrderExample();
        example.createCriteria().andPayChannelEqualTo(payChannel).andTOrderIdEqualTo(tOrderId);
        List<TDiegoTradeOrder> tradeOrders = this.tradeOrderMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(tradeOrders)) return null;
        return tradeOrders.get(0);
    }

    public PageInfo<TDiegoTradeOrder> findByCondition(OrderQueryReq queryReq) {
        if(queryReq.getPage() == null || queryReq.getPage() <= 0) queryReq.setPage(1);
        if(queryReq.getSize() == null || queryReq.getSize() <= 0) queryReq.setSize(20);
        if(queryReq.getSize() > 50) queryReq.setSize(50);
        TDiegoTradeOrderExample example = new TDiegoTradeOrderExample();
        TDiegoTradeOrderExample.Criteria criteria = example.createCriteria();
        criteria.andMchIdEqualTo(queryReq.getMchId());
        if(!StringUtils.isEmpty(queryReq.getOperatorId())) {
            criteria.andOperatorIdEqualTo(queryReq.getOperatorId());
        }
        if(!StringUtils.isEmpty(queryReq.getPayChannel())) {
            criteria.andPayChannelEqualTo(queryReq.getPayChannel());
        }
        if(!StringUtils.isEmpty(queryReq.getTradeType())) {
            criteria.andTradeTypeEqualTo(queryReq.getTradeType());
        }
        if(!CollectionUtils.isEmpty(queryReq.getInTradeStates())) {
            criteria.andTradeStateIn(queryReq.getInTradeStates());
        }
        if(!CollectionUtils.isEmpty(queryReq.getNotInTradeStates())) {
            criteria.andTradeStateNotIn(queryReq.getNotInTradeStates());
        }
        if(!StringUtils.isEmpty(queryReq.getStartDate())) {
            criteria.andCreateDateGreaterThanOrEqualTo(queryReq.getStartDate());
        }
        if(!StringUtils.isEmpty(queryReq.getEndDate())) {
            criteria.andCreateDateLessThanOrEqualTo(queryReq.getEndDate());
        }
        example.setOrderByClause("create_date desc, create_time desc");
        // get total count
        long totalCount = this.tradeOrderMapper.countByExample(example);
        // get data list
        RowBounds rowBounds = new RowBounds(queryReq.getPage(), queryReq.getSize());
        List<TDiegoTradeOrder> tradeOrders = this.tradeOrderMapper
                .selectByExampleWithRowbounds(example, rowBounds);
        PageInfo<TDiegoTradeOrder> pageable = new PageInfo<>(tradeOrders);
        return pageable;
    }

    public void clearSecures(List<TDiegoTradeOrder> tradeOrders) {
        if(CollectionUtils.isEmpty(tradeOrders)) return;
        for(TDiegoTradeOrder tradeOrder: tradeOrders) {
            tradeOrder.settOrderId(null);
            tradeOrder.settMchId(null);
            tradeOrder.settAppId(null);
        }
    }

}
