package com.hq.crash.service.statistics;

import com.alibaba.fastjson.JSON;
import com.hq.crash.model.auth.UserSession;
import com.hq.crash.model.statistics.BillSummary;
import com.hq.crash.model.statistics.StatisticSummary;
import com.hq.crash.service.common.CrashCommonService;
import com.hq.diego.model.resp.statistics.MchTradeStatisticSummaries;
import com.hq.diego.model.resp.statistics.MchTradeStatistics;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.MapBuilder;
import com.hq.scrati.model.HqRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * Created by zhaoyang on 05/02/2017.
 */
@Service
public class StatisticsService extends CrashCommonService {

    public StatisticSummary getStatisticSummary(
            String statisticDate, UserSession userSession) {
        if (StringUtils.isEmpty(statisticDate)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "统计日期不能为空[statisticDate]");
        }
        HqRequest request = getHqRequest(userSession);
        String jsonStr = JSON.toJSONString(
            MapBuilder.create().add("startDate", statisticDate).add("endDate", statisticDate).get()
        );
        request.setBizContent(jsonStr);
        MchTradeStatisticSummaries summaries = invoke(
                "diego.trade.statistics", "1.0", request, MchTradeStatisticSummaries.class);
        StatisticSummary statisticSummary = new StatisticSummary();
        statisticSummary.setTotalAmount(summaries.getTotalAmount());
        statisticSummary.setTradeCount(summaries.getTradeCount());
        statisticSummary.setCouponVerifyCountToday(0);
        statisticSummary.setMemberIncomesToday(0);
        return statisticSummary;
    }

    public BillSummary getBillSummary(String statisticDate, UserSession userSession) {
        if (StringUtils.isEmpty(statisticDate)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "统计日期不能为空[statisticDate]");
        }
        HqRequest request = getHqRequest(userSession);
        String jsonStr = JSON.toJSONString(
                MapBuilder.create().add("startDate", statisticDate).add("endDate", statisticDate).get()
        );
        request.setBizContent(jsonStr);
        MchTradeStatisticSummaries summaries = invoke(
                "diego.trade.statistics", "1.0", request, MchTradeStatisticSummaries.class);
        BillSummary billSummary = new BillSummary();
        billSummary.setAliTotalAmount(BigDecimal.ZERO);
        billSummary.setAliTradeCount(0);
        billSummary.setWxTotalAmount(BigDecimal.ZERO);
        billSummary.setWxTradeCount(0);
        for (MchTradeStatistics item: summaries.getStatisticItems()) {
            PayChannels.PayChannel payChannel = PayChannels
                    .fromString(item.getPayChannel());
            if (PayChannels.WEIXIN_PAY.equals(payChannel)) {
                billSummary.setWxTradeCount(billSummary
                        .getWxTradeCount() + item.getTradeCount());
                billSummary.setWxTotalAmount(billSummary
                        .getWxTotalAmount().add(item.getTotalAmount()));
            } else if (PayChannels.ALI_PAY.equals(payChannel)) {
                billSummary.setAliTradeCount(billSummary
                        .getAliTradeCount() + item.getTradeCount());
                billSummary.setAliTotalAmount(billSummary
                        .getAliTotalAmount().add(item.getTotalAmount()));
            }
        }
        billSummary.setReceiptAmount(summaries.getReceiptAmount());
        billSummary.setIncomeAmount(summaries
                .getReceiptAmount().subtract(summaries.getRateFee()));
        billSummary.setTotalAmount(summaries.getTotalAmount());
        billSummary.setTradeCount(summaries.getTradeCount());
        billSummary.setRateFee(summaries.getRateFee());
        // 后续加上退款功能需要填充值
        billSummary.setRefundRateFee(BigDecimal.ZERO);
        billSummary.setRefundAmount(BigDecimal.ZERO);
        billSummary.setRefundCount(0);
        return billSummary;
    }
}
