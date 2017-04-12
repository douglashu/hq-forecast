package com.hq.diego.gateway.service.persist.trade;

import com.alibaba.fastjson.JSON;
import com.hq.diego.gateway.service.core.RateCalcUtil;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.diego.repository.model.generate.TDiegoTradeOrderExample;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.constants.trade.TradeState;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.diego.repository.dao.generate.TDiegoTradeOrderMapper;
import com.hq.diego.repository.model.generate.TDiegoTradeOrder;
import com.hq.scrati.framework.IDGenerator;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by zhaoyang on 30/11/2016.
 */
@Service
public class TradeOrderService {

    private static final Logger logger = Logger.getLogger(TradeOrderService.class);

    @Value("${node.id}")
    private String nodeId;

    @Autowired
    private TDiegoTradeOrderMapper tradeOrderMapper;

    @Autowired
    private IDGenerator idGenerator;

    public String savePreCreateOrder(final OrderTradeReq tradeReq, final ChannelRoute route) {
        DateTime now = DateTime.now();
        TDiegoTradeOrder tradeOrder = new TDiegoTradeOrder();
        try {
            tradeOrder.setId(this.idGenerator.generateFlowId(tradeReq.getMchId()));
        } catch (Throwable th) {
            throw new CommonException(CommonErrCode.BUSINESS, "Generate Order RunningNo Fail");
        }
        tradeOrder.setChannel(route.getId());
        tradeOrder.setOrderId(tradeReq.getOrderId());
        tradeOrder.setMchId(tradeReq.getMchId());
        tradeOrder.settMchId(route.getMchId());
        tradeOrder.settAppId(route.getAppId());
        tradeOrder.settStoreId(tradeReq.gettStoreId());
        tradeOrder.setTerminalId(tradeReq.getTerminalId());
        tradeOrder.setOperatorId(tradeReq.getOperatorId());
        tradeOrder.setOperatorName(tradeReq.getOperatorName());
        tradeOrder.setIpAddress(tradeReq.getIpAddress());
        tradeOrder.setPayChannel(tradeReq.getPayChannel());
        tradeOrder.setTradeType(tradeReq.getTradeType());
        tradeOrder.setTitle(tradeReq.getTitle());
        tradeOrder.setBody(tradeReq.getBody());
        tradeOrder.setFeeType(tradeReq.getFeeType());
        tradeOrder.setTotalAmount(Long.valueOf(tradeReq.getTotalAmount()));
        tradeOrder.setReceiptAmount(0L);
        tradeOrder.setBuyerPayAmount(0L);
        tradeOrder.setRate(route.getRate());
        tradeOrder.setRateFee(0L);
        tradeOrder.setTradeState(TradeState.PRE_CREATE);
        if(PayChannels.WEIXIN_PAY.getCode().equals(tradeOrder.getPayChannel())) {
            tradeOrder.setOpenId(tradeReq.getOpenId());
        } else if(PayChannels.ALI_PAY.getCode().equals(tradeOrder.getPayChannel())) {
            tradeOrder.settUserId(tradeReq.getOpenId());
        }
        tradeOrder.setCreateDate(now.toString("YYYYMMdd"));
        tradeOrder.setCreateTime(now.toString("HHmmss"));
        tradeOrder.setAttach(tradeReq.getAttach());
        tradeOrder.setErrorCode("");
        tradeOrder.setErrorCodeDes("");
        if(tradeReq.getGoodsList() != null
                && tradeReq.getGoodsList().size() > 0) {
            tradeOrder.setDetail(JSON.toJSONString(tradeReq.getGoodsList()));
        }
        tradeOrder.setNodeId(this.nodeId);
        this.tradeOrderMapper.insertSelective(tradeOrder);
        return tradeOrder.getId();
    }

