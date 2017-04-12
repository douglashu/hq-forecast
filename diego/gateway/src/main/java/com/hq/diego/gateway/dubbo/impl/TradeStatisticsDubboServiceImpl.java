package com.hq.diego.gateway.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.diego.gateway.dubbo.TradeStatisticsDubboService;
import com.hq.diego.gateway.service.statistics.MchTradeStatisticsService;
import com.hq.diego.model.req.statistics.MchTradeStatisticsReq;
import com.hq.diego.model.resp.statistics.MchTradeStatisticSummaries;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoyang on 03/02/2017.
 */
@Service(interfaceName = "com.hq.diego.gateway.dubbo.TradeStatisticsDubboService", version = "1.0")
public class TradeStatisticsDubboServiceImpl extends DubboBaseService implements TradeStatisticsDubboService {

    @Autowired
    private MchTradeStatisticsService statisticsService;

    @Override
    public RespEntity getMchTradeStatisticSummaries(HqRequest request) {
        try {
            MchTradeStatisticsReq statisticsReq = parseRequest(request, MchTradeStatisticsReq.class);
            MchTradeStatisticSummaries summaries = this
                    .statisticsService.getTradeSummaries(statisticsReq);
            return getSuccessResp(summaries);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

}
