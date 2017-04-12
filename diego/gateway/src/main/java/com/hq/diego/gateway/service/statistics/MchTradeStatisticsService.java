package com.hq.diego.gateway.service.statistics;

import com.hq.diego.model.req.statistics.MchTradeStatisticsReq;
import com.hq.diego.model.resp.statistics.MchTradeStatistics;
import com.hq.diego.model.resp.statistics.MchTradeStatisticSummaries;
import com.hq.diego.repository.dao.custom.TradeStatisticsMapper;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhaoyang on 03/02/2017.
 */
@Service
public class MchTradeStatisticsService {

    @Autowired
    private TradeStatisticsMapper statisticsMapper;

    public MchTradeStatisticSummaries getTradeSummaries(MchTradeStatisticsReq statisticsReq) {
        if (statisticsReq == null) throw new CommonException(CommonErrCode.ARGS_INVALID);
        if (StringUtils.isEmpty(statisticsReq.getMchId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商户号不能为空[mchId]");
        }
        if (StringUtils.isEmpty(statisticsReq.getStartDate())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "交易开始日期[startDate]");
        }
        if (StringUtils.isEmpty(statisticsReq.getEndDate())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "交易结束日期[endDate]");
        }
        MchTradeStatisticSummaries summaries = new MchTradeStatisticSummaries();
        Integer tradeCount = 0;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal receiptAmount = BigDecimal.ZERO;
        BigDecimal rateFee = BigDecimal.ZERO;
        List<MchTradeStatistics> statisticItems = this.statisticsMapper.getMchTradeStatistics(
                statisticsReq.getMchId(), statisticsReq.getStartDate(), statisticsReq.getEndDate());
        summaries.setStatisticItems(statisticItems);
        for (MchTradeStatistics item: summaries.getStatisticItems()) {
            if (item.getRateFee() == null) item.setRateFee(BigDecimal.ZERO);
            totalAmount = totalAmount.add(item.getTotalAmount());
            receiptAmount = receiptAmount.add(item.getReceiptAmount());
            rateFee = rateFee.add(item.getRateFee());
            tradeCount += item.getTradeCount();
        }
        summaries.setTradeCount(tradeCount);
        summaries.setTotalAmount(totalAmount);
        summaries.setReceiptAmount(receiptAmount);
        summaries.setRateFee(rateFee);
        return summaries;
    }

}