    public void updateWithTradeResp(OrderTradeResp tradeResp, BigDecimal rate) {
        TDiegoTradeOrder updateEntity = new TDiegoTradeOrder();
        updateEntity.setId(tradeResp.getTradeId());
        updateEntity.settOrderId(tradeResp.gettOrderId());
        if(tradeResp.getReceiptAmount() != null) {
            updateEntity.setReceiptAmount(tradeResp.getReceiptAmount() * 1L);
        } else {
            if (tradeResp.getTotalAmount() != null) {
                updateEntity.setReceiptAmount(tradeResp.getTotalAmount() * 1L);
            }
        }
        if(tradeResp.getBuyerPayAmount() != null) {
            updateEntity.setBuyerPayAmount(tradeResp.getBuyerPayAmount() * 1L);
        } else {
            updateEntity.setBuyerPayAmount(updateEntity.getReceiptAmount());
        }
        if(TradeState.SUCCESS.equals(tradeResp.getTradeState())) {
            updateEntity.setRateFee(RateCalcUtil
                    .getRateFeeAsCentUnit(tradeResp.getReceiptAmount(), rate));
        }
        updateEntity.setTradeState(tradeResp.getTradeState());
        updateEntity.setEndDate(tradeResp.getEndDate());
        updateEntity.setEndTime(tradeResp.getEndTime());
        updateEntity.setOpenId(tradeResp.getOpenId());
        updateEntity.setIsSubscribe(tradeResp.getIsSubscribe());
        updateEntity.setSubOpenId(tradeResp.getSubOpenId());
        updateEntity.setSubIsSubscribe(tradeResp.getSubIsSubscribe());
        updateEntity.settUserId(tradeResp.gettUserId());
        updateEntity.settLoginId(tradeResp.gettLoginId());
        updateEntity.setFundBills(tradeResp.getFundBills());
        updateEntity.setBankType(tradeResp.getBankType());
        if(StringUtils.isEmpty(tradeResp.getErrCode())) {
            updateEntity.setErrorCode("");
        } else {
            updateEntity.setErrorCode(tradeResp.getErrCode());
        }
        if(StringUtils.isEmpty(tradeResp.getErrCodeDes())) {
            updateEntity.setErrorCodeDes("");
        } else {
            updateEntity.setErrorCodeDes(tradeResp.getErrCodeDes());
        }
        DateTime now = DateTime.now();
        updateEntity.setUpdateDate(now.toString("YYYYMMdd"));
        updateEntity.setUpdateTime(now.toString("HHmmss"));
        this.tradeOrderMapper.updateByPrimaryKeySelective(updateEntity);
    }

    public void confirmTradeOrder(String orderId, String tOrderId
            , Long totalAmount, Long receiptAmount, Long buyerPayAmount
            , String tUserId, String tLoginId, String openId, String subOpenId
            , String isSubscribe, String subIsSubscribe, String endDate, String endTime
            , String bankType, String fundBillList, BigDecimal rate) {
        TDiegoTradeOrderExample example = new TDiegoTradeOrderExample();
        example.createCriteria().andOrderIdEqualTo(orderId)
                .andTradeStateIn(Arrays.asList(TradeState.PRE_CREATE, TradeState.WAIT_PAY));
        TDiegoTradeOrder update = new TDiegoTradeOrder();
        update.settOrderId(tOrderId);
        update.setTradeState(TradeState.SUCCESS);
        update.setTotalAmount(totalAmount);
        if (receiptAmount != null) {
            update.setReceiptAmount(receiptAmount);
        } else {
            update.setReceiptAmount(totalAmount);
        }
        if (buyerPayAmount != null) {
            update.setBuyerPayAmount(buyerPayAmount);
        } else {
            update.setBuyerPayAmount(totalAmount);
        }
        update.setRateFee(RateCalcUtil.getRateFeeAsCentUnit(
                update.getReceiptAmount(), rate));
        update.setEndDate(endDate);
        update.setEndTime(endTime);
        update.settUserId(tUserId);
        update.settLoginId(tLoginId);
        update.setOpenId(openId);
        update.setIsSubscribe(isSubscribe);
        update.setSubOpenId(subOpenId);
        update.setSubIsSubscribe(subIsSubscribe);
        update.setBankType(bankType);
        update.setFundBills(fundBillList);
        DateTime now = DateTime.now();
        update.setUpdateDate(now.toString("YYYYMMdd"));
        update.setUpdateTime(now.toString("HHmmss"));
        this.tradeOrderMapper.updateByExampleSelective(update, example);
    }

    public TDiegoTradeOrder getTradeOrderById(String tradeId) {
        if(StringUtils.isEmpty(tradeId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "交易号不能为空[tradeId]");
        }
        TDiegoTradeOrder tradeOrder = this.tradeOrderMapper.selectByPrimaryKey(tradeId);
        return tradeOrder;
    }
}
